package com.pineconelp.mc.items.cameras;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class CameraItem extends ItemStack {
    public CameraItem(int amount) {
        super(Material.DIAMOND_BLOCK, amount);

        ItemMeta itemMeta = this.getItemMeta();

        String displayName = ChatColor.GOLD + "Security Camera";
        itemMeta.setDisplayName(displayName);

        this.setItemMeta(itemMeta);
    }
}