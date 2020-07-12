package com.pineconelp.mc.models;

public class CameraLocation {
    private int x;
    private int y;
    private int z;

    public CameraLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
    public boolean equals(Object other) { 
  
        if (other == this) { 
            return true; 
        } 
  
        if (!(other instanceof CameraLocation)) { 
            return false; 
        } 
          
        CameraLocation otherCameraLocation = (CameraLocation) other; 

        return x == otherCameraLocation.getX() &&
            y == otherCameraLocation.getY() &&
            z == otherCameraLocation.getZ();
    } 
}