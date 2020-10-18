package org.morazzer.serversystem.api;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.morazzer.serversystem.api.logs.RequestLogger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Morazzer
 * @since Date 06.10.2020 22:46:00
 */
public class Main {

    public static Main instance;
    public boolean enableDevLogging;

    public static void main(String[] args) {
        new Main(args);
    }

    public Main(String[] args) {
        instance = this;

        OptionParser optionParser = new OptionParser() {
            {
                acceptsAll(asList("dev", "dev-mode"), "Enable Dev Logging");
                acceptsAll(asList("?", "help"), "Hilfe");
            }
        };

        OptionSet options = optionParser.parse(args);

        if (options != null) {
            if (options.has("?") || options.has("help")) {
                try {
                    optionParser.printHelpOn(System.out);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            if (options.has("dev") || options.has("dev-mode")) {
                enableDevLogging = true;
            }
        }

        new Api(enableDevLogging,
                DataSource.isWebSiteEnabled(),
                DataSource.isInternalSite(),
                DataSource.getSiteName());

        Api.getApi().addRequestHandler(new RequestLogger());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                DataSource.getConnection().close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }));
    }

    private static List<String> asList(String... params) {
        return Arrays.asList(params);
    }

    public static Main getInstance() {
        return instance;
    }
}
