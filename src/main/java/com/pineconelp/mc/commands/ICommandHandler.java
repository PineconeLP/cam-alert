package com.pineconelp.mc.commands;

import org.bukkit.command.CommandSender;

public interface ICommandHandler {
    boolean handle(CommandSender sender, String[] args);
}