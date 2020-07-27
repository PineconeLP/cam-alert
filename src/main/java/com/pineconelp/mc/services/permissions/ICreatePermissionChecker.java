package com.pineconelp.mc.services.permissions;

import org.bukkit.entity.Player;

public interface ICreatePermissionChecker {
    boolean canCreate(Player player);
}