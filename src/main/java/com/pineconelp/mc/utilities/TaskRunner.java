package com.pineconelp.mc.utilities;

import com.google.inject.Inject;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class TaskRunner {
    private Plugin plugin;

    @Inject
    public TaskRunner(Plugin plugin) {
        this.plugin = plugin;
    }

    public BukkitTask runTask(Runnable runnable) {
        return Bukkit.getScheduler().runTask(plugin, runnable);
    }

    public BukkitTask runTaskAsync(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }
}