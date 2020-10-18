package org.morazzer.serversystem.spigot;

import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.morazzer.serversystem.spigot.api.Api;
import org.morazzer.serversystem.spigot.api.websocket.Websocket;

import java.util.Properties;

/**
 * @author Morazzer
 * @since Date 15.10.2020 16:00:40
 */
public interface ServerSystem extends Api {

    @NotNull
    static ServerSystem getInstance() {
        return (ServerSystem) InstanceManager.getInstance(ServerSystem.class);
    }

    @NotNull
    static JavaPlugin getPlugin() {
        if (InstanceManager.getInstance(ServerSystem.class) instanceof JavaPlugin) {
            return (JavaPlugin) InstanceManager.getInstance(ServerSystem.class);
        } else {
            throw new RuntimeException(new InvalidPluginException("Instance ins't a Plugin"));
        }
    }

    Properties getProperties();
    Websocket getWebsocket();

}
