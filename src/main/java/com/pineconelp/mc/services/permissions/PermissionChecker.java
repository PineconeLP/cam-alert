package com.pineconelp.mc.services.permissions;

import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.pineconelp.mc.constants.Permission;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class PermissionChecker implements ICreatePermissionChecker, IUpdateRangePermissionChecker, IUpdateNotifyThresholdPermissionChecker {

    @Override
    public boolean canCreate(Player player) {
        return hasAdminPermission(player) || player.hasPermission(Permission.CAM_ALERT_CREATE);
    }

    @Override
    public boolean canUpdateRange(Player player, double range) {
        return hasUpdatePermission(player, () -> hasPermissionValue(player, Permission.CAM_ALERT_UPDATE_RANGE, (int)range));
    }

    @Override
    public boolean canUpdateNotifyThreshold(Player player, double threshold) {
        return hasUpdatePermission(player, () -> hasPermissionValue(player, Permission.CAM_ALERT_UPDATE_NOTIFY_THRESHOLD, (int)threshold));
    }

    private boolean hasUpdatePermission(Player player, BooleanSupplier hasSpecificPermission) {
        if(hasAdminPermission(player) || hasUpdateAdminPermission(player)) {
            return true;
        }

        return hasSpecificPermission.getAsBoolean();
    }

    private boolean hasAdminPermission(Player player) {
        return player.hasPermission(Permission.CAM_ALERT_ADMIN);
    }

    private boolean hasUpdateAdminPermission(Player player) {
        return player.hasPermission(Permission.CAM_ALERT_UPDATE_ADMIN);
    }

    private boolean hasPermissionValue(Player player, String prefix, int value) {
        boolean hasValue = false;

        if(player.hasPermission(prefix)) {
            boolean overrideRestrictions = player.hasPermission(prefix + Permission.ALL);

            if(overrideRestrictions) {
                hasValue = true;
            } else {
                List<Integer> exactAllowedValues = getExactPermissionValues(player, prefix);
                Optional<Integer> minAllowedOptional = getMinimumPermissionValue(player, prefix);
                Optional<Integer> maxAllowedOptional = getMaximumPermissionValue(player, prefix);

                boolean hasExactAllowedValuesRestriction = exactAllowedValues.size() > 0;
                boolean hasMinAllowedValueRestriction = minAllowedOptional.isPresent();
                boolean hasMaxAllowedValueRestriction = maxAllowedOptional.isPresent();
                boolean hasRestriction = hasExactAllowedValuesRestriction || hasMinAllowedValueRestriction || hasMaxAllowedValueRestriction;

                if(!hasRestriction) {
                    hasValue = true;
                } else {
                    boolean meetsExactAllowedValueRestriction = hasExactAllowedValuesRestriction && exactAllowedValues.contains(value);
                    if(meetsExactAllowedValueRestriction) {
                        hasValue = true;
                    } else {
                        boolean hasMinMaxRestriction = hasMinAllowedValueRestriction || hasMaxAllowedValueRestriction;

                        if(hasMinMaxRestriction) {
                            boolean meetsMinAllowedValueRestriction = value >= minAllowedOptional.orElse(Integer.MIN_VALUE);
                            boolean meetsMaxAllowedValueRestriction = value <= maxAllowedOptional.orElse(Integer.MAX_VALUE);
                            boolean meetsMinMaxRestriction = meetsMinAllowedValueRestriction && meetsMaxAllowedValueRestriction;
                            
                            hasValue = meetsMinMaxRestriction;
                        }
                    }
                }
            }
        }

        return hasValue;
    }

    private Optional<Integer> getMinimumPermissionValue(Player player, String prefix) {
        String minPrefix = prefix + Permission.MIN;

        return getPermissionValues(player, minPrefix)
            .map(p -> {
                try {
                    return Integer.parseInt(p);
                } catch (Exception e) {
                    return Integer.MIN_VALUE;
                }
            })
            .max(Integer::compare);
    }

    private Optional<Integer> getMaximumPermissionValue(Player player, String prefix) {
        String maxPrefix = prefix + Permission.MAX;

        return getPermissionValues(player, maxPrefix)
            .map(p -> {
                try {
                    return Integer.parseInt(p);
                } catch (Exception e) {
                    return Integer.MAX_VALUE;
                }
            })
            .min(Integer::compare);
    }

    private List<Integer> getExactPermissionValues(Player player, String prefix) {
        String exactPrefix = prefix + Permission.SEPARATOR;

        return getPermissionValues(player, exactPrefix)
            .map(p -> {
                try {
                    return Integer.parseInt(p);
                } catch (Exception e) {
                    return Integer.MAX_VALUE;
                }
            })
            .collect(Collectors.toList());
    }

    /**
     * Get permissions for a player that match a permission prefix.
     * @param player The player with permissions.
     * @param prefix The prefix of the permission.
     * @return The stream of permission strings for the player that match the prefix.
     */
    private Stream<String> getPermissionValues(Player player, String prefix) {
        return player.getEffectivePermissions()
            .stream()
            .map(PermissionAttachmentInfo::getPermission)
            .map(String::toLowerCase)
            .filter(p -> p.startsWith(prefix))
            .map(p -> p.substring(prefix.length()));
    }
    
}