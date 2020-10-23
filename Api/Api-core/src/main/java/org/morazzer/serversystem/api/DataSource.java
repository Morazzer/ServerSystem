package org.morazzer.serversystem.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.server.session.DatabaseAdaptor;
import org.eclipse.jetty.server.session.JDBCSessionDataStoreFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * @author Morazzer
 * @since Date 05.10.2020 21:47:29
 */
public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private static JDBCSessionDataStoreFactory factory;
    private static boolean my_Sql;
    private static int api_port;
    private static String api_host;
    private static boolean webSiteEnabled;
    private static boolean internalSite;
    private static String siteName;
    private static boolean enable_auth;

    static {


        try {

            Properties properties = new Properties();
            File propertiesFile = new File("api.properties");

            if (!propertiesFile.exists()) {
                try {
                    propertiesFile.createNewFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            properties.load(new FileReader(propertiesFile));

            if (!properties.containsKey("use_my_sql")) {
                properties.setProperty("use_my_sql", "false");
                properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
            }
            if (!properties.containsKey("api_port")) {
                properties.setProperty("api_port", "18706");
                properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
            }
            if (!properties.containsKey("api_host")) {
                properties.setProperty("api_host", "127.0.0.1");
                properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
            }
            if (!properties.containsKey("api_website")) {
                properties.setProperty("api_website", "false");
                properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
            }
            if (!properties.containsKey("api_website")) {
                properties.setProperty("api_website", "false");
                properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
            }
            if (!properties.containsKey("enable_auth")) {
                properties.setProperty("enable_auth", "true");
                properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
            }

            // Default

            api_port = Integer.parseInt(properties.getProperty("api_port"));
            api_host = properties.getProperty("api_host");
            webSiteEnabled = properties.getProperty("api_website").equalsIgnoreCase("true");
            enable_auth = properties.getProperty("enable_auth").equalsIgnoreCase("ture");

            if (properties.getProperty("api_website").equalsIgnoreCase("true")) {
                if (!properties.containsKey("website_internal")) {
                    properties.put("website_internal", "true");
                    properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
                }

                internalSite = properties.getProperty("website_internal").equalsIgnoreCase("true");

                if (properties.getProperty("website_internal").equalsIgnoreCase("false")) {
                    if (!properties.containsKey("website_name")) {
                        properties.setProperty("website_name", "app");
                        properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
                    }
                    siteName = properties.getProperty("website_name");
                }
            }

            if (properties.getProperty("use_my_sql").equalsIgnoreCase("true")) {
                if (!properties.containsKey("my_sql_server")) {
                    properties.setProperty("my_sql_server", "localhost");
                    properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
                }
                if (!properties.containsKey("my_sql_port")) {
                    properties.setProperty("my_sql_port", "3306");
                    properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
                }
                if (!properties.containsKey("my_sql_password")) {
                    properties.setProperty("my_sql_password", "password");
                    properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
                }
                if (!properties.containsKey("my_sql_user")) {
                    properties.setProperty("my_sql_user", "user");
                    properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
                }
                if (!properties.containsKey("my_sql_database")) {
                    properties.setProperty("my_sql_database", "database");
                    properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
                }
                my_Sql = true;
                config.setJdbcUrl("jdbc:mysql://" + properties.getProperty("my_sql_server", "localhost")
                        + ":" + properties.getProperty("my_sql_port", "3306")
                        + "/" + properties.getProperty("my_sql_database", "database"));

                config.setUsername(properties.getProperty("my_sql_user"));
                config.setPassword(properties.getProperty("my_sql_password"));
                config.addDataSourceProperty("serverTimezone", "UTC");
                config.addDataSourceProperty("cachePrepStmts", "true");
                config.addDataSourceProperty("prepStmtCacheSize", "250");
                config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
                config.addDataSourceProperty("useSSL", "false");
            } else {
                my_Sql = false;
                config.setJdbcUrl("jdbc:sqlite:database.db");
            }

            if (siteName == null) {
                siteName = "app";
            }

            ds = new HikariDataSource(config);

            ds.setAutoCommit(true);

            factory = new JDBCSessionDataStoreFactory();
            factory.setDatabaseAdaptor(new DatabaseAdaptor() {{
                setDatasource(ds);
            }});

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static JDBCSessionDataStoreFactory getSessionDataStoreFactory() {
        return factory;
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static boolean isMySql() {
        return my_Sql;
    }

    public static int getApiPort() {
        return api_port;
    }

    public static String getApiHost() {
        return api_host;
    }

    public static boolean isWebSiteEnabled() {
        return webSiteEnabled;
    }

    public static boolean isInternalSite() {
        return internalSite;
    }

    public static String getSiteName() {
        return siteName;
    }

    public static Statement getStatement() {
        try {
            return getConnection().createStatement();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void execute(String sql) {
        try {
            getStatement().execute(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String sql) {
        try {
            return getStatement().executeQuery(sql);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static boolean isAuthEnabled() {
        return enable_auth;
    }

    public static void setEnable_auth(boolean enable_auth) {
        DataSource.enable_auth = enable_auth;
    }
}
