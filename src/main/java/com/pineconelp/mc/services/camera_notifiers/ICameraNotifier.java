package com.pineconelp.mc.services.camera_notifiers;

import com.pineconelp.mc.models.Camera;

import org.bukkit.entity.Entity;

public interface ICameraNotifier {
    void notify(Camera camera, Entity entity);
}