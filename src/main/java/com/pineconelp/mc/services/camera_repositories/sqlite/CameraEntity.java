package com.pineconelp.mc.services.camera_repositories.sqlite;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pineconelp.mc.models.CameraDirection;

@Entity
@Table(name = "cameras")
public class CameraEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private UUID worldId;

    @Column
    private int x;

    @Column
    private int y;

    @Column
    private int z;

    @Column
    private CameraDirection direction;

    @Column
    private double range;

    @Column
    private double notificationThresholdSeconds;

    @Column
    private UUID ownerPlayerId;

    @Column
    private Date dateCreated;

    public CameraEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getWorldId() {
        return worldId;
    }

    public void setWorldId(UUID worldId) {
        this.worldId = worldId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public CameraDirection getDirection() {
        return direction;
    }

    public void setDirection(CameraDirection direction) {
        this.direction = direction;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getNotificationThresholdSeconds() {
        return notificationThresholdSeconds;
    }

    public void setNotificationThresholdSeconds(double notificationThresholdSeconds) {
        this.notificationThresholdSeconds = notificationThresholdSeconds;
    }

    public UUID getOwnerPlayerId() {
        return ownerPlayerId;
    }

    public void setOwnerPlayerId(UUID ownerPlayerId) {
        this.ownerPlayerId = ownerPlayerId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}