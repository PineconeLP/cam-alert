package com.pineconelp.mc.runnables;

import java.util.Collection;
import java.util.UUID;

import com.google.inject.Inject;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraLocation;
import com.pineconelp.mc.services.camera_notifiers.ICameraNotifier;
import com.pineconelp.mc.stores.CamAlertSettingsStore;
import com.pineconelp.mc.stores.CameraStore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class EntityCameraMovementRunnable extends BukkitRunnable {

    private Plugin plugin;
    private CameraStore cameraStore;
    private CamAlertSettingsStore settingsStore;
    private ICameraNotifier cameraNotifier;

    private BukkitTask currentTask;

    @Inject
    public EntityCameraMovementRunnable(Plugin plugin, CameraStore cameraStore,
            CamAlertSettingsStore settingsStore,
            ICameraNotifier cameraNotifier) {
        this.plugin = plugin;
        this.cameraStore = cameraStore;
        this.settingsStore = settingsStore;
        this.cameraNotifier = cameraNotifier;
    }

    public void initialize() {
        updateTaskStatus();
    }

    private void updateTaskStatus() {
        boolean isRunning = currentTask != null && !currentTask.isCancelled();
        boolean isEntityNotificationsEnabled = this.settingsStore.isEntityNotificationsEnabled();

        if(isRunning && !isEntityNotificationsEnabled) {
            currentTask.cancel();
        } else if(!isRunning && isEntityNotificationsEnabled) {
            currentTask = runTaskTimer(plugin, 0L, 10L);
        }
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