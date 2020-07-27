package com.pineconelp.mc.commands;

import java.util.UUID;

import com.google.inject.Inject;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;
import com.pineconelp.mc.models.CameraDetails;
import com.pineconelp.mc.services.permissions.ICreatePermissionChecker;
import com.pineconelp.mc.stores.CamAlertSettingsStore;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class CreateCommandHandler implements ICommandHandler {

    private ICameraItemFactory cameraItemFactory;
    private CamAlertSettingsStore camAlertSettingsStore;
    private ICreatePermissionChecker createPermissionChecker;

    @Inject
    public CreateCommandHandler(ICameraItemFactory cameraItemFactory, CamAlertSettingsStore camAlertSettingsStore,
            ICreatePermissionChecker createPermissionChecker) {
        this.cameraItemFactory = cameraItemFactory;
        this.camAlertSettingsStore = camAlertSettingsStore;
        this.createPermissionChecker = createPermissionChecker;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(createPermissionChecker.canCreate(player)) {
                double cameraRange = camAlertSettingsStore.getDefaultCameraRange();
                double cameraNotificationThresholdSeconds = camAlertSettingsStore.getDefaultCameraNotificationThresholdSeconds();
                UUID ownerId = player.getUniqueId();

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
}