package org.morazzer.serversystem.api.v1.controller;

import io.javalin.http.Context;
import org.morazzer.serversystem.api.v1.models.moderation.BanModel;

/**
 * @author Morazzer
 * @since Date 11.10.2020 23:32:48
 */
public class ModerationController {

    public static void ban(Context context) {
        BanModel data = context.bodyAsClass(BanModel.class);

    }

}
