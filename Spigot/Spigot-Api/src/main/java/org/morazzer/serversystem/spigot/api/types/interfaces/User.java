package org.morazzer.serversystem.spigot.api.types.interfaces;

import org.bukkit.entity.Player;

/**
 * @author Morazzer
 * @since Date 17.10.2020 15:01:51
 */
public interface User extends Player {

    Rank getRank();

}
