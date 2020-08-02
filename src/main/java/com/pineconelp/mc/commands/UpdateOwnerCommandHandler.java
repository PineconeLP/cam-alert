package com.pineconelp.mc.commands;

import java.util.UUID;

import com.google.inject.Inject;
import com.pineconelp.mc.items.cameras.ICameraItemDetailer;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;
import com.pineconelp.mc.items.cameras.InvalidCameraItemException;
import com.pineconelp.mc.models.CameraDetails;
import com.pineconelp.mc.services.permissions.IUpdateOwnerPermissionChecker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpdateOwnerCommandHandler implements ICommandHandler {

    private ICameraItemDetailer cameraItemDetailer;
    private ICameraItemFactory cameraItemFactory;
    private IUpdateOwnerPermissionChecker permissionChecker;

    @Inject
    public UpdateOwnerCommandHandler(ICameraItemDetailer cameraItemDetailer, ICameraItemFactory cameraItemFactory,
            IUpdateOwnerPermissionChecker permissionChecker) {
        this.cameraItemDetailer = cameraItemDetailer;
        this.cameraItemFactory = cameraItemFactory;
        this.permissionChecker = permissionChecker;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length > 0) {
                String playerName = args[0];
                Player ownerPlayer = Bukkit.getPlayer(playerName);

                if (ownerPlayer != null) {
                    String ownerPlayerName = ownerPlayer.getName();

                    if(permissionChecker.canUpdateOwner(player, ownerPlayerName)) {
                        UUID ownerId = ownerPlayer.getUniqueId();
                        ItemStack heldItem = player.getInventory().getItemInMainHand();
                        int amount = heldItem.getAmount();
    
                        try {
                            CameraDetails details = cameraItemDetailer.getCameraItemDetails(heldItem);
    
                            ItemStack updatedCameraItem = cameraItemFactory.createCameraItem(details.cloneWithOwnerPlayerId(ownerId), amount);
                            player.getInventory().setItemInMainHand(updatedCameraItem);
    
                            player.sendMessage(ChatColor.GREEN + String.format("Successfully updated owner to '%s'.", ownerPlayerName));
                        } catch (InvalidCameraItemException ex) {
                            player.sendMessage(ChatColor.RED + "Please hold the camera you wish to update.");
                        } 
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission to update to the owner specified.");
                    }
                } else {
                    player.sendMessage(String.format(ChatColor.RED + "Online player '%s' not found.", playerName));
                }
            } else {
                player.sendMessage("Please specify the name of the new camera owner.");
            }
        }

        return true;
    }
}