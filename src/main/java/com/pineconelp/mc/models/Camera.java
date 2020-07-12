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

            locations.add(new CameraLocation(x, y, z));
            locations.add(new CameraLocation(x, y - 1, z));
        }
        
        CameraLocation[] arrayLocations = new CameraLocation[locations.size()];
        locations.toArray(arrayLocations);

        return arrayLocations;
    }

    public UUID getOwnerPlayerId() {
        return ownerPlayerId;
    }

	public CameraLocation getLocation() {
		return location;
	}
}