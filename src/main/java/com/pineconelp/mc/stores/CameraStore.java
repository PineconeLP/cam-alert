package com.pineconelp.mc.stores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraLocation;

import org.bukkit.Location;

public class CameraStore {
    private HashMap<CameraLocation, List<Camera>> monitoredLocations;

    public CameraStore() {
        monitoredLocations = new HashMap<CameraLocation, List<Camera>>();
    }

    public void addCamera(Camera camera) {
        CameraLocation[] cameraMonitoredLocations = camera.getMonitoredLocations();

        for (CameraLocation location : cameraMonitoredLocations) {
            addMonitoredLocation(location, camera);
        }
    }

    public boolean hasCameras(Location location) {
        CameraLocation cameraLocation = new CameraLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return hasCameras(cameraLocation);
    }

    public boolean hasCameras(CameraLocation location) {
        List<Camera> cameras = getCameras(location);
        return cameras != null && cameras.size() > 0;
    }

    public List<Camera> getCameras(Location location) {
        CameraLocation cameraLocation = new CameraLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return getCameras(cameraLocation);
    }

    public List<Camera> getCameras(CameraLocation location) {
        return monitoredLocations.get(location);
    }

    private void addMonitoredLocation(CameraLocation location, Camera camera) {
        if(!monitoredLocations.containsKey(location)) {
            monitoredLocations.put(location, new ArrayList<Camera>());
        }

        List<Camera> locationCameras = monitoredLocations.get(location);
        locationCameras.add(camera);
    }
}