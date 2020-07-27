package com.pineconelp.mc.services.permissions;

import com.pineconelp.mc.constants.Permission;

import org.bukkit.entity.Player;

public class PermissionChecker implements ICreatePermissionChecker {

    @Override
    public boolean canCreate(Player player) {
        return player.hasPermission(Permission.CAM_ALERT_CREATE);
    }
    
}