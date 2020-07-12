package com.pineconelp.mc.models;

public enum CameraDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static CameraDirection fromYaw(float yaw) {
        if(yaw > 135 || yaw <= -135) {
            return CameraDirection.NORTH;
        } else if(yaw > 45 || yaw <= 135) {
            return CameraDirection.WEST;
        } else if(yaw > -135 || yaw <= -45) {
            return CameraDirection.EAST;
        } else {
            return CameraDirection.SOUTH;
        }
    }
}