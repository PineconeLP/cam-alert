package com.pineconelp.mc.utilities;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

public class DatabaseConnectionFactory {
    private String connectionString;

    private JdbcPooledConnectionSource connection;

    public DatabaseConnectionFactory(String connectionString) {
        this.connectionString = connectionString;
    }

    public JdbcPooledConnectionSource createConnection() throws SQLException {
        if(connection == null) {
            connection = new JdbcPooledConnectionSource(connectionString);
        }

        return connection;
    }
}