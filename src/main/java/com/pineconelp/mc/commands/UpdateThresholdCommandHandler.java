package com.pineconelp.mc.commands;

import com.google.inject.Inject;
import com.pineconelp.mc.items.cameras.ICameraItemDetailer;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;
import com.pineconelp.mc.items.cameras.InvalidCameraItemException;
import com.pineconelp.mc.models.CameraDetails;
import com.pineconelp.mc.services.permissions.IUpdateNotifyThresholdPermissionChecker;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpdateThresholdCommandHandler implements ICommandHandler {

    private ICameraItemDetailer cameraItemDetailer;
    private ICameraItemFactory cameraItemFactory;
    private IUpdateNotifyThresholdPermissionChecker permissionChecker;

    @Inject
    public UpdateThresholdCommandHandler(ICameraItemDetailer cameraItemDetailer, ICameraItemFactory cameraItemFactory,
            IUpdateNotifyThresholdPermissionChecker permissionChecker) {
        this.cameraItemDetailer = cameraItemDetailer;
        this.cameraItemFactory = cameraItemFactory;
        this.permissionChecker = permissionChecker;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is limited to players.");
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage("Please specify a camera notification threshold. /cam update threshold <seconds>");
            return true;
        }

        Player player = (Player) sender;
        String rawThreshold = args[0];

        try {
            double threshold = Double.parseDouble(rawThreshold);

            boolean canUpdateNotifyThreshold = permissionChecker.canUpdateNotifyThreshold(player, threshold);
            if(!canUpdateNotifyThreshold) { 
                player.sendMessage(ChatColor.RED + "You do not have permission to update to the notification threshold specified.");
                return true;
            }

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
            
            return true;
        } catch (NumberFormatException e) {
            player.sendMessage(String.format(ChatColor.RED + "Invalid camera notification threshold seconds '%s'.", rawThreshold));
            return true;
        }
    }
}