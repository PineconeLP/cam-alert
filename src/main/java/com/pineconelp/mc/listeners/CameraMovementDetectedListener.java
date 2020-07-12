package com.pineconelp.mc.listeners;

import com.google.inject.Inject;
import com.pineconelp.mc.stores.CameraStore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CameraMovementDetectedListener implements Listener {

    private CameraStore cameraStore;

    @Inject
    public CameraMovementDetectedListener(CameraStore cameraStore) {
        this.cameraStore = cameraStore;
    }

    @EventHandler
    public void onCameraMovementDetected(PlayerMoveEvent playerMoveEvent) {
        
    }
}