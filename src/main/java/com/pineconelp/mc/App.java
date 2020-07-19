package com.pineconelp.mc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.pineconelp.mc.listeners.CameraDestroyedListener;
import com.pineconelp.mc.listeners.CameraPlacedListener;
import com.pineconelp.mc.listeners.PlayerCameraMovementListener;
import com.pineconelp.mc.runnables.EntityCameraMovementRunnable;
import com.pineconelp.mc.runnables.IBukkitRunnableInitializer;
import com.pineconelp.mc.runnables.IBukkitRunnableInitializerFactory;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("CamAlert enabled.");

        Injector injector = Guice.createInjector(new CamAlertModule());

        registerCommand("cam", injector.getInstance(CommandExecutor.class));

        registerListener(injector.getInstance(CameraPlacedListener.class));
        registerListener(injector.getInstance(CameraDestroyedListener.class));
        registerListener(injector.getInstance(PlayerCameraMovementListener.class));

        initializeRunnable(injector.getInstance(new Key<IBukkitRunnableInitializerFactory<EntityCameraMovementRunnable>>() {}));
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

    private <T extends IBukkitRunnableInitializer> void initializeRunnable(IBukkitRunnableInitializerFactory<T> bukkitRunnableFactory) {
        IBukkitRunnableInitializer bukkitRunnableInitializer = bukkitRunnableFactory.createBukkitRunnable(this);
        bukkitRunnableInitializer.initialize();
    }
}