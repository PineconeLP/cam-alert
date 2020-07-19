package com.pineconelp.mc.seeders;

import java.sql.SQLException;

import com.google.inject.Inject;

public class Seeder {

    private CamAlertDatabaseSeeder databaseSeeder;
    private ICamAlertSettingsSeeder settingsSeeder;

    @Inject
    public Seeder(CamAlertDatabaseSeeder databaseSeeder, ICamAlertSettingsSeeder settingsSeeder) {
        this.databaseSeeder = databaseSeeder;
        this.settingsSeeder = settingsSeeder;
    }

    public void seed() throws SQLException {
        databaseSeeder.seedDatabase();
        settingsSeeder.seedSettings();
    }
}