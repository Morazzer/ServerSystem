package org.morazzer.serversystem.spigot;

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
    static ServerSystem getInstance() { return null; /* Implemented in Runtime */ }

    @NotNull
    static JavaPlugin getPlugin() { return null; /* Implemented in Runtime */ }

    Properties getProperties();

    Websocket getWebsocket();

}
