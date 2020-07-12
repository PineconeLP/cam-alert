package com.pineconelp.mc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pineconelp.mc.listeners.CameraDestroyedListener;
import com.pineconelp.mc.listeners.CameraMovementDetectedListener;
import com.pineconelp.mc.listeners.CameraPlacedListener;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("CamAlert enabled.");

        Injector injector = Guice.createInjector(new CamAlertModule());
        getCommand("cam").setExecutor(injector.getInstance(CommandExecutor.class));
        registerListener(injector.getInstance(CameraPlacedListener.class));
        registerListener(injector.getInstance(CameraMovementDetectedListener.class));
        registerListener(injector.getInstance(CameraDestroyedListener.class));
    }

    @Override
    public void onDisable() {
        getLogger().info("CamAlert disabled.");
    }

    private void registerListener(Listener cameraPlacedEventListener) {
        getServer().getPluginManager().registerEvents(cameraPlacedEventListener, this);
    }
}