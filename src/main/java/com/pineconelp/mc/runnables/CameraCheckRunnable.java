package com.pineconelp.mc.runnables;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import com.google.inject.Inject;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraLocation;
import com.pineconelp.mc.stores.CameraStore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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
            CameraLocation cameraLocation = camera.getLocation();

            World cameraWorld = Bukkit.getWorld(cameraLocation.getWorldId());
            Location cameraWorldLocation = new Location(cameraWorld, cameraLocation.getX(), cameraLocation.getY(), cameraLocation.getZ());

            double cameraRange = camera.getRange();
            Collection<Entity> entitiesInCameraSight = cameraWorld.getNearbyEntities(cameraWorldLocation, cameraRange, cameraRange, cameraRange);

            for (Entity entity : entitiesInCameraSight) {
                Location location = entity.getLocation();

                if(camera.isMonitoring(location)) {
                    notifyCamera(camera, entity);
                }
            }
        }
    }

    private void notifyCamera(Camera camera, Entity entity) {
        UUID entityId = entity.getUniqueId();

        if(camera.canAddNotificationForEntity(entityId)) {
            camera.addEntityNotification(entityId);
            Player cameraOwner = Bukkit.getPlayer(camera.getOwnerPlayerId());

            if(cameraOwner != null) {
                cameraOwner.sendMessage(ChatColor.RED + "MOVEMENT DETECTED AT CAMERA.");
            }
        }
    }
}