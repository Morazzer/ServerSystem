package org.morazzer.serversystem.spigot.impl.api.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.morazzer.serversystem.serializable.RankUpdate;
import org.morazzer.serversystem.spigot.InstanceManager;
import org.morazzer.serversystem.spigot.ServerSystem;
import org.morazzer.serversystem.spigot.api.websocket.Websocket;
import org.morazzer.serversystem.spigot.impl.ServerSystemImpl;
import org.morazzer.serversystem.spigot.impl.api.handler.RankUpdateHandler;

import java.io.*;
import java.net.URI;
import java.util.Base64;

/**
 * @author Morazzer
 * @since Date 11.10.2020 14:48:15
 */
public class WebsocketImpl extends WebSocketClient implements Websocket {

    public WebsocketImpl(URI serverUri) {
        super(serverUri);
        InstanceManager.setInstance(this);
        connect();
        //addHeader("token", ServerSystem.getToken());
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        send("test");
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
                }
            } catch (IOException | ClassNotFoundException ignored) {}

        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        var ref = new Object() {
            int runs = 5;
            int trys = 0;
        };
        Bukkit.getScheduler().runTaskTimer(ServerSystem.getPlugin(),bukkitTask -> {
            if (ref.runs == 0) {
                connect();
                if (isOpen()) {
                    System.out.println("Succesfully reconnectet");
                    bukkitTask.cancel();
                } else {
                    ref.trys++;
                    ref.runs = 5 * ref.trys;
                }
            } else
                System.out.println("Webscoket Disconnectet try reconnect in " + ref.runs);

            ref.runs = ref.runs - 1;
        }, 0, 20);
    }

    @Override
    public void onError(Exception e) {

    }


    @Override
    public void send(Object object) {
        send(new Gson().toJson(object));
    }
}
