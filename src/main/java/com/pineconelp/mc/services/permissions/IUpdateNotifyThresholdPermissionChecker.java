package com.pineconelp.mc.services.permissions;

import org.bukkit.entity.Player;

public interface IUpdateNotifyThresholdPermissionChecker {
    boolean canUpdateNotifyThreshold(Player player, double notifyThreshold);
}