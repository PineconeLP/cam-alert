package com.pineconelp.mc.services.camera_repositories;

import java.sql.SQLException;
import java.util.List;

import com.pineconelp.mc.models.Camera;

public interface ICameraRepository {
    List<Camera> getAll() throws SQLException;

    Camera create(Camera camera) throws SQLException;

    void delete(long id) throws SQLException;
}