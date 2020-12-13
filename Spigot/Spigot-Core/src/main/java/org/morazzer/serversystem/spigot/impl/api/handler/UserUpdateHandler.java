package org.morazzer.serversystem.spigot.impl.api.handler;

import org.morazzer.serversystem.serializable.UserUpdate;

public class UserUpdateHandler {

    public static void handle(final UserUpdate userUpdate) {
        switch (userUpdate.userUpdateType) {
            case UPDATERANK: {

            }
            case UPDATESERVER: {

            }
            case UPDATEVANISH: {
                
            }
        }
    }

}
