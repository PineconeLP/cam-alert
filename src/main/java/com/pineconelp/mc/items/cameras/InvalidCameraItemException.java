package com.pineconelp.mc.items.cameras;

import org.bukkit.inventory.ItemStack;

public class InvalidCameraItemException extends Exception {

    private static final long serialVersionUID = 1L;

    private ItemStack itemStack;

    public InvalidCameraItemException(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    
    public ItemStack getItemStack() {
        return itemStack;
    }
}
