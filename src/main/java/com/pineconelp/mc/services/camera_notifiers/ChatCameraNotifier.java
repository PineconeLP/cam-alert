package com.pineconelp.mc.services.camera_notifiers;

import com.pineconelp.mc.models.Camera;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ChatCameraNotifier implements ICameraNotifier {

    @Override
    public void notify(Camera camera, Entity entity) {
        Player cameraOwner = Bukkit.getPlayer(camera.getOwnerPlayerId());

        if(cameraOwner != null) {
            cameraOwner.sendMessage(ChatColor.RED + "MOVEMENT DETECTED AT CAMERA.");
        }
    }
}