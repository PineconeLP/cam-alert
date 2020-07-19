package com.pineconelp.mc.commands;

import java.util.UUID;

import com.google.inject.Inject;
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

            double cameraRange = camAlertSettingsStore.getDefaultCameraRange();
            double cameraNotificationThresholdSeconds = camAlertSettingsStore.getDefaultCameraNotificationThresholdSeconds();
            UUID ownerId = player.getUniqueId();

            if (args.length > 0) {

                // Get camera range.
                try {
                    cameraRange = Double.parseDouble(args[0]);
                } catch (Exception e) {
                    player.sendMessage(String.format(ChatColor.RED + "Invalid camera range '%s'. Using default of %d.", args[0], (int)cameraRange));
                }

                if (args.length > 1) {

                    // Get notification threshold.
                    try {
                        cameraNotificationThresholdSeconds = Double.parseDouble(args[1]);
                    } catch (Exception e) {
                        player.sendMessage(String.format(ChatColor.RED + "Invalid camera notification threshold seconds '%s'. Using default of %d.", args[1], (int)cameraNotificationThresholdSeconds));
                    }

                    if (args.length > 2) {

                        // Get owner player.
                        String ownerPlayerName = args[2];
                        Player ownerPlayer = Bukkit.getPlayer(ownerPlayerName);

                        if(ownerPlayer != null) {
                            ownerId = ownerPlayer.getUniqueId();
                        } else {
                            player.sendMessage(String.format(ChatColor.RED + "Online player '%s' not found. Assigning '%s' as camera owner.", ownerPlayerName, player.getName()));
                        }
                    }
                }
            }

            CameraDetails details = new CameraDetails(cameraRange, cameraNotificationThresholdSeconds, ownerId);
            ItemStack cameraItem = cameraItemFactory.createCameraItem(details, 1);

            player.getInventory().addItem(cameraItem);
            player.sendMessage(ChatColor.GREEN + "Camera created.");

            return true;
        }

        return false;
    }
}