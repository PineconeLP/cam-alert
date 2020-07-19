package com.pineconelp.mc.services.camera_repositories.sqlite;

import com.google.inject.Inject;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.services.camera_repositories.ICameraRepository;

public class DatabaseCameraRepository implements ICameraRepository {

    private DatabaseConnectionFactory connectionFactory;

    @Inject
    public DatabaseCameraRepository(DatabaseConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Camera create(Camera camera) {
        return null;
    }
}