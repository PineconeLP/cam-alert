package com.pineconelp.mc.items.cameras;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_16_R1.NBTTagCompound;
import net.minecraft.server.v1_16_R1.NBTTagDouble;
import net.minecraft.server.v1_16_R1.NBTTagInt;

public class NMSCameraItemFactory implements ICameraItemFactory, ICameraItemValidator, ICameraItemDetailer{

    private static final String VERIFIED_TAG_NAME = "verified";
    private static final String RANGE_TAG_NAME = "range";

    @Override
    public ItemStack createCameraItem(double range, int amount) {
        ItemStack cameraItem = new ItemStack(Material.JACK_O_LANTERN, amount);

        // Add display information.
        ItemMeta itemMeta = cameraItem.getItemMeta();

        String displayName = ChatColor.GOLD + "Security Camera";
        itemMeta.setDisplayName(displayName);

        ArrayList<String> lore = new ArrayList<>();
        lore.add(String.format(ChatColor.GREEN + "Range: %d", (int)range));
        itemMeta.setLore(lore);

        cameraItem.setItemMeta(itemMeta);

        // Add NBT tag information.
        net.minecraft.server.v1_16_R1.ItemStack craftCameraItem = CraftItemStack.asNMSCopy(cameraItem);

        NBTTagCompound cameraItemTag = craftCameraItem.hasTag() ? craftCameraItem.getTag() : new NBTTagCompound();
        cameraItemTag.set(VERIFIED_TAG_NAME, NBTTagInt.a(1));
        cameraItemTag.set(RANGE_TAG_NAME, NBTTagDouble.a(range));

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

    @Override
    public CameraItemDetails getCameraItemDetails(ItemStack itemStack) throws InvalidCameraItemException {
        net.minecraft.server.v1_16_R1.ItemStack craftCameraItem = CraftItemStack.asNMSCopy(itemStack);

        if(!craftCameraItem.hasTag()) {
            throw new InvalidCameraItemException(itemStack);
        }

        NBTTagCompound cameraItemTag = craftCameraItem.getTag();
        
        double range = cameraItemTag.getDouble(RANGE_TAG_NAME);

        return new CameraItemDetails(range);
    }
}