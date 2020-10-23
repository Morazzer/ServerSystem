package org.morazzer.serversystem.api.v1.models.rank;

import org.morazzer.serversystem.api.types.dataclasses.Rank;

import java.util.List;
import java.util.UUID;

/**
 * @author Morazzer
 * @since Date 11.10.2020 23:42:25
 */
public class RankUpdateModel {

    public String uuid;
    public String name;
    public String prefix;
    public String color;
    public int level;
    public int tabLevel;
    public String inheritUuid;
    public Rank inherit;
    public List<String> permissions;

}
