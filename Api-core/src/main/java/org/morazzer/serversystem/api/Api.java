package org.morazzer.serversystem.api;

import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.staticfiles.Location;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.Session;
import org.eclipse.jetty.server.session.SessionCache;
import org.eclipse.jetty.server.session.SessionHandler;
import org.morazzer.serversystem.api.db.MySqlSetup;
import org.morazzer.serversystem.api.db.SqLiteSetup;
import org.morazzer.serversystem.api.types.dataclasses.Rank;
import org.morazzer.serversystem.api.types.dataclasses.Token;
import org.morazzer.serversystem.api.v1.Websocket;
import org.morazzer.serversystem.api.v1.controller.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * @author Morazzer
 * @since Date 05.10.2020 19:34:47
 */
public class Api {

    private static Api api;
    private final Javalin javalin;
    private final List<Session> sessions;
    private final ConcurrentHashMap<Integer, String> userMap;
    private final List<RequestHandler> requestHandlers;
    public final ConcurrentHashMap<String, Token> tokensUser;
    public final List<String> tokens;

    public Api(boolean enableWebPage, boolean internalSite, String webPageName) {
        this(false, enableWebPage, internalSite, webPageName);
    }

    public Api(boolean enableWebPage, String webPageName) {
        this(false, enableWebPage, true, webPageName);
    }

    public Api(boolean enableDevLogging) {
        this(enableDevLogging, false, true, "");
    }

    public Api() {
        this(false, false, true, "");
    }

    public Api(boolean enableDevLogging, boolean enablewebPage, boolean internalSite, String webPageName) {

        if (!DataSource.isMySql()) {
            SqLiteSetup.setup();
        } else {
            MySqlSetup.setup();
        }

        Rank.loadRanks();

        this.tokens = new ArrayList<>();
        this.tokensUser = new ConcurrentHashMap<>();
        this.requestHandlers = new ArrayList<>();
        this.userMap = new ConcurrentHashMap<>();
        this.sessions = new ArrayList<>();

        try {
            ResultSet resultSet = DataSource.getConnection().createStatement().executeQuery("SELECT * FROM `users`");

            while (resultSet.next()) {
                userMap.put(resultSet.getInt("id"), resultSet.getString("username"));
            }
        } catch (SQLException ignored) {
        }

        api = this;
        javalin = Javalin.create(config -> {
            config.defaultContentType = "application/json";
            config.showJavalinBanner = false;
            config.enableCorsForAllOrigins();
            config.sessionHandler(() -> {
                SessionHandler sessionHandler = new SessionHandler();

                SessionCache sessionCache = new DefaultSessionCache(sessionHandler);
                sessionCache.setSessionDataStore(DataSource.getSessionDataStoreFactory().getSessionDataStore(sessionHandler));

                sessionHandler.setSessionCache(sessionCache);
                sessionHandler.setHttpOnly(true);
                sessionHandler.setMaxInactiveInterval(600);
                sessionHandler.setRefreshCookieAge(172800);
                return sessionHandler;
            });
            if (enablewebPage) {
                config.addStaticFiles(webPageName, internalSite ? Location.CLASSPATH : Location.EXTERNAL);
                config.addSinglePageRoot("/", webPageName + "/index.html");
            }
            if (enableDevLogging)
                config.enableDevLogging();
        });
        javalin.routes(() -> {
            path("/api", () -> {
                before(ctx -> {
                    ctx.res.setHeader("Server", "ServerSystem Backend");
                    if (enableDevLogging) ctx.res.setHeader("path", ctx.fullUrl());
                    if (enableDevLogging) requestHandlers.forEach(requestHandler -> requestHandler.handleRequest(ctx));
                    if (ctx.path().contains("/auth")) return;
                    if (ctx.header("token") == null || !tokens.contains(ctx.header("token"))) throw new UnauthorizedResponse("Token isn't valid");
                    if (!enableDevLogging) requestHandlers.forEach(requestHandler -> requestHandler.handleRequest(ctx));
                });
                path("/v1", () -> {
                    path("/auth", () -> {
                        post("/signin", AuthController::signin);
                        post("/signout", AuthController::signout);
                    });
                    path("/user", () -> {
                        get("/list", UserConrtoller::list);
                        put("/register", UserConrtoller::registerUser);
                        patch("/vanish", UserConrtoller::changeVanished);
                        patch("/server", UserConrtoller::changeServer);
                        delete("/remove", UserConrtoller::removeUser);
                        get("/rank", UserConrtoller::getRank);
                        get("/get", UserConrtoller::getUser);
                    });
                    path("/rank", () -> {
                        put("/create", RankController::create);
                        get("/list", RankController::list);
                        get("/get", RankController::getRankGET);
                        patch("/get", RankController::getRank);
                        patch("/parent", RankController::setInherit);
                        patch("/name", RankController::updateName);
                        delete("/parent", RankController::removeParent);
                        delete("/delete", RankController::deleteRank);
                        path("/permissions", () -> {
                            patch("/add", RankController::addPermission);
                            patch("/remove", RankController::removePermission);
                        });
                    });
                    path("/permissions", () -> {
                        put("/add", PermissionController::addPermissions);
                        get("/list", PermissionController::listPermissions);
                    });
                    path("/moderation", () -> {
                        delete("/ban", ModerationController::ban);
                    });
                });
            });
        });

        new Websocket();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(Api.class.getClassLoader());
        javalin.start(DataSource.getApiHost(), DataSource.getApiPort());
        Thread.currentThread().setContextClassLoader(classLoader);

        Runtime.getRuntime().addShutdownHook(new Thread(Websocket::publishShutdown));
    }

    public Javalin getJavalin() {
        return javalin;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public static Api getApi() {
        return api;
    }

    public void addRequestHandler(RequestHandler requestHandler) {
        this.requestHandlers.add(requestHandler);
    }

    public ConcurrentHashMap<Integer, String> getUserMap() {
        return userMap;
    }
}
