package com.pineconelp.mc.seeders;

import java.sql.SQLException;

import com.google.inject.Inject;
import com.pineconelp.mc.utilities.DatabaseSessionFactory;

import org.hibernate.Session;

public class CamAlertDatabaseSeeder {

    private DatabaseSessionFactory sessionFactory;

    @Inject
    public CamAlertDatabaseSeeder(DatabaseSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void seedDatabase() throws SQLException {
        try(Session session = sessionFactory.createSession()) { } 
    }
}