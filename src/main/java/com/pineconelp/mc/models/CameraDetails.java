package com.pineconelp.mc.models;

import java.util.UUID;

public class CameraDetails {
    private double range;
    private double notificationThresholdSeconds;
    private UUID ownerPlayerId;

    public CameraDetails(double range, double notificationThresholdSeconds, UUID ownerPlayerId) {
        this.range = range;
        this.notificationThresholdSeconds = notificationThresholdSeconds;
        this.ownerPlayerId = ownerPlayerId;
    }

    public CameraDetails cloneWithOwnerPlayerId(UUID newOwnerPlayerId) {
        return new CameraDetails(this.range, this.notificationThresholdSeconds, newOwnerPlayerId);
    }

    public CameraDetails cloneWithRange(double range) {
        return new CameraDetails(range, this.notificationThresholdSeconds, this.ownerPlayerId);
    }

    public CameraDetails cloneWithThreshold(double threshold) {
        return new CameraDetails(this.range, threshold, this.ownerPlayerId);
    }

    public double getRange() {
        return range;
    }

    public double getNotificationThresholdSeconds() {
        return notificationThresholdSeconds;
    }

    public UUID getOwnerPlayerId() {
        return ownerPlayerId;
    }
}