package org.morazzer.serversystem.serializable;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Morazzer
 * @since Date 17.10.2020 15:42:53
 */
public class UserUpdate implements Serializable {

    public UUID uuid;
    public UUID rankUUID;

}
