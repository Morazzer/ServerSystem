package org.morazzer.serversystem.spigot.impl.commands;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.morazzer.serversystem.spigot.ServerSystem;
import org.morazzer.serversystem.spigot.impl.ServerSystemImpl;
import org.morazzer.serversystem.spigot.impl.types.abstracts.Command;

import java.util.Arrays;

/**
 * @author Morazzer
 * @since Date 08.10.2020 18:21:15
 */
@org.morazzer.serversystem.spigot.impl.types.annotations.Command
public class TestCommand extends Command {
    public TestCommand() {
        super("test");
    }

    @Override
    public void execute(@NotNull CommandSender commandSender, @NotNull String[] aliases) {
        HttpPut httpPut = new HttpPut(ServerSystem.getInstance().getApiPath("/permissions/add"));

        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        Bukkit.getPluginManager().getPermissions().stream().map(Permission::getName).forEach(jsonArray::add);
        Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(Plugin::getDescription)
                .map(PluginDescriptionFile::getPermissions).findFirst().ifPresent(list -> {
            list.stream().map(Permission::getName).forEach(jsonArray::add);
        });

        jsonObject.add("permissions", jsonArray);

        httpPut.setEntity(new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON));

        ServerSystem.getInstance().getWebsocket().send("test");
    }
}
