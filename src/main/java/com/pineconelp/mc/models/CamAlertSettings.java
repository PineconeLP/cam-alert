package com.pineconelp.mc.models;

import org.bukkit.Material;

public class CamAlertSettings {
    private double defaultCameraRange;
    private double defaultCameraNotificationThresholdSeconds;
    private Material defaultCameraBlockMaterial;

    public CamAlertSettings(double defaultCameraRange, double defaultCameraNotificationThresholdSeconds,
            Material defaultCameraBlockMaterial) {
        this.defaultCameraRange = defaultCameraRange;
        this.defaultCameraNotificationThresholdSeconds = defaultCameraNotificationThresholdSeconds;
        this.defaultCameraBlockMaterial = defaultCameraBlockMaterial;
    }

    public double getDefaultCameraRange() {
        return defaultCameraRange;
    }

    public void setDefaultCameraRange(double defaultCameraRange) {
        this.defaultCameraRange = defaultCameraRange;
    }

    public double getDefaultCameraNotificationThresholdSeconds() {
        return defaultCameraNotificationThresholdSeconds;
    }

    public void setDefaultCameraNotificationThresholdSeconds(double defaultCameraNotificationThresholdSeconds) {
        this.defaultCameraNotificationThresholdSeconds = defaultCameraNotificationThresholdSeconds;
    }

    public Material getDefaultCameraBlockMaterial() {
        return defaultCameraBlockMaterial;
    }

    public void setDefaultCameraBlockMaterial(Material defaultCameraBlockMaterial) {
        this.defaultCameraBlockMaterial = defaultCameraBlockMaterial;
    }
}