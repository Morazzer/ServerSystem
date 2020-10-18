package org.morazzer.serversystem.spigot;

import org.jetbrains.annotations.NotNull;

/**
 * @author Morazzer
 * @since Date 15.10.2020 19:29:55
 */
public interface System {

    void load();
    @NotNull
    static System getInstance() { return null; /* Implemented in Runtime */ }

}
