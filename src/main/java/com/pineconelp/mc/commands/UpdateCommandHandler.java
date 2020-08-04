package com.pineconelp.mc.commands;

import java.util.Arrays;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.bukkit.command.CommandSender;

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
        boolean handled = false;

        if(args.length > 0) {
            String arg = args[0];

            if(arg.equalsIgnoreCase("range")) {
                handled = updateRangeHandler.handle(sender, Arrays.copyOfRange(args, 1, args.length));
            } else if(arg.equalsIgnoreCase("threshold")) {
                handled = updateThresholdHandler.handle(sender, Arrays.copyOfRange(args, 1, args.length));
            } else if(arg.equalsIgnoreCase("owner")) {
                handled = updateOwnerHandler.handle(sender, Arrays.copyOfRange(args, 1, args.length));
            } 
        }

        if(!handled) {

        }

        return handled;
    }
}