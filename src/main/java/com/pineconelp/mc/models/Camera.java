package com.pineconelp.mc.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Camera {
    private UUID ownerPlayerId;
    private CameraLocation location;
    private CameraDirection direction;
    private CameraDetails cameraDetails;

    private HashMap<UUID, Date> notifications;

    public Camera(CameraLocation location, CameraDirection direction, UUID ownerPlayerId, CameraDetails cameraDetails) {
        this.location = location;
        this.direction = direction;
        this.ownerPlayerId = ownerPlayerId;
        this.cameraDetails = cameraDetails;

        this.notifications = new HashMap<>();
    }

    public CameraLocation[] getMonitoredLocations() {
        ArrayList<CameraLocation> locations = new ArrayList<CameraLocation>();

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

            locations.add(new CameraLocation(worldId, x, y, z));
            locations.add(new CameraLocation(worldId, x, y - 1, z));
        }
        
        CameraLocation[] arrayLocations = new CameraLocation[locations.size()];
        locations.toArray(arrayLocations);

        return arrayLocations;
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