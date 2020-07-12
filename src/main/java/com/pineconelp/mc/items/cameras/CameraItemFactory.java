package com.pineconelp.mc.items.cameras;

import org.bukkit.inventory.ItemStack;

public class CameraItemFactory implements ICameraItemFactory {
    
    @Override
    public ItemStack createCameraItem(int amount) {
        return new CameraItem(amount);
    }
}