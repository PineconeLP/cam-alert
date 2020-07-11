package com.pineconelp.mc.commands;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class CamAlertCommand implements CommandExecutor {

    private ICommandHandler noArgsHandler;

    @Inject
    public CamAlertCommand(@Named("NoArgsHandler") ICommandHandler noArgsHandler) {
        this.noArgsHandler = noArgsHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandName, String[] args) {
        if(args.length == 0) {
            return noArgsHandler.handle(sender, args);
        }

        sender.sendMessage(ChatColor.RED + "Unknown Cam Alert command.");

        return false;
    }
}