package org.morazzer.serversystem.spigot.impl.types.abstracts;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * @author Morazzer
 * @since Date 08.10.2020 18:23:41
 */
public abstract class PlayerCommand extends Command {

    public PlayerCommand(@NotNull String name) {
        super(name);
        registerCommand();
    }

    public PlayerCommand(@NotNull String name, String permission, String... alias) {
        super(name);
        super.setPermission(permission);
        super.setAliases(Arrays.asList(alias));
        registerCommand();
    }

    public PlayerCommand(@NotNull String name, String permission) {
        super(name);
        super.setPermission(permission);
        registerCommand();
    }

    private void registerCommand() {
        Bukkit.getCommandMap().register("serversystem", this);
    }

    @Override
    public final boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player)
            execute((Player) commandSender, strings);

        return true;
    }

    public abstract void execute(@NotNull Player commandSender, @NotNull String[] aliases);

}
