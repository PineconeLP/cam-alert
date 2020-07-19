package com.pineconelp.mc.stores;

import java.util.ArrayList;
import java.util.List;

import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraLocation;

import org.bukkit.Location;

public class CameraStore {
    private List<Camera> cameras;

    public CameraStore() {
        cameras = new ArrayList<>();
    }

    public Camera[] getCamerasMonitoring(Location location) {
        return cameras.stream().filter(c -> c.isMonitoring(location)).toArray(Camera[]::new);
    }

    public List<Camera> getCameras() {
        return cameras;
    }

    public boolean hasCamera(final CameraLocation cameraLocation) {
        return getCamera(cameraLocation) != null;
    }

    public void addCamera(Camera camera) {
        cameras.add(camera);
    }

    public Camera removeCamera(final CameraLocation cameraLocation) {
        Camera cameraToRemove = getCamera(cameraLocation);

        if(cameraToRemove != null) {
            cameras.remove(cameraToRemove);
        }

        return cameraToRemove;
    }

    private Camera getCamera(final CameraLocation cameraLocation) {
        return cameras.stream().filter(c -> c.getLocation().equals(cameraLocation)).findFirst().orElse(null);
    }
}