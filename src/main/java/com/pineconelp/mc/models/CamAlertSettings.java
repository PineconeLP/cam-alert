package com.pineconelp.mc.models;

import org.bukkit.Material;

public class CamAlertSettings {
    public static final double DEFAULT_CAMERA_RANGE = 20;
    public static final double DEFAULT_NOTIFICATION_THRESHOLD = 1;
    public static final Material DEFAULT_BLOCK_MATERIAL = Material.JACK_O_LANTERN;
    public static final boolean DEFAULT_ENTITY_NOTIFICATIONS_ENABLED = true;

    private double defaultCameraRange;
    private double defaultCameraNotificationThresholdSeconds;
    private Material defaultCameraBlockMaterial;
    private boolean entityNotificationsEnabled;

    public CamAlertSettings() { 
        this.defaultCameraRange = DEFAULT_CAMERA_RANGE;
        this.defaultCameraNotificationThresholdSeconds = DEFAULT_NOTIFICATION_THRESHOLD;
        this.defaultCameraBlockMaterial = DEFAULT_BLOCK_MATERIAL;
        this.entityNotificationsEnabled = DEFAULT_ENTITY_NOTIFICATIONS_ENABLED;
    }

    public CamAlertSettings(double defaultCameraRange, double defaultCameraNotificationThresholdSeconds,
            Material defaultCameraBlockMaterial, boolean entityNotificationsEnabled) {
        this.defaultCameraRange = defaultCameraRange;
        this.defaultCameraNotificationThresholdSeconds = defaultCameraNotificationThresholdSeconds;
        this.defaultCameraBlockMaterial = defaultCameraBlockMaterial;
        this.entityNotificationsEnabled = entityNotificationsEnabled;
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

    public boolean isEntityNotificationsEnabled() {
        return entityNotificationsEnabled;
    }

    public void setEntityNotificationsEnabled(boolean entityNotificationsEnabled) {
        this.entityNotificationsEnabled = entityNotificationsEnabled;
    }
}