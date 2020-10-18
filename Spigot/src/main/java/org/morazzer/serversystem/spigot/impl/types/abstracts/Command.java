package org.morazzer.serversystem.spigot.impl.types.abstracts;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * @author Morazzer
 * @since Date 08.10.2020 18:21:30
 */
public abstract class Command extends org.bukkit.command.Command {
    public Command(@NotNull String name) {
        super(name);
        registerCommand();
    }

    public Command(@NotNull String name, String permission, String... alias) {
        super(name);
        super.setPermission(permission);
        super.setAliases(Arrays.asList(alias));
        registerCommand();
    }

    public Command(@NotNull String name, String permission) {
        super(name);
        super.setPermission(permission);
        registerCommand();
    }

    private void registerCommand() {
        Bukkit.getCommandMap().register("serversystem", this);
    }

    @Override
    public final boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        execute(commandSender, strings);
        return true;
    }

    public abstract void execute(@NotNull CommandSender commandSender, @NotNull String[] aliases);
}
