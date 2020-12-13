package org.morazzer.serversystem.spigot.impl;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.morazzer.serversystem.spigot.InstanceManager;
import org.morazzer.serversystem.spigot.ServerSystem;
import org.morazzer.serversystem.spigot.api.Api;
import org.morazzer.serversystem.spigot.api.RankApi;
import org.morazzer.serversystem.spigot.api.UserApi;
import org.morazzer.serversystem.spigot.api.websocket.Websocket;
import org.morazzer.serversystem.spigot.impl.api.ApiImpl;
import org.morazzer.serversystem.spigot.impl.api.RankApiImpl;
import org.morazzer.serversystem.spigot.impl.api.UserApiImpl;
import org.morazzer.serversystem.spigot.impl.api.models.SignUpModel;
import org.morazzer.serversystem.spigot.impl.api.models.TokenModel;
import org.morazzer.serversystem.spigot.impl.api.websocket.WebsocketImpl;
import org.morazzer.serversystem.spigot.impl.manager.UserManager;
import org.morazzer.serversystem.spigot.impl.types.annotations.Command;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author Morazzer
 * @since Date 08.10.2020 18:06:28
 */
public class ServerSystemImpl extends JavaPlugin implements ServerSystem {

    private static Properties properties;
    private static HttpClient client;
    private static Websocket websocket;

    @Override
    public void onDisable() {
        HttpPost post = new HttpPost(getApiPath("/auth/signout"));

        execute(post);

        super.onDisable();
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    private static String token;

    @Override
    public void onEnable() {

        InstanceManager.setInstance(this);

        new RankApiImpl();
        new ApiImpl();

        client = HttpClientBuilder.create().setMaxConnTotal(Integer.MAX_VALUE).build();

        signin();

        websocket = new WebsocketImpl(URI.create(getApiPath("websocket")));

        new SystemImpl().load();

        Reflections reflections = new Reflections("org.morazzer.serversystem.spigot.impl");

        for (Class<?> clazz : reflections.getTypesAnnotatedWith(Command.class)) {
            try {
                Constructor<?> constructor = Arrays.stream(clazz.getConstructors()).filter(constructorTemp -> constructorTemp.getParameterCount() == 0).findFirst().orElse(null);

                assert constructor != null;

                constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        System.out.print(
                "§c------------------------------------------§r\n" +
                "§bRanks Loaded§f: §6" + RankApi.getInstance().getRanksSize() + "§r\n"+
                "§bPlayers on Network§f: §6" + UserManager.getUsersOnServer().size() + "\n" +
                "§c------------------------------------------§r\n"
        );
    }

    private void signin() {
        try {
            File propertiesFile = new File("api.properties");

            if (!propertiesFile.exists())
                propertiesFile.createNewFile();

            properties = new Properties();

            properties.load(new FileReader(propertiesFile));

            SignUpModel data = new SignUpModel();

            if (!properties.containsKey("api_host")) {
                properties.put("api_host", "127.0.0.1");
                properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
            }
            if (!properties.containsKey("api_port")) {
                properties.put("api_port", "18706");
                properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
            }
            if (!properties.containsKey("api_useauth")) {
                properties.put("api_useauth", "false");
                properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
            }
            if (properties.getProperty("api_useauth").equalsIgnoreCase("true")) {
                if (!properties.containsKey("api_username")) {
                    properties.put("api_username", "user");
                    properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
                }
                if (!properties.containsKey("api_password")) {
                    properties.put("api_password", "password");
                    properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
                }
                data.username = properties.getProperty("api_username");
                data.password = properties.getProperty("api_password");
            }

            HttpPost login = new HttpPost(getApiPath() + "auth/signin");

            login.setEntity(new StringEntity(new Gson().toJson(data)));

            TokenModel tokenModel = new Gson().fromJson(execute(login), TokenModel.class);

            token = tokenModel.token;

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String getApiPath() {
        return Api.getInstance().getApiPath();
    }

    public String getApiPath(String path) {
        return Api.getInstance().getApiPath(path);
    }

    public String execute(HttpRequestBase requestBase) {
        return Api.getInstance().execute(requestBase);
    }

    public HttpResponse executeRaw(HttpRequestBase requestBase) {
        return Api.getInstance().executeRaw(requestBase);
    }

    @Override
    public void reconnect() {
        Api.getInstance().reconnect();
    }

    public HttpClient getClient() {
        return Api.getInstance().getClient();
    }

    public static String getToken() {
        return token;
    }

    @Override
    public Websocket getWebsocket() {
        return websocket;
    }

    public Properties getProperties() {
        return properties;
    }

    public static void setToken(String token) {
        ServerSystemImpl.token = token;
    }
}
