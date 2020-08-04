package com.pineconelp.mc.commands;

import org.bukkit.command.CommandSender;

public class HelpCommandHandler implements ICommandHandler {

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        sender.sendMessage("hello \n world");
        return false;
    }
}