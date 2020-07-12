package com.pineconelp.mc.commands;

import com.google.inject.Inject;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCameraItemCommandHandler implements ICommandHandler {

    private ICameraItemFactory cameraItemFactory;

    @Inject
    public GiveCameraItemCommandHandler(ICameraItemFactory cameraItemFactory) {
        this.cameraItemFactory = cameraItemFactory;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            ItemStack cameraItem = cameraItemFactory.createCameraItem(1);
            player.getInventory().addItem(cameraItem);

            return true;
        }

        return false;
    }
}