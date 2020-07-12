package com.pineconelp.mc.listeners;

import com.google.inject.Inject;
import com.pineconelp.mc.items.cameras.ICameraItemValidator;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraDirection;
import com.pineconelp.mc.models.CameraLocation;
import com.pineconelp.mc.stores.CameraStore;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class CameraPlacedListener implements Listener{

    private CameraStore cameraStore;
    private ICameraItemValidator cameraItemValidator;

    @Inject
    public CameraPlacedListener(CameraStore cameraStore, ICameraItemValidator cameraItemValidator) {
        this.cameraStore = cameraStore;
        this.cameraItemValidator = cameraItemValidator;
    }

    @EventHandler
    public void onCameraPlaced(BlockPlaceEvent blockPlaceEvent) {
        ItemStack itemPlaced = blockPlaceEvent.getItemInHand();

        if(cameraItemValidator.isCameraItem(itemPlaced)) {
            Player player = blockPlaceEvent.getPlayer();
            Location cameraLocation = blockPlaceEvent.getBlockPlaced().getLocation();
            cameraStore.addCamera(createCamera(cameraLocation, 10, player));

            blockPlaceEvent.getPlayer().sendMessage(ChatColor.GREEN + "Camera initialized.");
        }
    }

    private Camera createCamera(Location placedLocation, int range, Player player) {
        float playerYaw = player.getLocation().getYaw();
        CameraDirection cameraDirection = getCameraDirection(playerYaw);
        CameraLocation cameraLocation = new CameraLocation(placedLocation.getBlockX(), placedLocation.getBlockY(), placedLocation.getBlockZ());

        return new Camera(cameraLocation, cameraDirection, range, player.getUniqueId());
    }

    private CameraDirection getCameraDirection(float playerYaw) {
        CameraDirection playerDirection = CameraDirection.fromYaw(playerYaw);
        return playerDirection.inverse();
    }
}