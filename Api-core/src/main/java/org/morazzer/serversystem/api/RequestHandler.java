package org.morazzer.serversystem.api;

import io.javalin.http.Context;

/**
 * @author Morazzer
 * @since Date 07.10.2020 22:50:59
 */
public interface RequestHandler {

    public void handleRequest(Context context);

}
