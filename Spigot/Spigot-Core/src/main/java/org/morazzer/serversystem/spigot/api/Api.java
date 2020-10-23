package org.morazzer.serversystem.spigot.api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.jetbrains.annotations.NotNull;
import org.morazzer.serversystem.spigot.InstanceManager;

/**
 * @author Morazzer
 * @since Date 15.10.2020 16:10:10
 */
public interface Api {

    @NotNull
    static Api getInstance() {
        return (Api) InstanceManager.getInstance(Api.class);
    }
    HttpClient getClient();
    String getApiPath();
    String getApiPath(String path);
    String execute(HttpRequestBase requestBase);
    HttpResponse executeRaw(HttpRequestBase requestBase);
    void reconnect();


}
