package org.morazzer.serversystem.waterfall;

import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * @author Morazzer
 * @since Date 07.10.2020 21:31:07
 */
public class ServerSystem extends Plugin {

    @Override
    public void onEnable() {
        File propertiesFile = new File("serversystem.properties");

        if (!propertiesFile.exists()) {
            try {
                propertiesFile.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ignored) {
                }
                getProxy().stop();
            }
        }

        Properties properties = new Properties();

        try {
            properties.load(new FileReader(propertiesFile));
        } catch (IOException exception) {
            exception.printStackTrace();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ignored) {
            }
            getProxy().stop();
        }

        try {

            if (!properties.containsKey("ws_server")) {
                properties.put("ws_server", "127.0.0.1");
                properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
            }
            if (!properties.containsKey("api_port")) {
                properties.put("api_port", "1807");
                properties.store(new FileWriter(propertiesFile), "Saved at " + LocalDateTime.now().toString());
            }
        } catch (IOException ignored) {}

        URI websocketUri = URI.create("http://" + properties.getProperty("ws_server") + ":" + properties.getProperty("api_port") + "/api/v1/websocket");
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onDisable() {

    }
}
