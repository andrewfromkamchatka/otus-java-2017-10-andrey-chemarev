package ru.chemarev.andrey;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ru.chemarev.andrey.cache.CacheEngineImpl;
import ru.chemarev.andrey.core.AddressDataSet;
import ru.chemarev.andrey.core.DBService;
import ru.chemarev.andrey.core.UserDataSet;
import ru.chemarev.andrey.hibernate.DBServiceHibernateImpl;
import ru.chemarev.andrey.jetty.LoginServlet;
import ru.chemarev.andrey.jetty.StatisticsServlet;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private final static int PORT = 8090;
    private final static String STATIC_DIR = "static";

    public static void main(String[] args) throws Exception {
        CacheEngineImpl<Long, UserDataSet> cache =
                new CacheEngineImpl<>(50, 30 * 1000, 0, false);

        DBService dbService = new DBServiceHibernateImpl(cache);

        Server server = buildServer(dbService, cache);
        server.start();

        cacheWork(dbService);
        server.join();
    }

    private static Server buildServer(DBService dbService, CacheEngineImpl<Long, UserDataSet> cache) {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(STATIC_DIR);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(LoginServlet.class, "/login");
        context.addServlet(new ServletHolder(new StatisticsServlet(cache)), "/statistics");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        return server;
    }

    private static void cacheWork(DBService dbService) {

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            dbService.save(generateUser());

            if ( i % 50 == 0) {

                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int j = 1; j < 25; j++) {
                    dbService.read(ThreadLocalRandom.current().nextInt(i-50, i));
                }
            }
        }
    }

    private static UserDataSet generateUser() {
        UserDataSet userDataSet = new UserDataSet();

        userDataSet.setName("Andrew");
        userDataSet.setAge(29);
        userDataSet.setAddress(new AddressDataSet("MyAddressStreet"));

        userDataSet.addPhone("8-789-456-12-23");
        userDataSet.addPhone("1-234-567-78-89");

        return userDataSet;
    }
}
