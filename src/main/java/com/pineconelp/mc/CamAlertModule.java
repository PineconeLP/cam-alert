package com.pineconelp.mc;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.pineconelp.mc.commands.ICommandHandler;
import com.pineconelp.mc.commands.CamAlertCommand;
import com.pineconelp.mc.commands.WelcomeCommandHandler;

import org.bukkit.command.CommandExecutor;

public class CamAlertModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(ICommandHandler.class).annotatedWith(Names.named("NoArgsHandler")).to(WelcomeCommandHandler.class).in(Singleton.class);

        bind(CommandExecutor.class).to(CamAlertCommand.class).in(Singleton.class);
    }
}