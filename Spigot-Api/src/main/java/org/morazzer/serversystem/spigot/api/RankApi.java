package org.morazzer.serversystem.spigot.api;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.morazzer.serversystem.spigot.api.types.interfaces.Rank;

import java.util.UUID;

/**
 * @author Morazzer
 * @since Date 15.10.2020 16:20:08
 */
public interface RankApi {

    static RankApi getInstance() {
        return null; /* Implemented in Runtime */
    }

    public Rank getRankPerName(String name);
    public boolean hasPermission(Rank rank, String permission);
    public boolean hasPermission(Rank rank, Permission permission);
    public boolean hasPermission(Player player, String permission);
    public boolean hasPermission(Player player, Permission permission);
    public Rank getRank(UUID uuid);
    public Rank getRank(String uuid);
    public Rank addRankAndGet(UUID uuid);
    public Rank addRankAndGet(String uuid);


}
