package org.morazzer.serversystem.spigot.api;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.morazzer.serversystem.spigot.api.types.interfaces.Rank;
import org.morazzer.serversystem.spigot.api.types.interfaces.User;

import java.util.UUID;

/**
 * @author Morazzer
 * @since Date 17.10.2020 17:25:30
 */
public interface UserApi {

    @NotNull
    static UserApi getInstance() { return null; /* Implemented in Runtime */ }

    Rank getRankFromPlayer(Player player);
    User getUser(Player player);
    User getUser(String uuid);
    User getUser(UUID uuid);

}
