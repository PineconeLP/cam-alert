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

public class UpdateCommandHandler implements ICommandHandler {

    private ICameraItemDetailer cameraItemDetailer;
    private ICameraItemFactory cameraItemFactory;

    @Inject
    public UpdateCommandHandler(ICameraItemDetailer cameraItemDetailer, ICameraItemFactory cameraItemFactory) {
        this.cameraItemDetailer = cameraItemDetailer;
        this.cameraItemFactory = cameraItemFactory;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack heldItem = player.getInventory().getItemInMainHand();
            int amount = heldItem.getAmount();

            try {
                CameraDetails details = cameraItemDetailer.getCameraItemDetails(heldItem);
                double range = details.getRange();
                double threshold = details.getNotificationThresholdSeconds();
                UUID ownerId = details.getOwnerPlayerId();

                if(args.length > 1) {
                    String updateField = args[0];
                    String updateValue = args[1];

                    if(updateField.equalsIgnoreCase("range")) {
                        try {
                            range = Double.parseDouble(updateValue);
                            updateCameraItemInHand(player, new CameraDetails(range, threshold, ownerId), amount);
                            player.sendMessage(ChatColor.GREEN + String.format("Successfully updated range to %dm.", (int)range));
                        } catch (Exception e) {
                            player.sendMessage(String.format(ChatColor.RED + "Invalid camera range '%s'.", updateValue));
                        }
                    } else if(updateField.equalsIgnoreCase("threshold")) {
                        try {
                            threshold = Double.parseDouble(updateValue);
                            updateCameraItemInHand(player, new CameraDetails(range, threshold, ownerId), amount);
                            player.sendMessage(ChatColor.GREEN + String.format("Successfully updated notification threshold seconds to %ds.", (int)threshold));
                        } catch (Exception e) {
                            player.sendMessage(String.format(ChatColor.RED + "Invalid camera notification threshold seconds '%s'.", updateValue));
                        }
                    } else if(updateField.equalsIgnoreCase("owner")) {
                        Player ownerPlayer = Bukkit.getPlayer(updateValue);

                        if (ownerPlayer != null) {
                            ownerId = ownerPlayer.getUniqueId();
                            updateCameraItemInHand(player, new CameraDetails(range, threshold, ownerId), amount);
                            player.sendMessage(ChatColor.GREEN + String.format("Successfully updated owner to '%s'.", ownerPlayer.getDisplayName()));
                        } else {
                            player.sendMessage(String.format(ChatColor.RED + "Online player '%s' not found.", updateValue));
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Unknown update command.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Unknown update command.");
                }
            } catch (InvalidCameraItemException ex) {
                player.sendMessage(ChatColor.RED + "Please hold the camera you wish to update.");
            }

            return true;
        }

        return false;
    }

    private void updateCameraItemInHand(Player player, CameraDetails details, int amount) {
        ItemStack updatedCameraItem = cameraItemFactory.createCameraItem(details, amount);
        player.getInventory().setItemInMainHand(updatedCameraItem);
    }
}