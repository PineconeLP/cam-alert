package com.pineconelp.mc.items.cameras;

import org.bukkit.inventory.ItemStack;

public interface ICameraItemDetailer {
    CameraItemDetails getCameraItemDetails(ItemStack cameraItem) throws InvalidCameraItemException;
}