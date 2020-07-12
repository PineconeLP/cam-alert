package com.pineconelp.mc.items.cameras;

import java.util.ArrayList;

import com.pineconelp.mc.models.CameraDetails;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_16_R1.NBTTagCompound;
import net.minecraft.server.v1_16_R1.NBTTagDouble;
import net.minecraft.server.v1_16_R1.NBTTagInt;

public class NMSCameraItemFactory implements ICameraItemFactory, ICameraItemValidator, ICameraItemDetailer {

    private static final String VERIFIED_TAG_NAME = "verified";
    private static final String RANGE_TAG_NAME = "range";
    private static final String NOTIFICATION_THRESHOLD_SECONDS_TAG_NAME = "notification_threshold_seconds";

    @Override
    public ItemStack createCameraItem(CameraDetails details, int amount) {
        ItemStack cameraItem = new ItemStack(Material.JACK_O_LANTERN, amount);

        double range = details.getRange();
        int notificationThresholdSeconds = details.getNotificationThresholdSeconds();

        // Add display information.
        ItemMeta itemMeta = cameraItem.getItemMeta();

        String displayName = ChatColor.GOLD + "Security Camera";
        itemMeta.setDisplayName(displayName);

        ArrayList<String> lore = new ArrayList<>();
        lore.add(String.format(ChatColor.GREEN + "Range: %d", (int)range));
        lore.add(String.format(ChatColor.GREEN + "Notification Interval: %d", notificationThresholdSeconds));
        itemMeta.setLore(lore);

        cameraItem.setItemMeta(itemMeta);

        // Add NBT tag information.
        net.minecraft.server.v1_16_R1.ItemStack craftCameraItem = CraftItemStack.asNMSCopy(cameraItem);

        NBTTagCompound cameraItemTag = craftCameraItem.hasTag() ? craftCameraItem.getTag() : new NBTTagCompound();
        cameraItemTag.set(VERIFIED_TAG_NAME, NBTTagInt.a(1));
        cameraItemTag.set(RANGE_TAG_NAME, NBTTagDouble.a(range));
        cameraItemTag.set(NOTIFICATION_THRESHOLD_SECONDS_TAG_NAME, NBTTagInt.a(notificationThresholdSeconds));

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
    public CameraDetails getCameraItemDetails(ItemStack itemStack) throws InvalidCameraItemException {
        net.minecraft.server.v1_16_R1.ItemStack craftCameraItem = CraftItemStack.asNMSCopy(itemStack);

        if(!craftCameraItem.hasTag()) {
            throw new InvalidCameraItemException(itemStack);
        }

        NBTTagCompound cameraItemTag = craftCameraItem.getTag();
        
        double range = cameraItemTag.getDouble(RANGE_TAG_NAME);
        int notificationThresholdSeconds = cameraItemTag.getInt(NOTIFICATION_THRESHOLD_SECONDS_TAG_NAME);

        return new CameraDetails(range, notificationThresholdSeconds);
    }
}