package com.pineconelp.mc.services.permissions;

import org.bukkit.entity.Player;

public interface IUpdateRangePermissionChecker {
    boolean canUpdateRange(Player player, double range);
}