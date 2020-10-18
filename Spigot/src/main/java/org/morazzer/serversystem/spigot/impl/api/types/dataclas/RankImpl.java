package org.morazzer.serversystem.spigot.impl.api.types.dataclas;

import org.bukkit.ChatColor;
import org.morazzer.serversystem.spigot.api.RankApi;
import org.morazzer.serversystem.spigot.api.types.interfaces.Rank;
import org.morazzer.serversystem.spigot.impl.api.RankApiImpl;
import org.morazzer.serversystem.spigot.impl.api.models.RankModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Morazzer
 * @since Date 10.10.2020 09:45:48
 */
public class RankImpl implements Rank{

    private String name;
    private String color;
    private final UUID uuid;
    private UUID inheritUUID;
    private String prefix;
    private List<String> permissions;
    private int level;
    private int tabLevel;
    private Rank inherit;

    @Override
    public String toString() {
        return "RankImpl{" +
                "name='" + name + '\'' +
                ", uuid=" + uuid +
                ", inheritUUID=" + inheritUUID +
                ", inherit=" + inherit +
                '}';
    }

    public RankImpl(RankModel model) {
        name = model.name;
        color = model.color;
        uuid = UUID.fromString(model.uuid);
        inheritUUID = model.inheritUuid != null ? UUID.fromString(model.inheritUuid) : null;
        prefix = model.prefix;
        permissions = model.permissions;
        level = model.level;
        tabLevel = model.level;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getColorcode() {
        return color;
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.valueOf(color.replaceAll("§", ""));
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Rank getInherit() {
        return inherit;
    }

    @Override
    public UUID getInheritUUID() {
        return inheritUUID;
    }

    @Override
    public List<String> getPermissions(boolean inherited) {

        List<String> permissions = new ArrayList<>(this.permissions);

        if (inherit != null) {
            permissions.addAll(inherit.getPermissions(true));
        }

        return permissions;
    }

    public void loadInherit() {
        inherit = RankApi.getInstance().getRank(inheritUUID);
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setInherit(Rank inherit) {
        this.inherit = inherit;
    }

    public void setInheritUUID(UUID inheritUUID) {
        this.inheritUUID = inheritUUID;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setTabLevel(int tabLevel) {
        this.tabLevel = tabLevel;
    }
}
