package com.pineconelp.mc.stores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraLocation;

public class CameraStore {
    private HashMap<CameraLocation, List<Camera>> monitoredLocations;

    public CameraStore() {
        monitoredLocations = new HashMap<>();
    }

    public void addCamera(Camera camera) {
        CameraLocation[] cameraMonitoredLocations = camera.getMonitoredLocations();

        for (CameraLocation location : cameraMonitoredLocations) {
            addMonitoredLocation(location, camera);
        }
    }

    private void addMonitoredLocation(CameraLocation location, Camera camera) {
        if(!monitoredLocations.containsKey(location)) {
            monitoredLocations.put(location, new ArrayList<Camera>());
        }

        List<Camera> locationCameras = monitoredLocations.get(location);
        locationCameras.add(camera);
    }
}