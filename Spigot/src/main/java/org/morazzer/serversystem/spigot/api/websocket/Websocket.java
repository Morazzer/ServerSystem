package org.morazzer.serversystem.spigot.api.websocket;

import org.morazzer.serversystem.spigot.InstanceManager;

/**
 * @author Morazzer
 * @since Date 16.10.2020 16:36:03
 */
public interface Websocket {

    void send(String message);
    void send(Object object);
    static Websocket getInstance() { return (Websocket) InstanceManager.getInstance(Websocket.class); }

}
