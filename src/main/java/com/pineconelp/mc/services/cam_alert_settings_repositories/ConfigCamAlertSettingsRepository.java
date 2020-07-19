package com.pineconelp.mc.services.cam_alert_settings_repositories;

import com.google.inject.Inject;
import com.pineconelp.mc.models.CamAlertSettings;

import org.bukkit.plugin.Plugin;

public class ConfigCamAlertSettingsRepository implements ICamAlertSettingsRepository {

    private Plugin plugin;

    @Inject
    public ConfigCamAlertSettingsRepository(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public CamAlertSettings loadSettings() {
        return null;
    }

    @Override
    public void saveSettings(CamAlertSettings settings) {
    }
}