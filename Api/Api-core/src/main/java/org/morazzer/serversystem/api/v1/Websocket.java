package org.morazzer.serversystem.api.v1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.javalin.websocket.*;
import org.jetbrains.annotations.NotNull;
import org.morazzer.serversystem.api.Api;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author Morazzer
 * @since Date 06.10.2020 19:49:10
 */
public class Websocket implements WsBinaryMessageHandler, WsCloseHandler, WsErrorHandler, WsMessageHandler, WsConnectHandler {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final List<WsConnectContext> handlers = new ArrayList<>();

    public Websocket() {
        Api.getApi().getJavalin().ws("/api/v1/websocket", wsHandler -> {
            wsHandler.onMessage(this);
            wsHandler.onConnect(this);
            wsHandler.onClose(this);
            wsHandler.onError(this);
            wsHandler.onBinaryMessage(this);
        });
    }

    @Override
    public void handleBinaryMessage(@NotNull WsBinaryMessageContext wsBinaryMessageContext) throws Exception {

    }

    @Override
    public void handleClose(@NotNull WsCloseContext wsCloseContext) throws Exception {
        handlers.remove(handlers.stream().filter(wsConnectContext -> wsConnectContext.session.equals(wsCloseContext.session)).findFirst().orElse(null));
    }

    @Override
    public void handleConnect(@NotNull WsConnectContext wsConnectContext) throws Exception {
        //System.out.println(wsConnectContext.host());
        handlers.add(wsConnectContext);
    }

    @Override
    public void handleError(@NotNull WsErrorContext wsErrorContext) throws Exception {

    }

    @Override
    public void handleMessage(@NotNull WsMessageContext wsMessageContext) {
//        if (wsMessageContext.header("token") == null || !Api.getApi().tokens.contains(wsMessageContext.header("token"))) {
//            System.out.println("token inst valid on Request " + wsMessageContext.host());
//            return;
//        }
        System.out.println(wsMessageContext.message());
    }

    public static void publishUpdate(Serializable serializable) {
        try {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.close();


            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("serializable", Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()));
            jsonObject.addProperty("json", gson.toJson(serializable));

            handlers.forEach(wsConnectContext -> wsConnectContext.send(jsonObject.toString()));
        } catch (IOException ignored) {
        }
    }

    public static void publishShutdown() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("event", "shutdown_api");

        handlers.forEach(wsConnectContext -> wsConnectContext.send(jsonObject.toString()));
    }
}
