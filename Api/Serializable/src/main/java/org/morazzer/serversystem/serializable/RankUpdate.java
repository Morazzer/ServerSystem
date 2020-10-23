package org.morazzer.serversystem.serializable;

import org.morazzer.serversystem.serializable.enums.RankUpdeType;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * @author Morazzer
 * @since Date 11.10.2020 19:50:50
 */
public class RankUpdate implements Serializable {

    public RankUpdeType type;
    public UUID uuid;
    public UUID inheritUUID;
    public String name;
    public String prefix;
    public String color;
    public List<String> permissions;
    public int level;
    public int tabLevel;

}
