package org.morazzer.serversystem.api.types.v1.dataclasses;

import io.javalin.http.InternalServerErrorResponse;
import org.morazzer.serversystem.api.DataSource;
import org.morazzer.serversystem.api.v1.models.user.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Morazzer
 * @since Date 07.10.2020 23:11:36
 */
public class User {

    private final static List<UUID> registeredUsers = new ArrayList<>();

    public static void initialize() {
        ResultSet set = DataSource.executeQuery("SELECT uuid FROM players");
        try {
            while (set.next()) {
                registeredUsers.add(UUID.fromString(set.getString("uuid")));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static AtomicInteger integer = new AtomicInteger();
    private static ConcurrentHashMap<UUID, User> userList = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<UUID, User> getUserList() {
        return userList;
    }

    public static List<User> getUsers() {
        return new ArrayList<>(userList.values());
    }

    public static User of(String uuid) {
        return of(UUID.fromString(uuid));
    }


    public static User of(UUID uuid) {
        User user = userList.get(uuid);

        if (user == null)
            throw new InternalServerErrorResponse("User isn't Registered");

        return user;
    }

    public static User unregister(String uuid) {
        return unregister(UUID.fromString(uuid));
    }

    public static User unregister(UUID uuid) {

        User user = userList.remove(uuid);

        if (user == null)
            throw new InternalServerErrorResponse("User isn't Registered");

        return user;
    }

    public static User register(UserModel userModel) {
        userList.remove(UUID.fromString(userModel.uuid));
        User user = new User();
        if (!registeredUsers.contains(UUID.fromString(userModel.uuid)))
            user.newJoined = true;
        user.name = userModel.name;
        user.uuid = UUID.fromString(userModel.uuid);
        user.rank = UUID.fromString(userModel.rank);
        user.vanished = userModel.vanished;
        user.id = integer.incrementAndGet();
        user.server = "unknown";
        userList.put(user.uuid, user);
        return user;
    }

    public boolean newJoined;
    public boolean vanished;
    public String name;
    public UUID uuid;
    public int id;
    public String server;
    public UUID rank;
    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isVanished() {
        return vanished;
    }

    public int getId() {
        return id;
    }

    public String getServer() {
        return server;
    }
}
