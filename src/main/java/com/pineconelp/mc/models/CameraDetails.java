package com.pineconelp.mc.models;

public class CameraDetails {
    private double range;
    private int notificationThresholdSeconds;

    public CameraDetails(double range, int notificationThresholdSeconds) {
        this.range = range;
        this.notificationThresholdSeconds = notificationThresholdSeconds;
    }

    public double getRange() {
        return range;
    }

    public int getNotificationThresholdSeconds() {
        return notificationThresholdSeconds;
    }
}