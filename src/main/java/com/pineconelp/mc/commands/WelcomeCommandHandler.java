package com.pineconelp.mc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class WelcomeCommandHandler implements ICommandHandler {

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.GREEN + "Welcome to CamAlert!");
        
        return true;
    }
    
}