package com.pineconelp.mc.stores;

import com.pineconelp.mc.models.CamAlertSettings;

import org.bukkit.Material;

public class CamAlertSettingsStore {
    private CamAlertSettings camAlertSettings;
    
    public CamAlertSettingsStore() {
        camAlertSettings = new CamAlertSettings(10, 5, Material.JACK_O_LANTERN, false);
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