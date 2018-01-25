package ru.chemarev.andrey.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.chemarev.andrey.cache.CacheEngine;
import ru.chemarev.andrey.cache.CacheEngineImpl;
import ru.chemarev.andrey.cache.MyElement;
import ru.chemarev.andrey.core.*;
import ru.chemarev.andrey.hibernate.dao.UserDAO;

import java.util.function.Function;

public class DBServiceHibernateImpl implements DBService {
    protected SessionFactory sessionFactory;
    private CacheEngine<Long, UserDataSet> userCache = new CacheEngineImpl<>(10, 5000, 30000, false);

    public DBServiceHibernateImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(DataSet.class);
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:~/test;user=sa");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    protected SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public UserDataSet read(long id) {
        MyElement<Long, UserDataSet> element = userCache.get(id);
        UserDataSet result = element == null ? null : element.getValue();

        if ( result == null ) {
            result = runInSession( session -> {
                UserDAO dao = new UserDAO(session);
                return dao.read(id);
            });

            addUserToCache(result);
        }

        return result;
    }

    @Override
    public void save(UserDataSet dataSet) {
        runInSession( session -> {
            UserDAO dao = new UserDAO(session);
            dao.save(dataSet);
            return null;
        });

        addUserToCache(dataSet);
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
        userCache.dispose();
    }

    private void addUserToCache(UserDataSet dataSet) {
        userCache.put(new MyElement<>(dataSet.getId(), dataSet));
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    @Override
    public void close() throws Exception {
        shutdown();
    }
}
