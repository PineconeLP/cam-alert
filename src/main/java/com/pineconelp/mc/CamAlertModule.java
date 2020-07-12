package com.pineconelp.mc;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.pineconelp.mc.commands.ICommandHandler;
import com.pineconelp.mc.commands.CamAlertCommand;
import com.pineconelp.mc.commands.GiveCameraItemCommandHandler;
import com.pineconelp.mc.commands.WelcomeCommandHandler;
import com.pineconelp.mc.items.cameras.CameraItemFactory;
import com.pineconelp.mc.items.cameras.ICameraItemFactory;

import org.bukkit.command.CommandExecutor;

public class CamAlertModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(ICameraItemFactory.class).to(CameraItemFactory.class).in(Singleton.class);

        bind(ICommandHandler.class).annotatedWith(Names.named("NoArgsHandler")).to(WelcomeCommandHandler.class).in(Singleton.class);
        bind(ICommandHandler.class).annotatedWith(Names.named("GiveCameraItemCommandHandler")).to(GiveCameraItemCommandHandler.class).in(Singleton.class);

        bind(CommandExecutor.class).to(CamAlertCommand.class).in(Singleton.class);
    }
}