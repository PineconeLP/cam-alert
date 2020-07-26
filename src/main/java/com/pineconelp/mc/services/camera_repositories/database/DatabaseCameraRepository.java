package com.pineconelp.mc.services.camera_repositories.database;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.pineconelp.mc.models.Camera;
import com.pineconelp.mc.models.CameraDetails;
import com.pineconelp.mc.models.CameraDirection;
import com.pineconelp.mc.models.CameraLocation;
import com.pineconelp.mc.services.camera_repositories.ICameraRepository;
import com.pineconelp.mc.utilities.DatabaseSessionFactory;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DatabaseCameraRepository implements ICameraRepository {

    private DatabaseSessionFactory sessionFactory;

    @Inject
    public DatabaseCameraRepository(DatabaseSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Camera> getAll() throws SQLException {
        try(Session session = sessionFactory.createSession()) {
            return session.createQuery("from CameraEntity", CameraEntity.class).getResultStream()
                .map(this::cameraEntityToCamera)
                .collect(Collectors.toList());
        }
    }

    private Camera cameraEntityToCamera(CameraEntity entity) {
        CameraLocation location = new CameraLocation(entity.getWorldId(), entity.getX(), entity.getY(), entity.getZ());
        CameraDirection direction = entity.getDirection();
        CameraDetails details = new CameraDetails(entity.getRange(), entity.getNotificationThresholdSeconds(), entity.getOwnerPlayerId());

        return new Camera(entity.getId(), location, direction, details);
    }

    @Override
    public Camera create(Camera camera) throws SQLException {
        try(Session session = sessionFactory.createSession()) {
            CameraEntity cameraEntity = cameraToCameraEntity(camera);

            Transaction transaction = session.beginTransaction();
            session.persist(cameraEntity);
            camera.setId(cameraEntity.getId());
            transaction.commit();

            return camera;
        }
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
        try(Session session = sessionFactory.createSession()) {
            CameraEntity cameraEntity = new CameraEntity();
            cameraEntity.setId(id);

            Transaction transaction = session.beginTransaction();
            session.remove(cameraEntity);
            transaction.commit();
        }
    }
}