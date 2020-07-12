package com.pineconelp.mc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pineconelp.mc.events.CameraPlacedEventListener;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("CamAlert enabled.");

        Injector injector = Guice.createInjector(new CamAlertModule());

		CommandExecutor commandExecutor = injector.getInstance(CommandExecutor.class);

        getCommand("cam").setExecutor(commandExecutor);

        CameraPlacedEventListener cameraPlacedEventListener = injector.getInstance(CameraPlacedEventListener.class);
        registerEventListener(cameraPlacedEventListener);
    }

    @Override
    public void onDisable() {
        getLogger().info("CamAlert disabled.");
    }

    private void registerEventListener(Listener cameraPlacedEventListener) {
        getServer().getPluginManager().registerEvents(cameraPlacedEventListener, this);
    }
}