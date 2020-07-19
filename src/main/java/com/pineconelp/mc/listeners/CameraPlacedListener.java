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
import com.pineconelp.mc.utilities.TaskRunner;

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
    private TaskRunner taskRunner;

    @Inject
    public CameraPlacedListener(CameraStore cameraStore, ICameraItemValidator cameraItemValidator,
            ICameraItemDetailer cameraItemDetailer, TaskRunner taskRunner) {
        this.cameraStore = cameraStore;
        this.cameraItemValidator = cameraItemValidator;
        this.cameraItemDetailer = cameraItemDetailer;
        this.taskRunner = taskRunner;
    }

    @EventHandler
    public void onCameraPlaced(BlockPlaceEvent blockPlaceEvent) {
        ItemStack itemPlaced = blockPlaceEvent.getItemInHand();

        if (cameraItemValidator.isCameraItem(itemPlaced)) {
            Player player = blockPlaceEvent.getPlayer();
            Location cameraLocation = blockPlaceEvent.getBlockPlaced().getLocation();

            try {
                CameraDetails cameraDetails = cameraItemDetailer.getCameraItemDetails(itemPlaced);
                CameraDetails newCameraDetails = cameraDetails.clone(player.getUniqueId());

                taskRunner.runTaskAsync(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            player.sendMessage(ChatColor.GREEN + "Saving camera...");
                            
                            Camera createdCamera = createCamera(cameraLocation, player, newCameraDetails);
                            cameraStore.addCamera(createdCamera);

                            player.sendMessage(ChatColor.GREEN + "Camera saved.");
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + "Failed to save camera. Pickup this camera or it will be lost after the next server restart.");
                            e.printStackTrace();
                        }
					}
                });
            } catch (InvalidCameraItemException e) {
                player.sendMessage(ChatColor.RED + "Invalid camera.");
            } 
        }
    }

    private Camera createCamera(Location placedLocation, Player player, CameraDetails cameraDetails) {
        float playerYaw = player.getLocation().getYaw();
        CameraDirection cameraDirection = getCameraDirection(playerYaw);
        CameraLocation cameraLocation = new CameraLocation(placedLocation);

        return new Camera(cameraLocation, cameraDirection, cameraDetails);
    }

    private CameraDirection getCameraDirection(float playerYaw) {
        CameraDirection playerDirection = CameraDirection.fromYaw(playerYaw);
        return playerDirection.inverse();
    }
}