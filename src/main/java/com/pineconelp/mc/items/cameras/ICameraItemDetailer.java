package com.pineconelp.mc.items.cameras;

import com.pineconelp.mc.models.CameraDetails;

import org.bukkit.inventory.ItemStack;

public interface ICameraItemDetailer {
    CameraDetails getCameraItemDetails(ItemStack cameraItem) throws InvalidCameraItemException;
}