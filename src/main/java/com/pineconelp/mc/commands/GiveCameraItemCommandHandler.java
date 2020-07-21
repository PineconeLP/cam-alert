package com.pineconelp.mc.commands;

import java.util.UUID;

import com.google.inject.Inject;
import com.pineconelp.mc.constants.Permission;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;
import com.pineconelp.mc.models.CameraDetails;
import com.pineconelp.mc.stores.CamAlertSettingsStore;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class GiveCameraItemCommandHandler implements ICommandHandler {

    private ICameraItemFactory cameraItemFactory;
    private CamAlertSettingsStore camAlertSettingsStore;

    @Inject
    public GiveCameraItemCommandHandler(ICameraItemFactory cameraItemFactory,
            CamAlertSettingsStore camAlertSettingsStore) {
        this.cameraItemFactory = cameraItemFactory;
        this.camAlertSettingsStore = camAlertSettingsStore;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(player.hasPermission(Permission.CAM_ALERT_CREATE)) {
                double cameraRange = camAlertSettingsStore.getDefaultCameraRange();
                double cameraNotificationThresholdSeconds = camAlertSettingsStore.getDefaultCameraNotificationThresholdSeconds();
                UUID ownerId = player.getUniqueId();
    
                if (args.length > 0) {
                    cameraRange = getRange(args[0], cameraRange, player);
    
                    if (args.length > 1) {
                        cameraNotificationThresholdSeconds = getNotificationThreshold(args[1], cameraNotificationThresholdSeconds, player);
    
                        if (args.length > 2) {
                            ownerId = getOwnerId(args[2], ownerId, player);
                        }
                    }
                }

                CameraDetails details = new CameraDetails(cameraRange, cameraNotificationThresholdSeconds, ownerId);
                ItemStack cameraItem = cameraItemFactory.createCameraItem(details, 1);
    
                player.getInventory().addItem(cameraItem);
                player.sendMessage(ChatColor.GREEN + "Camera created.");
    
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to create cameras.");
                return true;
            }
        }

        return false;
    }

    private double getRange(String input, double fallback, Player player) {
        double cameraRange;

        try {
            cameraRange = Double.parseDouble(input);
        } catch (Exception e) {
            player.sendMessage(String.format(ChatColor.RED + "Invalid camera range '%s'. Using default of %d.", input, (int)fallback));
            cameraRange = fallback;
        }
        return cameraRange;
    }

    private double getNotificationThreshold(String input, double fallback, Player player) {
        double cameraNotificationThresholdSeconds;

        try {
            cameraNotificationThresholdSeconds = Double.parseDouble(input);
        } catch (Exception e) {
            player.sendMessage(String.format(ChatColor.RED + "Invalid camera notification threshold seconds '%s'. Using default of %d.", input, (int)fallback));
            cameraNotificationThresholdSeconds = fallback;
        }

        return cameraNotificationThresholdSeconds;
    }

    private UUID getOwnerId(String input, UUID fallback, Player player) {
        UUID ownerId;

        String ownerPlayerName = input;
        Player ownerPlayer = Bukkit.getPlayer(ownerPlayerName);

        if (ownerPlayer != null) {
            ownerId = ownerPlayer.getUniqueId();
        } else {
            player.sendMessage(String.format(ChatColor.RED + "Online player '%s' not found. Assigning '%s' as camera owner.", ownerPlayerName, player.getName()));
            ownerId = fallback;
        }

        return ownerId;
    }
}