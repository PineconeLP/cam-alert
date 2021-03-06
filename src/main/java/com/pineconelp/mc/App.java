package com.pineconelp.mc;

import com.google.inject.Injector;
import com.pineconelp.mc.listeners.CameraDestroyedListener;
import com.pineconelp.mc.listeners.CameraPlacedListener;
import com.pineconelp.mc.listeners.PlayerCameraMovementListener;
import com.pineconelp.mc.runnables.EntityCameraMovementRunnable;
import com.pineconelp.mc.seeders.Seeder;
import com.pineconelp.mc.stores.CamAlertSettingsStore;
import com.pineconelp.mc.stores.CameraStore;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("CamAlert enabled.");

        Injector injector = new CamAlertModule(this).createInjector();

        try {
            // Seed data before loading.
            injector.getInstance(Seeder.class).seed();

            // Load stores before creating dependencies.
            injector.getInstance(CamAlertSettingsStore.class).loadSettings();
            injector.getInstance(CameraStore.class).loadCameras();
        } catch (Exception e) {
            e.printStackTrace();
        }

        registerCommand("cam", injector.getInstance(CommandExecutor.class));

        registerListener(injector.getInstance(CameraPlacedListener.class));
        registerListener(injector.getInstance(CameraDestroyedListener.class));
        registerListener(injector.getInstance(PlayerCameraMovementListener.class));

        injector.getInstance(EntityCameraMovementRunnable.class).initialize();
    }

    @Override
    public void onDisable() {
        getLogger().info("CamAlert disabled.");
    }

    private void registerCommand(String commandName, CommandExecutor executor) {
        getCommand(commandName).setExecutor(executor);
    }

    private void registerListener(Listener cameraPlacedEventListener) {
        getServer().getPluginManager().registerEvents(cameraPlacedEventListener, this);
    }
}