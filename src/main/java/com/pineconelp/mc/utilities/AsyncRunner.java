package com.pineconelp.mc.utilities;

import com.google.inject.Inject;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class AsyncRunner {
    private Plugin plugin;

    @Inject
    public AsyncRunner(Plugin plugin) {
        this.plugin = plugin;
    }

    public BukkitTask runTaskAsync(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }
}