package com.pineconelp.mc.commands;

import java.util.Arrays;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class CamAlertCommand implements CommandExecutor {

    private ICommandHandler noArgsHandler;
    private ICommandHandler createHandler;
    private ICommandHandler updateHandler;

    @Inject
    public CamAlertCommand(@Named("NoArgsHandler") ICommandHandler noArgsHandler,
            @Named("CreateCommandHandler") ICommandHandler createHandler,
            @Named("UpdateCommandHandler") ICommandHandler updateHandler) {
        this.noArgsHandler = noArgsHandler;
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandName, String[] args) {
        if(args.length == 0) {
            return noArgsHandler.handle(sender, args);
        } else {
            if(args[0].equalsIgnoreCase("create")) {
                return createHandler.handle(sender, Arrays.copyOfRange(args, 1, args.length));
            } else if(args[0].equalsIgnoreCase("update")) {
                return updateHandler.handle(sender, Arrays.copyOfRange(args, 1, args.length));
            }
        }

        sender.sendMessage(ChatColor.RED + "Unknown Cam Alert command.");

        return false;
    }
}