package com.pineconelp.mc.stores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraLocation;

public class CameraStore {
    private HashMap<CameraLocation, Camera> cameras;
    private HashMap<CameraLocation, List<Camera>> monitoredLocations;

    public CameraStore() {
        cameras = new HashMap<CameraLocation, Camera>(); 
        monitoredLocations = new HashMap<CameraLocation, List<Camera>>();
    }

    public boolean hasCamera(CameraLocation cameraLocation) {
        return cameras.containsKey(cameraLocation);
    }

    public void addCamera(Camera camera) {
        cameras.put(camera.getLocation(), camera);

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

    public Camera removeCamera(CameraLocation cameraLocation) {
        Camera removedCamera = cameras.remove(cameraLocation);

        CameraLocation[] cameraMonitoredLocations = removedCamera.getMonitoredLocations();

        for (CameraLocation location : cameraMonitoredLocations) {
            removeMonitoredLocation(location, removedCamera);
        }

        return removedCamera;
    }

    private void removeMonitoredLocation(CameraLocation location, Camera camera) {
        if(monitoredLocations.containsKey(location)) {
            List<Camera> locationCameras = monitoredLocations.get(location);
            locationCameras.remove(camera);
        }
    }

    public boolean hasCamerasMonitoring(CameraLocation location) {
        List<Camera> cameras = getCamerasMonitoring(location);
        return cameras != null && cameras.size() > 0;
    }

    public List<Camera> getCamerasMonitoring(CameraLocation location) {
        return monitoredLocations.get(location);
    }
}