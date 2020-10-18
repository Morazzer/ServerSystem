package org.morazzer.serversystem.spigot.api.types.interfaces;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.UUID;

/**
 * @author Morazzer
 * @since Date 15.10.2020 16:07:49
 */
public interface Rank {

    String getName();
    String getColorcode();
    ChatColor getColor();
    String getPrefix();
    UUID getUUID();
    Rank getInherit();
    UUID getInheritUUID();
    List<String> getPermissions(boolean inherited);
    default List<String> getPermissions() {
        return getPermissions(true);
    }

}
