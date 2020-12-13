package org.morazzer.serversystem.spigot;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * @author Morazzer
 * @since Date 23.10.2020 21:07:08
 */
public class Setup {
    public static void before() {
        Collection<Player> players = new ArrayList<>();

        players.add(mockPlayer());

        when(Bukkit.getOnlinePlayers()).thenReturn(new ArrayList(players));
        when(Bukkit.getCommandMap()).thenReturn(mockCommandMap());
    }

    public static Player mockPlayer() {
        return mockPlayer(new RandomString(6).nextString());
    }

    public static Player mockPlayer(String name) {
        return mockPlayer(name, UUID.randomUUID());
    }

    public static Player mockPlayer(String name, UUID uuid) {
        Player player = mock(Player.class);

        when(player.getName()).thenReturn(name);
        when(player.getUniqueId()).thenReturn(uuid);


        return player;
    }

    public static Inventory mockInventory() {
        Inventory inventory = mock(Inventory.class);

        return inventory;
    }

    public static CommandMap mockCommandMap() {
        CommandMap commandMap = mock(CommandMap.class);

        return commandMap;
    }
}
