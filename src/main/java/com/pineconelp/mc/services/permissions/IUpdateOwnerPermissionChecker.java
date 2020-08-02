package com.pineconelp.mc.services.permissions;

import org.bukkit.entity.Player;

public interface IUpdateOwnerPermissionChecker {
    boolean canUpdateOwner(Player player, String playerName);
}