package org.morazzer.serversystem.spigot.impl;

import org.morazzer.serversystem.spigot.InstanceManager;
import org.morazzer.serversystem.spigot.System;
import org.morazzer.serversystem.spigot.impl.manager.RankManager;

/**
 * @author Morazzer
 * @since Date 10.10.2020 09:53:23
 */
public class SystemImpl implements System {

    public void load() {
        InstanceManager.setInstance(this);
        loadRanks();
    }

    private void loadRanks() {
        RankManager.initialize();
        RankManager.loadInherites();
        //RankManager.getRanks().forEach(rank -> rank.getPermissions(true).forEach(java.lang.System.out::println));
    }

}
