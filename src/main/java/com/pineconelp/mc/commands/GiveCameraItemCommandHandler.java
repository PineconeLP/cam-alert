package com.pineconelp.mc.commands;

import com.google.inject.Inject;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;
import com.pineconelp.mc.models.CameraDetails;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class GiveCameraItemCommandHandler implements ICommandHandler {

    private ICameraItemFactory cameraItemFactory;

    @Inject
    public GiveCameraItemCommandHandler(ICameraItemFactory cameraItemFactory) {
        this.cameraItemFactory = cameraItemFactory;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            int cameraRange = 10;
            int cameraNotificationThresholdSeconds = 5;

            if (args.length > 0) {
                try {
                    cameraRange = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    player.sendMessage(String.format(ChatColor.RED + "Invalid camera range '%s'. Using default of %d.", args[0], cameraRange));
                }

                if (args.length > 1) {
                    try {
                        cameraNotificationThresholdSeconds = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        player.sendMessage(String.format(ChatColor.RED + "Invalid camera notification threshold seconds '%s'. Using default of %d.", args[1], cameraNotificationThresholdSeconds));
                    }
                }
            }

            CameraDetails details = new CameraDetails(cameraRange, cameraNotificationThresholdSeconds);
            ItemStack cameraItem = cameraItemFactory.createCameraItem(details, 1);
            player.getInventory().addItem(cameraItem);

            return true;
        }

        return false;
    }
}