package org.morazzer.serversystem.waterfall.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @author Morazzer
 * @since Date 07.10.2020 21:32:47
 */
public class Websocket extends WebSocketClient {
    public Websocket(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

    }

    @Override
    public void onMessage(String s) {

    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}
