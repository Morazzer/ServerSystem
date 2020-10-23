package org.morazzer.serversystem.spigot.impl.api;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.morazzer.serversystem.spigot.InstanceManager;
import org.morazzer.serversystem.spigot.ServerSystem;
import org.morazzer.serversystem.spigot.api.Api;
import org.morazzer.serversystem.spigot.impl.ServerSystemImpl;
import org.morazzer.serversystem.spigot.impl.api.models.SignUpModel;
import org.morazzer.serversystem.spigot.impl.api.models.TokenModel;

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
    public HttpResponse executeRaw(HttpRequestBase requestBase) {

        requestBase.addHeader("token", ServerSystemImpl.getToken());

        try {
            return getClient().execute(requestBase);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void reconnect() {
        HttpPost login = new HttpPost(getApiPath() + "auth/signin");

        SignUpModel data = new SignUpModel();

        data.username = ServerSystem.getInstance().getProperties().getProperty("api_username");
        data.password = ServerSystem.getInstance().getProperties().getProperty("api_password");

        try {

            login.setEntity(new StringEntity(new Gson().toJson(data)));

            TokenModel tokenModel = new Gson().fromJson(execute(login), TokenModel.class);
            ServerSystemImpl.setToken(tokenModel.token);

            System.out.println(tokenModel.token);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}
