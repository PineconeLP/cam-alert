package com.pineconelp.mc.events;

import com.google.inject.Inject;
import com.pineconelp.mc.items.cameras.ICameraItemValidator;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class CameraPlacedEventListener implements Listener{

    private ICameraItemValidator cameraItemValidator;

    @Inject
    public CameraPlacedEventListener(ICameraItemValidator cameraItemValidator) {
        this.cameraItemValidator = cameraItemValidator;
    }

    @EventHandler
    public void onCameraPlaced(BlockPlaceEvent blockPlaceEvent) {
        ItemStack itemPlaced = blockPlaceEvent.getItemInHand();

        if(cameraItemValidator.isCameraItem(itemPlaced)) {
            blockPlaceEvent.getPlayer().sendMessage("You placed a camera!");
        }
    }
}