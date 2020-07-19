package com.pineconelp.mc.runnables;

import java.util.Collection;
import java.util.UUID;

import com.google.inject.Inject;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraLocation;
import com.pineconelp.mc.services.camera_notifiers.ICameraNotifier;
import com.pineconelp.mc.stores.CameraStore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityCameraMovementRunnable extends BukkitRunnable {

    private CameraStore cameraStore;
    private ICameraNotifier cameraNotifier;

    @Inject
    public EntityCameraMovementRunnable(CameraStore cameraStore, ICameraNotifier cameraNotifier) {
        this.cameraStore = cameraStore;
        this.cameraNotifier = cameraNotifier;
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
                boolean isPlayer = entity instanceof Player;
                boolean isMoving = entity.getVelocity().getX() != 0 || entity.getVelocity().getZ() != 0 || !entity.isOnGround();

                if(!isPlayer && isMoving) {
                    Location location = entity.getLocation();

                    if(camera.isMonitoring(location)) {
                        UUID entityId = entity.getUniqueId();
    
                        if(camera.addEntityNotification(entityId)) {
                            cameraNotifier.notify(camera, entity);
                        }
                    }
                }
            }
        }
    }
}