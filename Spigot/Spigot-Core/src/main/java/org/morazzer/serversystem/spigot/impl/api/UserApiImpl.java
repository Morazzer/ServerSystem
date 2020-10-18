package org.morazzer.serversystem.spigot.impl.api;

import org.bukkit.entity.Player;
import org.morazzer.serversystem.spigot.api.UserApi;
import org.morazzer.serversystem.spigot.api.types.interfaces.Rank;
import org.morazzer.serversystem.spigot.api.types.interfaces.User;

import java.util.UUID;

/**
 * @author Morazzer
 * @since Date 17.10.2020 17:28:05
 */
public class UserApiImpl implements UserApi {
    @Override
    public Rank getRankFromPlayer(Player player) {
        return null;
    }

    @Override
    public User getUser(Player player) {
        return null;
    }

    @Override
    public User getUser(String uuid) {
        return null;
    }

    @Override
    public User getUser(UUID uuid) {
        return null;
    }
}
