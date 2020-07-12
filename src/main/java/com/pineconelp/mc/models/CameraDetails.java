package com.pineconelp.mc.models;

public class CameraDetails {
    private double range;
    private double notificationThresholdSeconds;

    public CameraDetails(double range, double notificationThresholdSeconds) {
        this.range = range;
        this.notificationThresholdSeconds = notificationThresholdSeconds;
    }

    public double getRange() {
        return range;
    }

    public double getNotificationThresholdSeconds() {
        return notificationThresholdSeconds;
    }
}