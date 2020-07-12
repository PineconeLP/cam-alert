package com.pineconelp.mc.listeners;

import com.google.inject.Inject;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraLocation;
import com.pineconelp.mc.stores.CameraStore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class CameraDestroyedListener implements Listener {

    private CameraStore cameraStore;
    private ICameraItemFactory cameraItemFactory;

    @Inject
    public CameraDestroyedListener(CameraStore cameraStore, ICameraItemFactory cameraItemFactory) {
        this.cameraStore = cameraStore;
		this.cameraItemFactory = cameraItemFactory;
    }

    @EventHandler
    public void onCameraDestroyed(BlockBreakEvent blockBreakEvent) {
        Location blockBreakLocation = blockBreakEvent.getBlock().getLocation();
        CameraLocation cameraLocation = new CameraLocation(blockBreakLocation);

        if(cameraStore.hasCamera(cameraLocation)) {
            Camera brokenCamera = cameraStore.removeCamera(cameraLocation);

            Player blockBreakPlayer = blockBreakEvent.getPlayer();
            blockBreakPlayer.sendMessage(ChatColor.GREEN + "Camera destroyed.");

            Player cameraOwnerPlayer = Bukkit.getPlayer(brokenCamera.getOwnerPlayerId());
            if(cameraOwnerPlayer != null && cameraOwnerPlayer != blockBreakPlayer) {
                cameraOwnerPlayer.sendMessage(ChatColor.RED + "Your camera was destroyed!");
            }

            blockBreakEvent.setDropItems(false);

            ItemStack cameraItem = cameraItemFactory.createCameraItem(brokenCamera.getRange(), 1);
            blockBreakPlayer.getWorld().dropItemNaturally(blockBreakEvent.getBlock().getLocation(), cameraItem);
        }
    }
}