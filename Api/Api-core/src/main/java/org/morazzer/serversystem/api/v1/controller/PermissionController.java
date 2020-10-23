package org.morazzer.serversystem.api.v1.controller;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.morazzer.serversystem.api.v1.models.permission.PermissionAddModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Morazzer
 * @since Date 08.10.2020 18:08:52
 */
public class PermissionController {

    private final static Gson gson = new Gson();
    public final static List<String> permissions = new ArrayList<>();

    public static void addPermissions(Context context) {
        PermissionAddModel data = context.bodyAsClass(PermissionAddModel.class);

        permissions.addAll(Arrays.stream(data.permissions).filter(perm -> !permissions.contains(perm)).collect(Collectors.toList()));

        context.result(gson.toJson(permissions));
    }

    public static void listPermissions(Context context) {
        context.result(gson.toJson(permissions));
    }

}
