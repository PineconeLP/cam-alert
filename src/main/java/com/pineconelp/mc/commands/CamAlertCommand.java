package com.pineconelp.mc.commands;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class CamAlertCommand implements CommandExecutor {

    private ICommandHandler noArgsHandler;
    private ICommandHandler giveCameraCommandHandler;

    @Inject
    public CamAlertCommand(@Named("NoArgsHandler") ICommandHandler noArgsHandler,
            @Named("GiveCameraItemCommandHandler") ICommandHandler giveCameraCommandHandler) {
        this.noArgsHandler = noArgsHandler;
        this.giveCameraCommandHandler = giveCameraCommandHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandName, String[] args) {
        if(args.length == 0) {
            return noArgsHandler.handle(sender, args);
        } else {
            if(args[0].equalsIgnoreCase("create")) {
                return giveCameraCommandHandler.handle(sender, args);
            }
        }

        sender.sendMessage(ChatColor.RED + "Unknown Cam Alert command.");

        return false;
    }
}