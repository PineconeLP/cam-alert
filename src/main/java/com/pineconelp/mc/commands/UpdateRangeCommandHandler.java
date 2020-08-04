package com.pineconelp.mc.commands;

import com.google.inject.Inject;
import com.pineconelp.mc.items.cameras.ICameraItemDetailer;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;
import com.pineconelp.mc.items.cameras.InvalidCameraItemException;
import com.pineconelp.mc.models.CameraDetails;
import com.pineconelp.mc.services.permissions.IUpdateRangePermissionChecker;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpdateRangeCommandHandler implements ICommandHandler {

    private ICameraItemDetailer cameraItemDetailer;
    private ICameraItemFactory cameraItemFactory;
    private IUpdateRangePermissionChecker updatePermissionChecker;

    @Inject
    public UpdateRangeCommandHandler(ICameraItemDetailer cameraItemDetailer, ICameraItemFactory cameraItemFactory,
            IUpdateRangePermissionChecker updatePermissionChecker) {
        this.cameraItemDetailer = cameraItemDetailer;
        this.cameraItemFactory = cameraItemFactory;
        this.updatePermissionChecker = updatePermissionChecker;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is limited to players.");
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage("Please specify a camera range. /cam update range <block amount>");
            return true;
        }

        Player player = (Player) sender;
        String rawRange = args[0];

        try {
            double range = Double.parseDouble(rawRange);
            boolean canUpdateRange = updatePermissionChecker.canUpdateRange(player, range);

            if(!canUpdateRange) {
                player.sendMessage(ChatColor.RED + "You do not have permission to update to the range specified.");
                return true;
            }

            ItemStack heldItem = player.getInventory().getItemInMainHand();
            int amount = heldItem.getAmount();

            try {
                CameraDetails details = cameraItemDetailer.getCameraItemDetails(heldItem);

                ItemStack updatedCameraItem = cameraItemFactory.createCameraItem(details.cloneWithRange(range), amount);
                player.getInventory().setItemInMainHand(updatedCameraItem);

                player.sendMessage(ChatColor.GREEN + String.format("Successfully updated range to %dm.", (int)range));
            } catch (InvalidCameraItemException ex) {
                player.sendMessage(ChatColor.RED + "Please hold the camera you wish to update.");
            } 

            return true;
        } catch (NumberFormatException e) {
            player.sendMessage(String.format(ChatColor.RED + "Invalid camera range '%s'.", rawRange));
            return true;
        }
    }
}