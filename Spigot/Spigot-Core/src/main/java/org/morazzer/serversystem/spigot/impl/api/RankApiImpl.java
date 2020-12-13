package org.morazzer.serversystem.spigot.impl.api;

import com.google.gson.Gson;
import org.apache.http.client.methods.HttpGet;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.morazzer.serversystem.spigot.InstanceManager;
import org.morazzer.serversystem.spigot.api.Api;
import org.morazzer.serversystem.spigot.api.RankApi;
import org.morazzer.serversystem.spigot.api.types.interfaces.Rank;
import org.morazzer.serversystem.spigot.impl.api.models.RankModel;
import org.morazzer.serversystem.spigot.impl.manager.RankManager;

import java.util.UUID;

/**
 * @author Morazzer
 * @since Date 10.10.2020 10:11:01
 */
public class RankApiImpl implements RankApi {

    public RankApiImpl() {
        InstanceManager.setInstance(this);
    }

    @Override
    public Rank getRankPerName(String name) {
        return null;
    }

    @Override
    public boolean hasPermission(Rank rank, String permission, boolean inherited) {
        return false;
    }

    @Override
    public boolean hasPermission(Rank rank, Permission permission, boolean inherited) {
        return false;
    }

    @Override
    public boolean hasPermission(Player player, String permission, boolean inherited) {
        return false;
    }

    @Override
    public boolean hasPermission(Player player, Permission permission, boolean inherited) {
        if (inherited) {
            return player.hasPermission(permission);
        } else  {
            throw new RuntimeException("No't Implemented Yet"); //TODO: Add User Api
        }
    }


    public Rank getRank(UUID uuid) {
        return RankManager.getRanks().stream().filter(rank -> rank.getUUID().equals(uuid)).findFirst().orElse(null);
    }

    public Rank getRank(String uuid) {
        return getRank(UUID.fromString(uuid));
    }

    public Rank addRankAndGet(UUID uuid) {
        HttpGet httpGet = new HttpGet(Api.getInstance().getApiPath("/rank/get?uuid=" + uuid.toString()));


        RankModel rankModel = new Gson().fromJson(Api.getInstance().execute(httpGet), RankModel.class);

        RankManager.add(rankModel);

        return getRank(rankModel.uuid);
    }

    public Rank addRankAndGet(String uuid) {
        return addRankAndGet(UUID.fromString(uuid));
    }

    @Override
    public int getRanksSize() {
        return RankManager.getRanks().size();
    }

}
