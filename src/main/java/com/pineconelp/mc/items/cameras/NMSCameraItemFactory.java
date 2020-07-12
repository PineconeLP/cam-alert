package com.pineconelp.mc.items.cameras;

import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R1.NBTTagCompound;
import net.minecraft.server.v1_16_R1.NBTTagInt;

public class NMSCameraItemFactory implements ICameraItemFactory, ICameraItemValidator{

    private static final String VERIFIED_TAG_NAME = "verified";

    @Override
    public ItemStack createCameraItem(int amount) {
        CameraItem cameraItem = new CameraItem(amount);

        net.minecraft.server.v1_16_R1.ItemStack craftCameraItem = CraftItemStack.asNMSCopy(cameraItem);

        NBTTagCompound cameraItemTag = craftCameraItem.hasTag() ? craftCameraItem.getTag() : new NBTTagCompound();
        cameraItemTag.set(VERIFIED_TAG_NAME, NBTTagInt.a(1));

        craftCameraItem.setTag(cameraItemTag);

        return CraftItemStack.asBukkitCopy(craftCameraItem);
    }

    @Override
    public boolean isCameraItem(ItemStack itemStack) {
        net.minecraft.server.v1_16_R1.ItemStack craftCameraItem = CraftItemStack.asNMSCopy(itemStack);

        if(craftCameraItem.hasTag()) {
            NBTTagCompound cameraItemTag = craftCameraItem.getTag();
            
            return cameraItemTag.hasKey(VERIFIED_TAG_NAME);
        }

        return false;
    }
}