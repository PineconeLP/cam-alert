package com.pineconelp.mc.models;

import java.util.ArrayList;
import java.util.UUID;

public class Camera {
    private UUID ownerPlayerId;
    private CameraLocation location;
    private CameraDirection direction;
    private double range;

    public Camera(CameraLocation location, CameraDirection direction, double range, UUID ownerPlayerId) {
        this.location = location;
        this.direction = direction;
        this.range = range;
        this.ownerPlayerId = ownerPlayerId;
    }

    public CameraLocation[] getMonitoredLocations() {
        ArrayList<CameraLocation> locations = new ArrayList<CameraLocation>();

        for (int i = 1; i < range; i++) {
            switch (direction) {
                case NORTH:
                    locations.add(new CameraLocation(location.getX(), location.getY(), location.getZ() - i));
                    break;
                case EAST:
                    locations.add(new CameraLocation(location.getX() + i, location.getY(), location.getZ()));
                    break;
                case SOUTH:
                    locations.add(new CameraLocation(location.getX(), location.getY(), location.getZ() + i));
                    break;
                case WEST:
                    locations.add(new CameraLocation(location.getX() - i, location.getY(), location.getZ()));
                    break;
                default:
                    break;
            }
        }
        
        CameraLocation[] arrayLocations = new CameraLocation[locations.size()];
        locations.toArray(arrayLocations);

        return arrayLocations;
    }

    public UUID getOwnerPlayerId() {
        return ownerPlayerId;
    }
}