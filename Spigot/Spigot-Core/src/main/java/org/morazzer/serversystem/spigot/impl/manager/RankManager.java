package org.morazzer.serversystem.spigot.impl.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.HttpGet;
import org.morazzer.serversystem.spigot.ServerSystem;
import org.morazzer.serversystem.spigot.api.types.interfaces.Rank;
import org.morazzer.serversystem.spigot.impl.ServerSystemImpl;
import org.morazzer.serversystem.spigot.impl.api.models.RankModel;
import org.morazzer.serversystem.spigot.impl.api.types.dataclas.RankImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Morazzer
 * @since Date 10.10.2020 09:53:56
 */
public class RankManager {

    private static final Gson gson = new Gson();

    private static final List<Rank> ranks = new ArrayList<>();
    private static final ConcurrentHashMap<UUID, Rank> rankMap = new ConcurrentHashMap<>();

    public static void initialize() {
        HttpGet listRanks = new HttpGet(ServerSystem.getInstance().getApiPath("/rank/list"));

        String response = ServerSystem.getInstance().execute(listRanks);

        JsonArray jsonArray = new JsonParser().parse(response).getAsJsonArray();

        jsonArray.forEach(jsonElement -> {
            if (jsonElement instanceof JsonObject) {
                RankModel rankModel = gson.fromJson(jsonElement, RankModel.class);
                ranks.add(new RankImpl(rankModel));
            }
        });

        ranks.forEach(rank -> {
            rankMap.put(rank.getUUID(), rank);
        });

    }

    public static void loadInherites() {
        ranks.stream().map(rank -> (RankImpl) rank).forEach(RankImpl::loadInherit);
    }

    public static List<Rank> getRanks() {
        return ranks;
    }

    public static ConcurrentHashMap<UUID, Rank> getRankMap() {
        return rankMap;
    }

    public static void add(RankModel model) {
        Rank rank = new RankImpl(model);
        ranks.add(rank);

        rankMap.put(rank.getUUID(), rank);
    }
}
