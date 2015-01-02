package com.watersoft.rostock.render.camera;

/**
 * Created by Wouter on 1/2/2015.
 */
public interface Camera {
    void transform();

    float getPitch();

    void setPitch(float pitch);

    void addPitch(float pitch);

    float getYaw();

    void setYaw(float yaw);

    void addYaw(float yaw);

    float getX();

    void setX(float x);

    void addX(float x);

    float getY();

    void setY(float y);

    void addY(float y);

    float getZ();

    void setZ(float z);

    void addZ(float z);

    void forward(float d);

    void left(float d);

    void up(float d);
}
