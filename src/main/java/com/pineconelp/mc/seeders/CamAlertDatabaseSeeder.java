package com.pineconelp.mc.seeders;

import java.sql.SQLException;

import com.google.inject.Inject;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.pineconelp.mc.services.camera_repositories.sqlite.CameraEntity;
import com.pineconelp.mc.utilities.DatabaseConnectionFactory;

public class CamAlertDatabaseSeeder {

    private DatabaseConnectionFactory connectionFactory;

    @Inject
    public CamAlertDatabaseSeeder(DatabaseConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void seedDatabase() throws SQLException {
        ConnectionSource connection = connectionFactory.createConnection();
        TableUtils.createTableIfNotExists(connection, CameraEntity.class);
    }
}