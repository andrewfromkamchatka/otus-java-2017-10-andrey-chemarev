package ru.chemarev.andrey.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.chemarev.andrey.app.DBService;
import ru.chemarev.andrey.app.MessageSystemContext;
import ru.chemarev.andrey.app.CacheEngine;
import ru.chemarev.andrey.db.cache.MyElement;
import ru.chemarev.andrey.db.dao.UserDAO;
import ru.chemarev.andrey.db.dataset.AddressDataSet;
import ru.chemarev.andrey.db.dataset.DataSet;
import ru.chemarev.andrey.db.dataset.PhoneDataSet;
import ru.chemarev.andrey.db.dataset.UserDataSet;
import ru.chemarev.andrey.messageSystem.Address;
import ru.chemarev.andrey.messageSystem.MessageSystem;

import java.util.function.Function;

public class DBServiceHibernateImpl implements DBService {
    private final Address address;
    private final MessageSystemContext context;

    protected SessionFactory sessionFactory;
    private CacheEngine<Long, UserDataSet> userCache;

    public DBServiceHibernateImpl(MessageSystemContext context, Address address, CacheEngine<Long, UserDataSet> userCache) {
        this.context = context;
        this.address = address;
        this.userCache = userCache;
        configureHibernate();
    }

    private void configureHibernate() {
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

        sessionFactory = configureHibernate(configuration);
    }

    protected SessionFactory configureHibernate(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    @Override
    public UserDataSet getUser(long id) {
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
    public CacheEngine<Long, UserDataSet> getUserCache() {
        return userCache;
    }

    @Override
    public void saveUser(UserDataSet dataSet) {
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
