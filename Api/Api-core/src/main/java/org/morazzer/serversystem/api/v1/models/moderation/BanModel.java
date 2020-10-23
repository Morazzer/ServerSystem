package org.morazzer.serversystem.api.v1.models.moderation;

/**
 * @author Morazzer
 * @since Date 11.10.2020 23:33:36
 */
public class BanModel {

    public String uuid;
    public String reason;
    public String banner;
    public long until;
    public boolean perma;

}
