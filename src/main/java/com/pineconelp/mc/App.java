package com.pineconelp.mc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pineconelp.mc.listeners.CameraDestroyedListener;
import com.pineconelp.mc.listeners.CameraPlacedListener;
import com.pineconelp.mc.runnables.CameraCheckRunnable;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class App extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("CamAlert enabled.");

        Injector injector = Guice.createInjector(new CamAlertModule());

        registerCommand("cam", injector.getInstance(CommandExecutor.class));

        registerListener(injector.getInstance(CameraPlacedListener.class));
        registerListener(injector.getInstance(CameraDestroyedListener.class));

        registerRepeatingSyncTask(injector.getInstance(CameraCheckRunnable.class), 10L);
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

    private void registerRepeatingSyncTask(BukkitRunnable runnable, long interval) {
        runnable.runTaskTimer(this, 0L, interval);
    }
}