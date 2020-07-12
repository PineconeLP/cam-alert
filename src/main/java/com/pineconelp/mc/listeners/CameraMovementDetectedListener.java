package com.pineconelp.mc.listeners;

import java.util.List;

import com.google.inject.Inject;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraLocation;
import com.pineconelp.mc.stores.CameraStore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.md_5.bungee.api.ChatColor;

public class CameraMovementDetectedListener implements Listener {

    private CameraStore cameraStore;

    @Inject
    public CameraMovementDetectedListener(CameraStore cameraStore) {
        this.cameraStore = cameraStore;
    }

    @EventHandler
    public void onCameraMovementDetected(PlayerMoveEvent playerMoveEvent) {
        Location playerLocation = playerMoveEvent.getTo();
        CameraLocation cameraLocation = new CameraLocation(playerLocation);

        if(cameraStore.hasCamerasMonitoring(cameraLocation)) {
            List<Camera> triggeredCameras = cameraStore.getCamerasMonitoring(cameraLocation);

            for (Camera camera : triggeredCameras) {
                notifyCamera(camera);
            }
        }
    }

    private void notifyCamera(Camera camera) {
        Player cameraOwner = Bukkit.getServer().getPlayer(camera.getOwnerPlayerId());
        
        if(cameraOwner != null) {
            cameraOwner.sendMessage(ChatColor.RED + "MOVEMENT DETECTED AT CAMERA.");
        }
    }
}