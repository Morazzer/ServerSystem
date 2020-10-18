package org.morazzer.serversystem.spigot.api.types.interfaces;

import org.bukkit.entity.Player;

/**
 * @author Morazzer
 * @since Date 17.10.2020 17:23:37
 */
public interface User extends Player {

    Rank getRank();

}
