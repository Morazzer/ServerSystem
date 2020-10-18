package org.morazzer.serversystem.spigot.impl.api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.morazzer.serversystem.spigot.InstanceManager;
import org.morazzer.serversystem.spigot.ServerSystem;
import org.morazzer.serversystem.spigot.api.Api;
import org.morazzer.serversystem.spigot.impl.ServerSystemImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

/**
 * @author Morazzer
 * @since Date 14.10.2020 15:50:42
 */
public class ApiImpl implements Api {

    public ApiImpl() {
        InstanceManager.setInstance(this);
    }

    @Override
    public HttpClient getClient() {
        return HttpClientBuilder.create().build();
    }


    @Override
    public String getApiPath() {
        return "http://" + ServerSystem.getInstance().getProperties().getProperty("api_host") + ":" + ServerSystem.getInstance().getProperties().getProperty("api_port") + "/api/v1/";
    }
    @Override
    public String getApiPath(String path) {
        return getApiPath() + (path.startsWith("/") ? path.substring(1) : path);
    }

    @Override
    public String execute(HttpRequestBase requestBase) {

        requestBase.addHeader("token", ServerSystemImpl.getToken());
        try {

            Stream<String> lines = new BufferedReader(new InputStreamReader(getClient().execute(requestBase).getEntity().getContent())).lines();

            StringBuilder stringBuilder = new StringBuilder();

            lines.forEach(stringBuilder::append);

            return stringBuilder.toString();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    @Override
    public HttpResponse executeS(HttpRequestBase requestBase) {

        requestBase.addHeader("token", ServerSystemImpl.getToken());

        try {
            return getClient().execute(requestBase);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


}
