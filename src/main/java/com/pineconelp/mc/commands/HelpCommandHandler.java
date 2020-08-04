package com.pineconelp.mc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCommandHandler implements ICommandHandler {

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        sender.sendMessage(new String[] { 
            ChatColor.BLUE + "-=-=-=- Cam Alert Help -=-=-=-",
            createCommandDescription("/cam create", "Create a camera with default settings."),
            createCommandDescription("/cam update range <block amount>", "Update the camera in hand with a new range."),
            createCommandDescription("/cam update threshold <seconds>", "Update the camera in hand with a new notification threshold. The notification threshold determines the minimum seconds between notifications for repeating intruders."),
            createCommandDescription("/cam update owner <player name>", "Update the camera in hand with a new owner. The owner will receive all camera notifications."),
        });

        return true;
    }

    private String createCommandDescription(String command, String description) {
        return ChatColor.GREEN + command + ChatColor.WHITE + " - " + description;
    }
}