package com.pineconelp.mc.listeners;

import com.google.inject.Inject;
import com.pineconelp.mc.items.cameras.ICameraItemDetailer;
import com.pineconelp.mc.items.cameras.ICameraItemValidator;
import com.pineconelp.mc.items.cameras.InvalidCameraItemException;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraDetails;
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

public class CameraPlacedListener implements Listener {

    private CameraStore cameraStore;
    private ICameraItemValidator cameraItemValidator;
    private ICameraItemDetailer cameraItemDetailer;

    @Inject
    public CameraPlacedListener(CameraStore cameraStore, ICameraItemValidator cameraItemValidator,
            ICameraItemDetailer cameraItemDetailer) {
        this.cameraStore = cameraStore;
        this.cameraItemValidator = cameraItemValidator;
        this.cameraItemDetailer = cameraItemDetailer;
    }

    @EventHandler
    public void onCameraPlaced(BlockPlaceEvent blockPlaceEvent) {
        ItemStack itemPlaced = blockPlaceEvent.getItemInHand();

        if (cameraItemValidator.isCameraItem(itemPlaced)) {
            Player player = blockPlaceEvent.getPlayer();
            Location cameraLocation = blockPlaceEvent.getBlockPlaced().getLocation();

            try {
                CameraDetails cameraDetails = cameraItemDetailer.getCameraItemDetails(itemPlaced);
                cameraStore.addCamera(createCamera(cameraLocation, player, cameraDetails));

                player.sendMessage(ChatColor.GREEN + "Camera initialized.");
            } catch (InvalidCameraItemException e) {
                player.sendMessage(ChatColor.RED + "Invalid camera.");
            }
        }
    }

    private Camera createCamera(Location placedLocation, Player player, CameraDetails cameraDetails) {
        float playerYaw = player.getLocation().getYaw();
        CameraDirection cameraDirection = getCameraDirection(playerYaw);
        CameraLocation cameraLocation = new CameraLocation(placedLocation);

        return new Camera(cameraLocation, cameraDirection, player.getUniqueId(), cameraDetails);
    }

    private CameraDirection getCameraDirection(float playerYaw) {
        CameraDirection playerDirection = CameraDirection.fromYaw(playerYaw);
        return playerDirection.inverse();
    }
}