package com.pineconelp.mc.utilities;

import java.sql.SQLException;
import java.util.Properties;

import com.pineconelp.mc.services.camera_repositories.database.CameraEntity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class DatabaseSessionFactory {
    private String connectionString;

    private SessionFactory sessionFactory;

    public DatabaseSessionFactory(String connectionString) {
        this.connectionString = connectionString;
    }

    public Session createSession() throws SQLException {
        if (sessionFactory == null) {

            Configuration configuration = new Configuration();

            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "org.h2.Driver");
            settings.put(Environment.URL, connectionString);
            settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
            settings.put(Environment.SHOW_SQL, "false");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.HBM2DDL_AUTO, "update");

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(CameraEntity.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        
        return sessionFactory.openSession();
    }
}