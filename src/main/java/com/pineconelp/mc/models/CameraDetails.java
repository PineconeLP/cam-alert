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

    public CameraDetails clone(UUID newOwnerPlayerId) {
        return new CameraDetails(this.range, this.notificationThresholdSeconds, newOwnerPlayerId);
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