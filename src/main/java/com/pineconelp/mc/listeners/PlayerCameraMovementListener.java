package com.pineconelp.mc.listeners;

import java.util.UUID;

import com.google.inject.Inject;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.services.camera_notifiers.ICameraNotifier;
import com.pineconelp.mc.stores.CameraStore;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerCameraMovementListener implements Listener {
    
    private CameraStore cameraStore;
    private ICameraNotifier cameraNotifier;

    @Inject
    public PlayerCameraMovementListener(CameraStore cameraStore, ICameraNotifier cameraNotifier) {
        this.cameraStore = cameraStore;
        this.cameraNotifier = cameraNotifier;
	}

    @EventHandler
    public void onPlayerCameraMovement(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        Location location = event.getTo();

        for(Camera camera : cameraStore.getCamerasMonitoring(location)) {
            if(camera.addEntityNotification(playerId)) {
                cameraNotifier.notify(camera, player);
            }
        }
    }
}

