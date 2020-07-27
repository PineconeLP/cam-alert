package com.pineconelp.mc.commands;

import com.google.inject.Inject;
import com.pineconelp.mc.items.cameras.ICameraItemDetailer;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;
import com.pineconelp.mc.items.cameras.InvalidCameraItemException;
import com.pineconelp.mc.models.CameraDetails;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpdateThresholdCommandHandler implements ICommandHandler {

    private ICameraItemDetailer cameraItemDetailer;
    private ICameraItemFactory cameraItemFactory;

    @Inject
    public UpdateThresholdCommandHandler(ICameraItemDetailer cameraItemDetailer, ICameraItemFactory cameraItemFactory) {
        this.cameraItemDetailer = cameraItemDetailer;
		this.cameraItemFactory = cameraItemFactory;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length > 0) {
                String rawThreshold = args[0];

                try {
                    double threshold = Double.parseDouble(rawThreshold);

                    ItemStack heldItem = player.getInventory().getItemInMainHand();
                    int amount = heldItem.getAmount();

                    try {
                        CameraDetails details = cameraItemDetailer.getCameraItemDetails(heldItem);

                        ItemStack updatedCameraItem = cameraItemFactory.createCameraItem(details.cloneWithThreshold(threshold), amount);
                        player.getInventory().setItemInMainHand(updatedCameraItem);

                        player.sendMessage(ChatColor.GREEN + String.format("Successfully updated notification threshold to %ds.", (int)threshold));
                    } catch (InvalidCameraItemException ex) {
                        player.sendMessage(ChatColor.RED + "Please hold the camera you wish to update.");
                    } 
                } catch (Exception e) {
                    player.sendMessage(String.format(ChatColor.RED + "Invalid camera notification threshold seconds '%s'.", rawThreshold));
                }
            } else {
                player.sendMessage("Please specify a camera notification threshold.");
            }
        }

        return true;
    }
}