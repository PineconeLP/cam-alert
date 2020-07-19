package com.pineconelp.mc.stores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraLocation;
import com.pineconelp.mc.services.camera_repositories.ICameraRepository;

import org.bukkit.Location;

public class CameraStore {
    private ICameraRepository cameraRepository;

    private List<Camera> cameras;

    @Inject
    public CameraStore(ICameraRepository cameraRepository) {
        this.cameraRepository = cameraRepository;
        cameras = new ArrayList<>();
    }

    public void loadCameras() throws Exception {
        try {
            List<Camera> newCameras = cameraRepository.getAll();

            cameras.clear();
            cameras.addAll(newCameras);
        } catch (SQLException e) {
            throw new Exception("Failed to load cameras.", e);
        }
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

    public void addCamera(Camera camera) throws Exception {
        cameras.add(camera);
        
        try {
            camera = cameraRepository.create(camera);
        } catch (SQLException e) {
            throw new Exception("Failed to create camera.", e);
        }
    }

    public Camera removeCamera(final CameraLocation cameraLocation) throws Exception {
        Camera cameraToRemove = getCamera(cameraLocation);

        if(cameraToRemove != null) {
            cameras.remove(cameraToRemove);

            try {
                cameraRepository.delete(cameraToRemove.getId());
            } catch (SQLException e) {
                throw new Exception("Failed to remove camera.", e);
            }
        }

        return cameraToRemove;
    }

    private Camera getCamera(final CameraLocation cameraLocation) {
        return cameras.stream().filter(c -> c.getLocation().equals(cameraLocation)).findFirst().orElse(null);
    }
}