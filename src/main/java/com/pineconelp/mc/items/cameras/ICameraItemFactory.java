package com.pineconelp.mc.items.cameras;

import com.pineconelp.mc.models.CameraDetails;

import org.bukkit.inventory.ItemStack;

public interface ICameraItemFactory {
    ItemStack createCameraItem(CameraDetails details, int amount);
}
