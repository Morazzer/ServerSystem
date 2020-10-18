package org.morazzer.serversystem.spigot.api.websocket;

/**
 * @author Morazzer
 * @since Date 16.10.2020 16:36:03
 */
public interface Websocket {

    public void send(String message);

    public void send(Object object);

}
