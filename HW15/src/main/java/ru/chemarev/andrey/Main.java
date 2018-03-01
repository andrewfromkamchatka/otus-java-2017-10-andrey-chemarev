package ru.chemarev.andrey;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.chemarev.andrey.app.DBService;
import ru.chemarev.andrey.app.FrontendService;
import ru.chemarev.andrey.app.MessageSystemContext;
import ru.chemarev.andrey.app.CacheEngine;
import ru.chemarev.andrey.db.cache.CacheEngineImpl;
import ru.chemarev.andrey.db.DBServiceHibernateImpl;
import ru.chemarev.andrey.db.dataset.UserDataSet;
import ru.chemarev.andrey.front.FrontendServiceImpl;
import ru.chemarev.andrey.front.LoginServlet;
import ru.chemarev.andrey.front.StatisticsServlet;
import ru.chemarev.andrey.front.WebSocketStatisticsServlet;
import ru.chemarev.andrey.messageSystem.Address;
import ru.chemarev.andrey.messageSystem.MessageSystem;

public class Main {
    private final static int PORT = 8090;
    private final static String STATIC_DIR = "static";

    public static void main(String[] args) throws Exception {
        MessageSystem messageSystem = new MessageSystem();

        MessageSystemContext context = new MessageSystemContext(messageSystem);
        Address frontAddress = new Address("Frontend");
        context.setFrontAddress(frontAddress);
        Address dbAddress = new Address("DB");
        context.setDbAddress(dbAddress);

        FrontendService frontendService = new FrontendServiceImpl(context, frontAddress);
        frontendService.init();

        CacheEngine userCache = new CacheEngineImpl<Long, UserDataSet>(10, 5000, 30000, false);
        DBService dbService = new DBServiceHibernateImpl(context, dbAddress, userCache);
        dbService.init();

        messageSystem.start();

        Server server = buildServer(frontendService);
        server.start();
        emulate(dbService);
        server.join();

    }

    private static Server buildServer(FrontendService frontendService) {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(STATIC_DIR);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(LoginServlet.class, "/");
        context.addServlet(LoginServlet.class, "/login");
        context.addServlet(StatisticsServlet.class, "/statistics");
        context.addServlet(new ServletHolder(new WebSocketStatisticsServlet(frontendService)), "/websocket-statistics");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        return server;
    }

    private static void emulate(DBService dbService) {
        Emulation emulation = new Emulation(dbService);
        emulation.cacheWork();
    }
}
