package com.pineconelp.mc.seeders;

import com.google.inject.Inject;

public class Seeder {
    
    private ICamAlertSettingsSeeder camAlertSettingsSeeder;

    @Inject
    public Seeder(ICamAlertSettingsSeeder camAlertSettingsSeeder) {
        this.camAlertSettingsSeeder = camAlertSettingsSeeder;
    }

    public void seed() {
        camAlertSettingsSeeder.seedSettings();
    }
}