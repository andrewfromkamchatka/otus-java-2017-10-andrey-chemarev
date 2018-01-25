package ru.chemarev.andrey;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ru.chemarev.andrey.cache.CacheEngine;
import ru.chemarev.andrey.cache.CacheEngineImpl;
import ru.chemarev.andrey.cache.MyElement;
import ru.chemarev.andrey.jetty.LoginServlet;
import ru.chemarev.andrey.jetty.StatisticsServlet;

public class Main {

    private final static int PORT = 8090;
    private final static String STATIC_DIR = "static";
    private final static int IDLE_TIME = 30 * 1000;

    public static void main(String[] args) throws Exception {
        CacheEngine<Long, String> cache = new CacheEngineImpl<>(20, 0, IDLE_TIME, false);

        Server server = buildServer(cache);
        server.start();

        cacheWork(cache);
        server.join();
    }

    private static Server buildServer(CacheEngine<Long, String> cache) {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(STATIC_DIR);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(LoginServlet.class, "/login");
        context.addServlet(new ServletHolder(new StatisticsServlet(cache)), "/statistics");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        return server;
    }

    private static void cacheWork(CacheEngine<Long, String> cache) {
        for (int j = 1; j < Integer.MAX_VALUE; j++) {

            for (long i = 1; i <= 100; i++) {
                cache.put(new MyElement<>(i, Long.toString(i)));
            }

            try {
                Thread.sleep(IDLE_TIME / 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (long i = 1; i <= 100; i++) {
                cache.get(i);
            }

        }
    }
}
