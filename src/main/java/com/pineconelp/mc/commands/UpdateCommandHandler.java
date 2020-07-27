package com.pineconelp.mc.commands;

import java.util.Arrays;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UpdateCommandHandler implements ICommandHandler {

    private ICommandHandler updateRangeHandler;
    private ICommandHandler updateThresholdHandler;
    private ICommandHandler updateOwnerHandler;

    @Inject
    public UpdateCommandHandler(@Named("UpdateRangeCommandHandler") ICommandHandler updateRangeHandler,
            @Named("UpdateThresholdCommandHandler") ICommandHandler updateThresholdHandler,
            @Named("UpdateOwnerCommandHandler") ICommandHandler updateOwnerHandler) {
        this.updateRangeHandler = updateRangeHandler;
        this.updateThresholdHandler = updateThresholdHandler;
        this.updateOwnerHandler = updateOwnerHandler;

    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if(args.length == 0) {
            return handleUnknownUpdateCommand(sender);
        } else {
            String arg = args[0];

            if(arg.equalsIgnoreCase("range")) {
                return updateRangeHandler.handle(sender, Arrays.copyOfRange(args, 1, args.length));
            } else if(arg.equalsIgnoreCase("threshold")) {
                return updateThresholdHandler.handle(sender, Arrays.copyOfRange(args, 1, args.length));
            } else if(arg.equalsIgnoreCase("owner")) {
                return updateOwnerHandler.handle(sender, Arrays.copyOfRange(args, 1, args.length));
            }
        }

        return handleUnknownUpdateCommand(sender);
    }

    private boolean handleUnknownUpdateCommand(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            
            player.sendMessage(ChatColor.RED + "Unknown update command.");
        }

        return true;
    }
}