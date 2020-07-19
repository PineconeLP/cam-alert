package com.pineconelp.mc.models;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Location;

public class Camera {
    private long id;
    private CameraLocation location;
    private CameraDirection direction;
    private CameraDetails cameraDetails;

    private HashMap<UUID, Date> notifications;
    private HashSet<CameraLocation> monitoredLocations;

    public Camera(CameraLocation location, CameraDirection direction, CameraDetails cameraDetails) {
        this.location = location;
        this.direction = direction;
        this.cameraDetails = cameraDetails;

        this.notifications = new HashMap<>();
        this.monitoredLocations = new HashSet<>();

        updateMonitoredLocation();
    }

    public Camera(long id, CameraLocation location, CameraDirection direction, CameraDetails cameraDetails) {
        this(location, direction, cameraDetails);
        
        this.id = id;
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

    public boolean addEntityNotification(UUID entityId) {
        boolean success = false;

        if(canAddNotificationForEntity(entityId)) {
            notifications.put(entityId, new Date());
            success = true;
        }

        return success;
    }

    private boolean canAddNotificationForEntity(UUID entityId) {
        Date lastPlayerNotification = getLastNotificationForEntity(entityId);
		Date notificationThreshold = new Date(System.currentTimeMillis() - (int)(getNotificationThresholdSeconds() * 1000));

        return lastPlayerNotification == null || lastPlayerNotification.before(notificationThreshold);
    }

    public Date getLastNotificationForEntity(UUID entityId) {
        return notifications.get(entityId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
	public CameraLocation getLocation() {
		return location;
    }

    public CameraDirection getCameraDirection() {
        return direction;
    }
    
    public UUID getOwnerPlayerId() {
        return cameraDetails.getOwnerPlayerId();
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

}