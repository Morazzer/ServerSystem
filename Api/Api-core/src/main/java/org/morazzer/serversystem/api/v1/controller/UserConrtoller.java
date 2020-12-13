package org.morazzer.serversystem.api.v1.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.javalin.http.Context;
import org.morazzer.serversystem.api.types.v1.dataclasses.Rank;
import org.morazzer.serversystem.api.types.v1.dataclasses.User;
import org.morazzer.serversystem.api.v1.models.user.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Morazzer
 * @since Date 07.10.2020 23:08:56
 */
public class UserConrtoller {


    private static Gson gson = new Gson();

    public static void registerUser(Context context) {
        UserModel data = context.bodyAsClass(UserModel.class);

        User user = User.register(data);

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", user.getName());
        jsonObject.addProperty("uuid", user.getUuid().toString());
        jsonObject.addProperty("vanished", user.isVanished());
        jsonObject.addProperty("server", user.getServer());

        context.result(jsonObject.toString());
    }

    public static void list(Context context) {
        int per_page = 20;
        if (context.queryParamMap().containsKey("per_page")) {
            per_page = Integer.parseInt(context.queryParam("per_page"));
        }

        List<User> users = new ArrayList<>();

        if (context.queryParamMap().containsKey("index")) {
            for (int i = per_page * (Integer.parseInt(context.queryParam("index"))); i < per_page * Integer.parseInt(context.queryParam("index")) + per_page; i++) {
                if (User.getUsers().size() == i || User.getUsers().size() == 0)
                    break;
                users.add(User.getUsers().get(i));
            }
        } else {
            for (int i = 0; i < per_page; i++) {
                if (User.getUsers().size() == i || User.getUsers().size() == 0)
                    break;
                users.add(User.getUsers().get(i));
            }
        }

        context.result(gson.toJson(users));
    }

    public static void changeVanished(Context context) {
        VanishedChangedModel data = context.bodyAsClass(VanishedChangedModel.class);

        User user = User.of(data.uuid);

        user.vanished = data.vanished;

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", user.name);
        jsonObject.addProperty("uuid", data.uuid);
        jsonObject.addProperty("vanished", data.vanished);

        context.result(jsonObject.toString());
    }

    public static void changeServer(Context context) {
        ServerChangeModel data = context.bodyAsClass(ServerChangeModel.class);

        User user = User.of(data.uuid);

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", user.name);
        jsonObject.addProperty("uuid", data.uuid);
        jsonObject.addProperty("oldServer", user.server);
        jsonObject.addProperty("newServer", data.server);

        user.server = data.server;

        context.result(jsonObject.toString());
    }

    public static void removeUser(Context context) {
        RemoveUserModel data = context.bodyAsClass(RemoveUserModel.class);

        User user = User.unregister(data.uuid);

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", user.name);
        jsonObject.addProperty("uuid", data.uuid);

        context.result(jsonObject.toString());
    }

    public static void setRank(Context context) {
        RankChangeModel data = context.bodyAsClass(RankChangeModel.class);

        User user = User.of(data.uuid);
    }

    public static void getRank(Context context) {
        String uuid = context.queryParam("uuid");

        User user = User.of(uuid);

        Rank rank = Rank.of(user.rank);

        context.result(gson.toJson(rank));
    }

    public static void getUser(Context context) {
        String uuid = context.queryParam("uuid");

        context.result(gson.toJson(User.of(uuid)));
    }
}
