package com.pineconelp.mc.items.cameras;

import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R1.NBTTagCompound;
import net.minecraft.server.v1_16_R1.NBTTagInt;

public class NMSCameraItemFactory implements ICameraItemFactory{

    @Override
    public ItemStack createCameraItem(int amount) {
        CameraItem cameraItem = new CameraItem(amount);

        net.minecraft.server.v1_16_R1.ItemStack craftCameraItem = CraftItemStack.asNMSCopy(cameraItem);

        NBTTagCompound cameraItemTag = (craftCameraItem.hasTag()) ? craftCameraItem.getTag() : new NBTTagCompound();
        cameraItemTag.set("verified", NBTTagInt.a(1));

        craftCameraItem.setTag(cameraItemTag);

        return CraftItemStack.asBukkitCopy(craftCameraItem);
    }
}