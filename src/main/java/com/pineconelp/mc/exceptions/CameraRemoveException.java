package com.pineconelp.mc.exceptions;

import com.pineconelp.mc.models.Camera;

public class CameraRemoveException extends Exception {

    private static final long serialVersionUID = 1L;

    private Camera camera;

    public CameraRemoveException(Camera camera) {
        super();
        
        this.camera = camera;
    }

    public CameraRemoveException(Camera camera, Throwable e) {
        super(e);

        this.camera = camera;
	}

	public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}