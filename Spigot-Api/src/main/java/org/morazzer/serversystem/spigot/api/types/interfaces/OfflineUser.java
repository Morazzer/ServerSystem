package org.morazzer.serversystem.spigot.api.types.interfaces;

import java.util.UUID;

/**
 * @author Morazzer
 * @since Date 17.10.2020 23:09:23
 */
public interface OfflineUser {

    UUID getUniqueId();
    UUID getRankUUID();

}
