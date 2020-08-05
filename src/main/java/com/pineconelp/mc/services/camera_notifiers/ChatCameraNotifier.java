package com.pineconelp.mc.services.camera_notifiers;

import java.util.UUID;

import com.pineconelp.mc.models.Camera;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ChatCameraNotifier implements ICameraNotifier {

    @Override
    public void notify(Camera camera, Entity entity) {
        UUID cameraOwnerId = camera.getOwnerPlayerId();
        Player cameraOwner = Bukkit.getPlayer(cameraOwnerId);

        UUID intruderId = entity.getUniqueId();
        boolean cameraOwnerIsIntruder = cameraOwnerId.equals(intruderId);

        if(cameraOwner != null && !cameraOwnerIsIntruder) {
            cameraOwner.sendMessage(ChatColor.RED + "MOVEMENT DETECTED AT CAMERA.");
        }
    }
}