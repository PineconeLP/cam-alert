package com.pineconelp.mc.services.permissions;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.pineconelp.mc.constants.Permission;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class PermissionChecker implements ICreatePermissionChecker, IUpdateRangePermissionChecker {

    @Override
    public boolean canCreate(Player player) {
        return player.hasPermission(Permission.CAM_ALERT_CREATE);
    }

    @Override
    public boolean canUpdateRange(Player player, double range) {
        boolean canUpdateAll = player.hasPermission(Permission.CAM_ALERT_UPDATE_RANGE_ALL);

        Integer updateMin = getPermissionValues(player, Permission.CAM_ALERT_UPDATE_MIN_RANGE_X_PREFIX)
            .map(p -> {
                try {
                    return Integer.parseInt(p);
                } catch (Exception e) {
                    return Integer.MIN_VALUE;
                }
            })
            .max(Integer::compare)
            .orElse(Integer.MIN_VALUE);

        Integer updateMax = getPermissionValues(player, Permission.CAM_ALERT_UPDATE_MAX_RANGE_X_PREFIX)
            .map(p -> {
                try {
                    return Integer.parseInt(p);
                } catch (Exception e) {
                    return Integer.MAX_VALUE;
                }
            })
            .min(Integer::compare)
            .orElse(Integer.MAX_VALUE);

        List<Integer> updateExacts = getPermissionValues(player, Permission.CAM_ALERT_UPDATE_RANGE_X_PREFIX)
            .map(p -> {
                try {
                    return Integer.parseInt(p);
                } catch (Exception e) {
                    return Integer.MAX_VALUE;
                }
            })
            .collect(Collectors.toList());

        return false;
    }

    private Stream<String> getPermissionValues(Player player, String prefix) {
        return player.getEffectivePermissions()
            .stream()
            .map(PermissionAttachmentInfo::getPermission)
            .map(String::toLowerCase)
            .filter(p -> p.startsWith(prefix))
            .map(p -> p.substring(prefix.length()));
    }
    
}