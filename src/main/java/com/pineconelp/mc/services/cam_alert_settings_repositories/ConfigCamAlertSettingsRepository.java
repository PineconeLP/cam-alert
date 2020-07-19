package com.pineconelp.mc.services.cam_alert_settings_repositories;

import com.google.inject.Inject;
import com.pineconelp.mc.models.CamAlertSettings;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigCamAlertSettingsRepository implements ICamAlertSettingsRepository {
    private static final double DEFAULT_CAMERA_RANGE = 20;
    private static final double DEFAULT_NOTIFICATION_THRESHOLD = 1;
    private static final String DEFAULT_BLOCK_MATERIAL = Material.JACK_O_LANTERN.toString();
    private static final boolean DEFAULT_ENTITY_NOTIFICATIONS_ENABLED = true;

    private static final String SETTINGS_PATH = "settings";
    private static final String DEFAULT_SETTINGS_PATH = SETTINGS_PATH + ".default";
    private static final String DEFAULT_CAMERA_RANGE_PATH = DEFAULT_SETTINGS_PATH + ".cameraRange";
    private static final String DEFAULT_NOTIFICATION_THRESHOLD_PATH = DEFAULT_SETTINGS_PATH + ".notificationThresholdSeconds";
    private static final String DEFAULT_BLOCK_MATERIAL_PATH = DEFAULT_SETTINGS_PATH + ".cameraBlockMaterial";
    private static final String DEFAULT_ENTITY_NOTIFICATIONS_ENABLED_PATH = DEFAULT_SETTINGS_PATH + ".entityNotificationsEnabled";

    private Plugin plugin;
    private FileConfiguration config;

    @Inject
    public ConfigCamAlertSettingsRepository(Plugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();

        seed();
    }

    public void seed() {
        config.addDefault(DEFAULT_CAMERA_RANGE_PATH, DEFAULT_CAMERA_RANGE);
        config.addDefault(DEFAULT_NOTIFICATION_THRESHOLD_PATH, DEFAULT_NOTIFICATION_THRESHOLD);
        config.addDefault(DEFAULT_BLOCK_MATERIAL_PATH, DEFAULT_BLOCK_MATERIAL);
        config.addDefault(DEFAULT_ENTITY_NOTIFICATIONS_ENABLED_PATH, DEFAULT_ENTITY_NOTIFICATIONS_ENABLED);
        config.options().copyDefaults(true);

        plugin.saveConfig();
    }

    @Override
    public CamAlertSettings loadSettings() {
        double defaultCameraRange = config.getDouble(DEFAULT_CAMERA_RANGE_PATH, DEFAULT_CAMERA_RANGE);
        double defaultCameraNotificationThresholdSeconds = config.getDouble(DEFAULT_NOTIFICATION_THRESHOLD_PATH, DEFAULT_NOTIFICATION_THRESHOLD);

        Material defaultCameraBlockMaterial = Material.getMaterial(config.getString(DEFAULT_BLOCK_MATERIAL_PATH, DEFAULT_BLOCK_MATERIAL));
        if(defaultCameraBlockMaterial == null) {
            defaultCameraBlockMaterial = Material.getMaterial(DEFAULT_BLOCK_MATERIAL);
        }

        boolean entityNotificationsEnabled = config.getBoolean(DEFAULT_ENTITY_NOTIFICATIONS_ENABLED_PATH, DEFAULT_ENTITY_NOTIFICATIONS_ENABLED);

        return new CamAlertSettings(defaultCameraRange, defaultCameraNotificationThresholdSeconds, defaultCameraBlockMaterial, entityNotificationsEnabled);
    }

    @Override
    public void saveSettings(CamAlertSettings settings) {
        config.set(DEFAULT_CAMERA_RANGE_PATH, settings.getDefaultCameraRange());
        config.set(DEFAULT_NOTIFICATION_THRESHOLD_PATH, settings.getDefaultCameraNotificationThresholdSeconds());
        config.set(DEFAULT_BLOCK_MATERIAL_PATH, settings.getDefaultCameraBlockMaterial());
        config.set(DEFAULT_ENTITY_NOTIFICATIONS_ENABLED_PATH, settings.isEntityNotificationsEnabled());
    }
}