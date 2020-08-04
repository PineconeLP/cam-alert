package com.pineconelp.mc.commands;

import java.util.Arrays;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CamAlertCommand implements CommandExecutor {

    private ICommandHandler helpHandler;
    private ICommandHandler createHandler;
    private ICommandHandler updateHandler;

    @Inject
    public CamAlertCommand(@Named("HelpHandler") ICommandHandler helpHandler,
            @Named("CreateCommandHandler") ICommandHandler createHandler,
            @Named("UpdateCommandHandler") ICommandHandler updateHandler) {
        this.helpHandler = helpHandler;
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandName, String[] args) {
        boolean handled = false;

        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("create")) {
                handled = createHandler.handle(sender, Arrays.copyOfRange(args, 1, args.length));
            } else if(args[0].equalsIgnoreCase("update")) {
                handled = updateHandler.handle(sender, Arrays.copyOfRange(args, 1, args.length));
            } else if(args[0].equalsIgnoreCase("help")) {
                handled = helpHandler.handle(sender, Arrays.copyOfRange(args, 1, args.length));
            }
        }

        if(!handled) {
            handled = helpHandler.handle(sender, Arrays.copyOfRange(args, 1, args.length));
        }

        return handled;
    }
}