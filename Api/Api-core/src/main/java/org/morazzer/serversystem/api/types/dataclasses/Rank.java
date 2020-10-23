package org.morazzer.serversystem.api.types.dataclasses;

import io.javalin.http.InternalServerErrorResponse;
import org.morazzer.serversystem.api.DataSource;
import org.morazzer.serversystem.api.v1.models.rank.RankModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Morazzer
 * @since Date 07.10.2020 23:13:22
 */
public class Rank {

    private static final ConcurrentHashMap<UUID, Rank> ranks = new ConcurrentHashMap<>();

    public static void loadInherites() {
        ranks.values().stream().filter(Objects::nonNull).forEach(rank -> {
            if (rank.inheritUuid != null)
                rank.inherit = Rank.of(rank.inheritUuid);
        });
    }

    public static void loadPermissions() {
        ranks.values().forEach(rank -> {
            final List<String> permissions = new ArrayList<>();

            try {
                ResultSet set = DataSource.getConnection().createStatement().executeQuery("select  * from `permissions` where `rank` = '" + rank.uuid.toString() + "'");

                while (set.next()) {
                    permissions.add(set.getString("permission"));
                }

                set.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            rank.permissions = permissions;
        });
    }

    public static Rank of(String uuid) {
        return of(UUID.fromString(uuid));
    }

    public static Rank of(UUID uuid) {
        Rank rank = ranks.get(uuid);

        if (rank == null)
            throw new InternalServerErrorResponse("Rank dosn't exist");

        return rank;
    }

    public static void loadRanks() {
        try {
            ResultSet set = DataSource.getConnection().createStatement().executeQuery("select  * from `ranks`");

            while (set.next()) {
                Rank rank = new Rank();

                rank.uuid = UUID.fromString(set.getString("uuid"));
                String inherit = set.getString("inherit");
                if (inherit != null && !inherit.equalsIgnoreCase("null"))
                    rank.inheritUuid = UUID.fromString(inherit);
                rank.name = set.getString("name");
                rank.color = set.getString("color");
                rank.prefix = set.getString("prefix");
                rank.level = set.getInt("level");
                rank.tabLevel = set.getInt("tab_level");

                ranks.put(rank.uuid, rank);
            }

            set.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        loadInherites();
        loadPermissions();
    }

    public static Rank addRank(RankModel data, UUID uuid) {
        Rank rank = new Rank();

        rank.color = data.color;
        if (data.inherit != null)
            rank.inheritUuid = UUID.fromString(data.inherit);
        rank.level = data.level;
        rank.name = data.name;
        rank.prefix = data.prefix;
        rank.tabLevel = data.tabLevel;
        rank.uuid = uuid;

        rank.permissions = new ArrayList<>();

        ranks.put(uuid, rank);

        loadInherites();

        try {
            DataSource.getConnection().createStatement().execute("insert into `ranks` (uuid, name, prefix, color, level, tab_level, inherit)" +
                    "values ('" + rank.uuid.toString() + "', '" + rank.name + "', '" + rank.prefix + "', '" + rank.color + "', " + rank.level + ", " + rank.tabLevel + ", '" + (rank.inheritUuid == null ? null : rank.inheritUuid.toString()) + "')");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return rank;
    }

    public UUID uuid;
    public String name;
    public String prefix;
    public String color;
    public int level;
    public int tabLevel;
    public UUID inheritUuid;
    public Rank inherit;
    public List<String> permissions;

    public static List<Rank> getRanks() {
        return new ArrayList<>(ranks.values());
    }

    public void addPermission(String permission) {
        if (!permissions.contains(permission))
            try {
                DataSource.getConnection().createStatement().execute("insert into `permissions`(`rank`, permission) VALUES ('" + uuid.toString() + "', '" + permission + "')");
                permissions.add(permission);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        else
            throw new InternalServerErrorResponse("Rank already has Permission");
    }

    public void removePermission(String permission) {
        if (permissions.contains(permission))
            try {
                DataSource.getConnection().createStatement().execute("delete from permissions where permission = '" + permission + "'");
                permissions.remove(permission);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        else
            throw new InternalServerErrorResponse("Rank dosn't have Permission");
    }

    public void setInherit(UUID inherit) {
        inheritUuid = inherit;

        try {
            DataSource.getStatement().execute("update `ranks` set inherit = '" + inheritUuid.toString() + "' where uuid = '" + uuid.toString() + "'");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        loadInherit();
    }

    public void loadInherit() {
        if (ranks.containsKey(inheritUuid)) {
            System.out.println();
            inherit = ranks.get(inheritUuid);
        }
    }

    public void resetInherit() {
        inheritUuid = null;
        inherit = null;

        try {
            DataSource.getStatement().execute("update `ranks` set inherit = 'null' where uuid = '" + uuid.toString() + "'");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void delete() {
        ranks.remove(uuid);

        ranks.values().forEach(rank -> {
            if (rank.inheritUuid.equals(uuid)) {
                rank.resetInherit();
            }
        });

        try {
            DataSource.getStatement().execute("delete from `ranks` where uuid = '" + uuid.toString() + "'");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
