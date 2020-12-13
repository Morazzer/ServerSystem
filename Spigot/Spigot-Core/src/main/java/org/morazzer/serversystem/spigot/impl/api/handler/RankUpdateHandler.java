package org.morazzer.serversystem.spigot.impl.api.handler;

import org.morazzer.serversystem.serializable.RankUpdate;
import org.morazzer.serversystem.spigot.api.RankApi;
import org.morazzer.serversystem.spigot.api.types.interfaces.Rank;
import org.morazzer.serversystem.spigot.impl.api.RankApiImpl;
import org.morazzer.serversystem.spigot.impl.api.types.dataclas.RankImpl;

/**
 * @author Morazzer
 * @since Date 13.10.2020 21:56:48
 */
public class RankUpdateHandler {

    public static void handle(RankUpdate update) {
        switch (update.type) {
            case CREATE: {
                Rank rank = RankApi.getInstance().addRankAndGet(update.uuid);

                System.out.println(rank.getName());
            }
            case NAME: {
                RankImpl rank = (RankImpl) RankApi.getInstance().getRank(update.uuid);
                rank.setName(update.name);
                break;
            }
        }
    }

}
