package com.pineconelp.mc.models;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Location;

public class Camera {
    private UUID ownerPlayerId;
    private CameraLocation location;
    private CameraDirection direction;
    private CameraDetails cameraDetails;

    private HashMap<UUID, Date> notifications;
    private HashSet<CameraLocation> monitoredLocations;

    public Camera(CameraLocation location, CameraDirection direction, UUID ownerPlayerId, CameraDetails cameraDetails) {
        this.location = location;
        this.direction = direction;
        this.ownerPlayerId = ownerPlayerId;
        this.cameraDetails = cameraDetails;

        this.notifications = new HashMap<>();
        this.monitoredLocations = new HashSet<>();

        updateMonitoredLocation();
    }

    private void updateMonitoredLocation() {
        monitoredLocations = new HashSet<>();

        for (int i = 1; i < getRange(); i++) {
            UUID worldId = location.getWorldId();
            int x = location.getX();
            int y = location.getY();
            int z = location.getZ();

            switch (direction) {
                case NORTH:
                    z -= i;
                    break;
                case EAST:
                    x += i;
                    break;
                case SOUTH:
                    z += i;
                    break;
                case WEST:
                    x -= i;
                    break;
                default:
                    break;
            }

            monitoredLocations.add(new CameraLocation(worldId, x, y, z));
            monitoredLocations.add(new CameraLocation(worldId, x, y - 1, z));
        }
    }

    public boolean isMonitoring(Location targetLocation) {
        CameraLocation cameraTargetLocation = new CameraLocation(targetLocation);
		return monitoredLocations.contains(cameraTargetLocation);
	}

    public void addPlayerNotification(UUID playerId) {
        notifications.put(playerId, new Date());
    }

    public Date getLastPlayerNotification(UUID playerId) {
        return notifications.get(playerId);
    }

    public UUID getOwnerPlayerId() {
        return ownerPlayerId;
    }

    public CameraDetails getCameraDetails() {
        return cameraDetails;
    }

    public double getRange() {
        return cameraDetails.getRange();
    }

    public double getNotificationThresholdSeconds() {
        return cameraDetails.getNotificationThresholdSeconds();
    }

	public CameraLocation getLocation() {
		return location;
	}
}