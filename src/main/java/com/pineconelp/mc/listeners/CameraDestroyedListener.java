package com.pineconelp.mc.listeners;

import com.google.inject.Inject;
import com.pineconelp.mc.exceptions.CameraRemoveException;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraLocation;
import com.pineconelp.mc.stores.CameraStore;
import com.pineconelp.mc.utilities.TaskRunner;

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
    private TaskRunner taskRunner;

    @Inject
    public CameraDestroyedListener(CameraStore cameraStore, ICameraItemFactory cameraItemFactory,
            TaskRunner taskRunner) {
        this.cameraStore = cameraStore;
        this.cameraItemFactory = cameraItemFactory;
        this.taskRunner = taskRunner;
    }

    @EventHandler
    public void onCameraDestroyed(BlockBreakEvent blockBreakEvent) {
        Location blockBreakLocation = blockBreakEvent.getBlock().getLocation();
        CameraLocation cameraLocation = new CameraLocation(blockBreakLocation);

        if (cameraStore.hasCamera(cameraLocation)) {
            Player blockBreakPlayer = blockBreakEvent.getPlayer();

            taskRunner.runTaskAsync(new Runnable() {
                @Override
                public void run() {
                    Camera brokenCamera;

                    try {
                        blockBreakPlayer.sendMessage(ChatColor.GREEN + "Removing camera...");
                        brokenCamera = cameraStore.removeCamera(cameraLocation);
                        blockBreakPlayer.sendMessage(ChatColor.GREEN + "Camera removed.");
        
                        Player cameraOwnerPlayer = Bukkit.getPlayer(brokenCamera.getOwnerPlayerId());
                        if(cameraOwnerPlayer != null && cameraOwnerPlayer != blockBreakPlayer) {
                            cameraOwnerPlayer.sendMessage(ChatColor.RED + "Your camera was destroyed!");
                        }
                    } catch (CameraRemoveException e) {
                        brokenCamera = e.getCamera();
                        e.printStackTrace();
                        blockBreakPlayer.sendMessage(ChatColor.RED + "Failed to remove camera. Replace this camera or it will continue to notify from this location after the next server restart.");
                    } 

                    Camera dropCamera = brokenCamera;
                    taskRunner.runTask(new Runnable(){
						@Override
						public void run() {
                            blockBreakEvent.setDropItems(false);
    
                            ItemStack cameraItem = cameraItemFactory.createCameraItem(dropCamera.getCameraDetails(), 1);
                            blockBreakPlayer.getWorld().dropItemNaturally(blockBreakEvent.getBlock().getLocation(), cameraItem);
						}
                    });
                }
            });
        }
    }
}