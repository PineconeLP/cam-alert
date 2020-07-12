package com.pineconelp.mc.commands;

import com.google.inject.Inject;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;
import com.pineconelp.mc.models.CameraDetails;
import com.pineconelp.mc.stores.CamAlertSettingsStore;

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

            if (args.length > 0) {
                try {
                    cameraRange = Double.parseDouble(args[0]);
                } catch (Exception e) {
                    player.sendMessage(String.format(ChatColor.RED + "Invalid camera range '%s'. Using default of %d.", args[0], (int)cameraRange));
                }

                if (args.length > 1) {
                    try {
                        cameraNotificationThresholdSeconds = Double.parseDouble(args[1]);
                    } catch (Exception e) {
                        player.sendMessage(String.format(ChatColor.RED + "Invalid camera notification threshold seconds '%s'. Using default of %d.", args[1], (int)cameraNotificationThresholdSeconds));
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