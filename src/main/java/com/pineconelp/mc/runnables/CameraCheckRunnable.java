package com.pineconelp.mc.runnables;

import com.google.inject.Inject;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraLocation;
import com.pineconelp.mc.stores.CameraStore;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class CameraCheckRunnable extends BukkitRunnable {

    private CameraStore cameraStore;

    @Inject
    public CameraCheckRunnable(CameraStore cameraStore) {
        this.cameraStore = cameraStore;
    }

    @Override
    public void run() {
        for (Camera camera : cameraStore.getCameras()) {
            for (CameraLocation cameraLocation : camera.getMonitoredLocations()) {
                World cameraWorld = Bukkit.getWorld(cameraLocation.getWorldId());

                Bukkit.broadcastMessage(cameraWorld.getName());
            }
        }
    }
}