package org.morazzer.serversystem.api.logs;

import io.javalin.http.Context;
import org.morazzer.serversystem.api.Api;
import org.morazzer.serversystem.api.RequestHandler;

import java.util.Objects;

/**
 * @author Morazzer
 * @since Date 07.10.2020 22:53:12
 */
public class RequestLogger implements RequestHandler {
    @Override
    public void handleRequest(Context context) {

        String stringBuilder = "Request from user " +
                (context.header("token") == null ? "" :
                        (Api.getApi().tokensUser.get(context.header("token")) != null ? Api.getApi().tokensUser.get(context.header("token")).username : "")) + "; " +
                " Requst Type: " + context.method() + "; Requst Path: " + context.path() + "; Requst Port: " + context.port();
        Logger.info(stringBuilder);
    }
}
