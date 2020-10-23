package org.morazzer.serversystem.api.v1.controller;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.morazzer.serversystem.api.types.dataclasses.Rank;
import org.morazzer.serversystem.api.v1.Websocket;
import org.morazzer.serversystem.api.v1.models.UUIDModel;
import org.morazzer.serversystem.api.v1.models.rank.RankPermissionModel;
import org.morazzer.serversystem.api.v1.models.rank.RankModel;
import org.morazzer.serversystem.api.v1.models.rank.RankUpdateModel;
import org.morazzer.serversystem.api.v1.models.rank.SetParentModel;
import org.morazzer.serversystem.serializable.RankUpdate;
import org.morazzer.serversystem.serializable.enums.RankUpdeType;

import java.util.List;
import java.util.UUID;

/**
 * @author Morazzer
 * @since Date 08.10.2020 13:57:41
 */
public class RankController {

    private static Gson gson = new Gson();

    public static void create(Context context) {
        RankModel data = context.bodyAsClass(RankModel.class);

        Rank rank = Rank.addRank(data, UUID.randomUUID());

        context.result(gson.toJson(rank));

        RankUpdate rankUpdate = new RankUpdate();

        rankUpdate.uuid = rank.uuid;
        rankUpdate.type = RankUpdeType.CREATE;

        Websocket.publishUpdate(rankUpdate);
    }

    public static void getRankGET(Context context) {
        Rank rank = Rank.of(context.queryParam("uuid"));

        context.result(gson.toJson(rank));
    }

    public static void getRank(Context context) {
        UUIDModel data = context.bodyAsClass(UUIDModel.class);

        Rank rank = Rank.of(data.uuid);

        context.result(gson.toJson(rank));
    }

    public static void list(Context context) {
        List<Rank> ranks = Rank.getRanks();

        context.result(gson.toJson(ranks.toArray()));
    }

    public static void updateName(Context context) {
        RankUpdateModel data = gson.fromJson(context.body(), RankUpdateModel.class);

        System.out.println(data.name);
    }

    public static void addPermission(Context context) {
        RankPermissionModel data = context.bodyAsClass(RankPermissionModel.class);

        Rank rank = Rank.of(data.uuid);

        rank.addPermission(data.permission);

        context.result(gson.toJson(rank));

        RankUpdate rankUpdate = new RankUpdate();

        rankUpdate.uuid = rank.uuid;
        rankUpdate.permissions = rank.permissions;
        rankUpdate.type = RankUpdeType.PERMISSIONADD;

        Websocket.publishUpdate(rankUpdate);
    }

    public static void removePermission(Context context) {
        RankPermissionModel data = context.bodyAsClass(RankPermissionModel.class);

        Rank rank = Rank.of(data.uuid);

        rank.removePermission(data.permission);

        context.result(gson.toJson(rank));

        RankUpdate rankUpdate = new RankUpdate();

        rankUpdate.uuid = rank.uuid;
        rankUpdate.permissions = rank.permissions;
        rankUpdate.type = RankUpdeType.PERMISSIONREMOVE;

        Websocket.publishUpdate(rankUpdate);
    }

    public static void setInherit(Context context) {
        SetParentModel data = context.bodyAsClass(SetParentModel.class);

        Rank rank = Rank.of(data.uuid);

        rank.setInherit(UUID.fromString(data.inherit));

        context.result(gson.toJson(rank));

        RankUpdate rankUpdate = new RankUpdate();

        rankUpdate.uuid = rank.uuid;
        rankUpdate.inheritUUID = rank.inheritUuid;
        rankUpdate.type = RankUpdeType.INHERIT;

        Websocket.publishUpdate(rankUpdate);
    }

    public static void removeParent(Context context) {
        UUIDModel data = context.bodyAsClass(UUIDModel.class);

        Rank rank = Rank.of(data.uuid);

        rank.resetInherit();

        context.result(gson.toJson(rank));

        RankUpdate rankUpdate = new RankUpdate();

        rankUpdate.uuid = rank.uuid;
        rankUpdate.inheritUUID = rank.inheritUuid;
        rankUpdate.type = RankUpdeType.INHERIT;

        Websocket.publishUpdate(rankUpdate);
    }

    public static void deleteRank(Context context) {
        UUIDModel data = context.bodyAsClass(UUIDModel.class);

        Rank rank = Rank.of(data.uuid);

        rank.delete();

        context.result(gson.toJson(rank));

        RankUpdate rankUpdate = new RankUpdate();

        rankUpdate.uuid = rank.uuid;
        rankUpdate.type = RankUpdeType.DELETE;

        Websocket.publishUpdate(rankUpdate);
    }
}
