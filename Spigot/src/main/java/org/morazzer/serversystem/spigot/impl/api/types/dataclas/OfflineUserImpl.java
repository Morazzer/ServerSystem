package org.morazzer.serversystem.spigot.impl.api.types.dataclas;

import org.morazzer.serversystem.serializable.UserUpdate;
import org.morazzer.serversystem.spigot.api.types.interfaces.OfflineUser;

import java.util.UUID;

/**
 * @author Morazzer
 * @since Date 17.10.2020 23:10:14
 */
public class OfflineUserImpl implements OfflineUser {

    UUID playerUUID;
    UUID rankUUID;

    public OfflineUserImpl(UserUpdate userUpdate) {
        this.playerUUID = userUpdate.uuid;
        this.rankUUID = userUpdate.rankUUID;
    }

    @Override
    public UUID getUniqueId() {
        return playerUUID;
    }

    @Override
    public UUID getRankUUID() {
        return rankUUID;
    }
}
