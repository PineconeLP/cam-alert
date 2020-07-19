package com.pineconelp.mc.runnables;

import org.bukkit.plugin.Plugin;

public interface IBukkitRunnableInitializerFactory<T extends IBukkitRunnableInitializer> {
    T createBukkitRunnable(Plugin plugin);
}