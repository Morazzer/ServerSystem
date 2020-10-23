package org.morazzer.serversystem.api.v1.controller;

import com.google.gson.JsonObject;
import io.javalin.http.Context;
import io.javalin.websocket.WsMessageContext;
import org.morazzer.serversystem.api.Api;
import org.morazzer.serversystem.api.DataSource;
import org.morazzer.serversystem.api.RandomString;
import org.morazzer.serversystem.api.types.dataclasses.Token;
import org.morazzer.serversystem.api.v1.models.auth.SignInUserModel;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * @author Morazzer
 * @since Date 05.10.2020 22:43:52
 */
public class AuthController {

    public static void signin(Context ctx) {
        SignInUserModel data = ctx.bodyAsClass(SignInUserModel.class);
        if (data.username == null || data.password == null) {
            ctx.status(400);
            return;
        }
        try (Connection conn = DataSource.getConnection()) {
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM `users` WHERE `username`= '" + data.username + "'");
            if (!resultSet.next()) {
                ctx.status(412);
                return;
            }
            if (!BCrypt.checkpw(data.password, resultSet.getString("password"))) {
                ctx.status(401);
                return;
            }

            //TODO: Create new session
            String name = resultSet.getString("username");
            int id = resultSet.getInt("id");
            resultSet.close();

            if (ctx.req.getSession() != null)
                ctx.req.changeSessionId();
            ctx.sessionAttribute("id", id);
            ctx.status(200);

            JsonObject jsonObject = new JsonObject();

            String token = new RandomString(96).nextString();

            jsonObject.addProperty("token", token);

            Token tokenClass = new Token();

            tokenClass.userID = id;
            tokenClass.username = name;

            Api.getApi().tokensUser.put(token, tokenClass);
            Api.getApi().tokens.add(token);

            ctx.result(jsonObject.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(501);
        }
    }

    public static void signout(Context ctx) {

        if (ctx.header("token") != null) {
            String token = ctx.header("token");

            Api.getApi().tokens.remove(token);
            Api.getApi().tokensUser.remove(token);
        }

        ctx.req.getSession().invalidate();
    }

    public static void wsLogin(WsMessageContext context) {
        SignInUserModel data = context.message(SignInUserModel.class);
    }
}
