package com.pineconelp.mc.services.camera_repositories.sqlite;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraDetails;
import com.pineconelp.mc.models.CameraDirection;
import com.pineconelp.mc.models.CameraLocation;
import com.pineconelp.mc.services.camera_repositories.ICameraRepository;

public class DatabaseCameraRepository implements ICameraRepository {

    private DatabaseConnectionFactory connectionFactory;

    private Dao<CameraEntity, Long> cameraDao;

    @Inject
    public DatabaseCameraRepository(DatabaseConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<Camera> getAll() throws SQLException {
        return getDao().queryForAll().stream().map(this::cameraEntityToCamera).collect(Collectors.toList());
    }

    private Camera cameraEntityToCamera(CameraEntity entity) {
        CameraLocation location = new CameraLocation(entity.getWorldId(), entity.getX(), entity.getY(), entity.getZ());
        CameraDirection direction = entity.getDirection();
        CameraDetails details = new CameraDetails(entity.getRange(), entity.getNotificationThresholdSeconds(), entity.getOwnerPlayerId());

        return new Camera(entity.getId(), location, direction, details);
    }

    @Override
    public Camera create(Camera camera) throws SQLException {
        CameraEntity cameraEntity = cameraToCameraEntity(camera);
        
        getDao().create(cameraEntity);
        camera.setId(cameraEntity.getId());

        return camera;
    }

    private CameraEntity cameraToCameraEntity(Camera camera) {
        CameraEntity cameraEntity = new CameraEntity();

        cameraEntity.setId(camera.getId());
        cameraEntity.setName(camera.getName());
        cameraEntity.setWorldId(camera.getLocation().getWorldId());
        cameraEntity.setX(camera.getLocation().getX());
        cameraEntity.setY(camera.getLocation().getY());
        cameraEntity.setZ(camera.getLocation().getZ());
        cameraEntity.setDirection(camera.getCameraDirection());
        cameraEntity.setRange(camera.getRange());
        cameraEntity.setNotificationThresholdSeconds(camera.getNotificationThresholdSeconds());
        cameraEntity.setOwnerPlayerId(camera.getOwnerPlayerId());
        cameraEntity.setDateCreated(new Date());

        return cameraEntity;
    }

    @Override
    public void delete(long id) throws SQLException {
        getDao().deleteById(id);
    }

    private Dao<CameraEntity, Long> getDao() throws SQLException {
        if(cameraDao == null) {
            ConnectionSource connection = connectionFactory.createConnection();
            cameraDao = DaoManager.createDao(connection, CameraEntity.class);
        }

        return cameraDao;
    }
}