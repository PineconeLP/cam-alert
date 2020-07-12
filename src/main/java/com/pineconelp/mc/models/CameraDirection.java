package com.pineconelp.mc.models;

public enum CameraDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static CameraDirection fromYaw(float yaw) {
        if(yaw >= 135 && yaw < 225) {
            return CameraDirection.NORTH;
        } else if(yaw >= 45 && yaw < 135) {
            return CameraDirection.WEST;
        } else if(yaw >= 225 && yaw < 315) {
            return CameraDirection.EAST;
        } else {
            return CameraDirection.SOUTH;
        }
    }

	public CameraDirection inverse() {
		switch (this) {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            default:
                return NORTH;
        }
	}
}