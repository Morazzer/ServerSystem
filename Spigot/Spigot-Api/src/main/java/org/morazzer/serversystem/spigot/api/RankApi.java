package org.morazzer.serversystem.spigot.api;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.morazzer.serversystem.spigot.api.types.interfaces.Rank;

import java.util.UUID;

/**
 * @author Morazzer
 * @since Date 15.10.2020 16:20:08
 */
public interface RankApi {

    @NotNull
    static RankApi getInstance() { return null; /* Implemented in Runtime */ }

    public Rank getRankPerName(String name);
    default public boolean hasPermission(Rank rank, String permission) {
        return hasPermission(rank, permission, true);
    }
    default public boolean hasPermission(Rank rank, Permission permission) {
        return hasPermission(rank, permission, true);
    }
    default public boolean hasPermission(Player player, String permission) {
        return hasPermission(player, permission, true);
    }
    default public boolean hasPermission(Player player, Permission permission) {
        return hasPermission(player, permission, true);
    }
    public boolean hasPermission(Rank rank, String permission, boolean inherited);
    public boolean hasPermission(Rank rank, Permission permission, boolean inherited);
    public boolean hasPermission(Player player, String permission, boolean inherited);
    public boolean hasPermission(Player player, Permission permission, boolean inherited);
    public Rank getRank(UUID uuid);
    public Rank getRank(String uuid);
    public Rank addRankAndGet(UUID uuid);
    public Rank addRankAndGet(String uuid);


}
