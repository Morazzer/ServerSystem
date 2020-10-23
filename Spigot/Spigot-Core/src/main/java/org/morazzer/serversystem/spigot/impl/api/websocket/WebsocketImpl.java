package org.morazzer.serversystem.spigot.impl.api.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.morazzer.serversystem.serializable.RankUpdate;
import org.morazzer.serversystem.serializable.UserUpdate;
import org.morazzer.serversystem.spigot.InstanceManager;
import org.morazzer.serversystem.spigot.ServerSystem;
import org.morazzer.serversystem.spigot.api.Api;
import org.morazzer.serversystem.spigot.api.websocket.Websocket;
import org.morazzer.serversystem.spigot.impl.api.handler.RankUpdateHandler;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URI;
import java.util.Base64;

/**
 * @author Morazzer
 * @since Date 11.10.2020 14:48:15
 */
public class WebsocketImpl extends WebSocketClient implements Websocket {

    private static int runs = 5;
    private static int trys = 1;
    private static URI uri;
    private static boolean reconnected = false;

    public WebsocketImpl(URI serverUri) {
        super(serverUri);
        uri = serverUri;
        InstanceManager.setInstance(this);
        try {
            connect();
        } catch (Exception exception) {
            reconnectTimer();
            return;
        }

        if (isOpen())
            if (reconnected)
                Api.getInstance().reconnect();
        //addHeader("token", ServerSystem.getToken());
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("ServerSystem websocket connectet on " + getURI().toString());
    }

    @Override
    public void onMessage(String s) {
        JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();

        if (jsonObject.has("serializable")) {

            try {
                byte[] encoded = Base64.getDecoder().decode(jsonObject.get("serializable").getAsString());

                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(encoded)));
                Object object = ois.readObject();


                if (object instanceof RankUpdate) {
                    RankUpdateHandler.handle((RankUpdate) object);
                } else if (object instanceof UserUpdate) {

                }
            } catch (IOException | ClassNotFoundException ignored) {
            }

        }
    }

    public static void reconnectTimer() {
        reconnected = true;
        Bukkit.getScheduler().runTaskTimer(ServerSystem.getPlugin(), bukkitTask -> {
            if (runs == 0) {
                if (trys > 4) {
                    if (trys == 10) {
                        Bukkit.shutdown();
                    } else
                        System.out.println("Shutdown in " + (10 - trys) + " runs");
                }
                bukkitTask.cancel();
                trys++;
                runs = 5 * trys;
                try {
                    new WebsocketImpl(uri);
                } catch (Exception exception) {
                    reconnectTimer();
                }
            } else {
                System.out.println("Webscoket Disconnectet try reconnect in " + runs);
                runs = runs - 1;
            }
        }, 0, 20);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        reconnectTimer();
    }

    @Override
    public void onError(Exception e) {
    }


    @Override
    public void send(Object object) {
        send(new Gson().toJson(object));
    }
}
