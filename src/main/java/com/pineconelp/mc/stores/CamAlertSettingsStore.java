package com.pineconelp.mc.stores;

import com.google.inject.Inject;
import com.pineconelp.mc.models.CamAlertSettings;
import com.pineconelp.mc.services.cam_alert_settings_repositories.ICamAlertSettingsRepository;

import org.bukkit.Material;

public class CamAlertSettingsStore {
    private ICamAlertSettingsRepository camAlertSettingsRepository;
    private CamAlertSettings camAlertSettings;

    @Inject
    public CamAlertSettingsStore(ICamAlertSettingsRepository camAlertSettingsRepository) {
        this.camAlertSettingsRepository = camAlertSettingsRepository;
        camAlertSettings = new CamAlertSettings(10, 5, Material.JACK_O_LANTERN, true);
    }

    public void loadSettings() {
        camAlertSettings = camAlertSettingsRepository.loadSettings();
    }

    public double getDefaultCameraRange() {
        return camAlertSettings.getDefaultCameraRange();
    }

    public double getDefaultCameraNotificationThresholdSeconds() {
        return camAlertSettings.getDefaultCameraNotificationThresholdSeconds();
    }

    public Material getDefaultCameraBlockMaterial() {
        return camAlertSettings.getDefaultCameraBlockMaterial();
    }

    public boolean isEntityNotificationsEnabled() {
        return camAlertSettings.isEntityNotificationsEnabled();
    }
}