package com.pineconelp.mc.commands;

import java.util.UUID;

import com.google.inject.Inject;
import com.pineconelp.mc.items.cameras.ICameraItemDetailer;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;
import com.pineconelp.mc.items.cameras.InvalidCameraItemException;
import com.pineconelp.mc.models.CameraDetails;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpdateOwnerCommandHandler implements ICommandHandler {

    private ICameraItemDetailer cameraItemDetailer;
    private ICameraItemFactory cameraItemFactory;

    @Inject
    public UpdateOwnerCommandHandler(ICameraItemDetailer cameraItemDetailer, ICameraItemFactory cameraItemFactory) {
        this.cameraItemDetailer = cameraItemDetailer;
		this.cameraItemFactory = cameraItemFactory;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length > 0) {
                String playerName = args[0];
                Player ownerPlayer = Bukkit.getPlayer(playerName);

                if (ownerPlayer != null) {
                    UUID ownerId = ownerPlayer.getUniqueId();

                    ItemStack heldItem = player.getInventory().getItemInMainHand();
                    int amount = heldItem.getAmount();

                    try {
                        CameraDetails details = cameraItemDetailer.getCameraItemDetails(heldItem);

                        ItemStack updatedCameraItem = cameraItemFactory.createCameraItem(details.cloneWithOwnerPlayerId(ownerId), amount);
                        player.getInventory().setItemInMainHand(updatedCameraItem);

                        player.sendMessage(ChatColor.GREEN + String.format("Successfully updated owner to '%s'.", ownerPlayer.getDisplayName()));
                    } catch (InvalidCameraItemException ex) {
                        player.sendMessage(ChatColor.RED + "Please hold the camera you wish to update.");
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