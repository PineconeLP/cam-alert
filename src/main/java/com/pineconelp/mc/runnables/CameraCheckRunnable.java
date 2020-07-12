package com.pineconelp.mc.runnables;

import java.util.Collection;

import com.google.inject.Inject;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraLocation;
import com.pineconelp.mc.stores.CameraStore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
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
                Location monitoredLocation = new Location(cameraWorld, cameraLocation.getX(), cameraLocation.getY(), cameraLocation.getZ());
                Collection<Entity> entitiesInCameraSight = cameraWorld.getNearbyEntities(monitoredLocation, 1, 1, 1);

                for (Entity entity : entitiesInCameraSight) {
                    if(entity.getLocation().getBlock().equals(monitoredLocation.getBlock())){
                        Bukkit.broadcastMessage(entity.getUniqueId().toString());
                        Bukkit.broadcastMessage(String.format("%d", entity.getEntityId()));
                    }
                }
            }
        }
    }
}