package org.morazzer.serversystem.spigot.api.websocket;

import org.jetbrains.annotations.NotNull;

/**
 * @author Morazzer
 * @since Date 16.10.2020 16:36:03
 */
public interface Websocket {

    void send(String message);
    void send(Object object);
    @NotNull
    static Websocket getInstance() { return null; /* Implemented in Runtime */ }

}
