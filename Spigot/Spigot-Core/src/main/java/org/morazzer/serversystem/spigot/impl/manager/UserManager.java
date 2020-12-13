package org.morazzer.serversystem.spigot.impl.manager;

import org.jetbrains.annotations.NotNull;
import org.morazzer.serversystem.serializable.UserUpdate;
import org.morazzer.serversystem.spigot.api.types.interfaces.OfflineUser;
import org.morazzer.serversystem.spigot.api.types.interfaces.User;
import org.morazzer.serversystem.spigot.impl.api.types.dataclas.OfflineUserImpl;
import org.morazzer.serversystem.spigot.impl.api.types.dataclas.UserImpl;
import org.morazzer.serversystem.spigot.impl.api.types.enums.OnlineStatus;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Morazzer
 * @since Date 17.10.2020 17:28:32
 */
public class UserManager {

    private final static ConcurrentHashMap<UUID, OfflineUser> usersOnServer = new ConcurrentHashMap<>();
    private final static List<User> users = new ArrayList<>();
    private final static ConcurrentHashMap<UUID, User> userMap = new ConcurrentHashMap<>();

    public static void add(UserUpdate update) {
        usersOnServer.put(update.uuid, new OfflineUserImpl(update));
    }

    public static void remove(UserUpdate update) {
        users.remove(userMap.remove(update.uuid));
    }

    public static @NotNull OnlineStatus changeOnlineStatus(UUID uuid) {
        if (userMap.containsKey(uuid)) {

            users.remove(userMap.remove(uuid));

            return OnlineStatus.ONLINE;
        } else {
            User user = new UserImpl(usersOnServer.get(uuid));

            users.add(user);
            userMap.put(uuid, user);
            return OnlineStatus.OFFLINE;
        }
    }

    public static ConcurrentHashMap<UUID, OfflineUser> getUsersOnServer() {
        return usersOnServer;
    }
}
