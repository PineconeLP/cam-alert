package com.pineconelp.mc;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("CamAlert enabled.");

        Injector injector = Guice.createInjector(new CamAlertModule());

		CommandExecutor commandExecutor = injector.getInstance(CommandExecutor.class);

        getCommand("cam").setExecutor(commandExecutor);
    }

    @Override
    public void onDisable() {
        getLogger().info("CamAlert disabled.");
    }
}