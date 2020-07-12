package com.pineconelp.mc.models;

import java.util.Objects;
import java.util.UUID;

import org.bukkit.Location;

public class CameraLocation {
    private UUID worldId;
    private int x;
    private int y;
    private int z;

    public CameraLocation(UUID worldId, int x, int y, int z) {
        this.worldId = worldId;
		this.x = x;
        this.y = y;
        this.z = z;
    }

    public CameraLocation(Location location) {
        this.worldId = location.getWorld().getUID();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public UUID getWorldId() {
        return worldId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public boolean equals(Object other) { 
  
        if (other == this) { 
            return true; 
        } 
  
        if (!(other instanceof CameraLocation)) { 
            return false; 
        } 
          
        CameraLocation otherCameraLocation = (CameraLocation) other; 

        return worldId == otherCameraLocation.getWorldId() &&
            x == otherCameraLocation.getX() &&
            y == otherCameraLocation.getY() &&
            z == otherCameraLocation.getZ();
    } 
}