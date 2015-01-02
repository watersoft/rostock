package com.watersoft.rostock.render.camera;

import com.watersoft.rostock.common.MathUtils;

import javax.media.opengl.glu.GLU;

/**
 * Created by Wouter on 1/2/2015.
 */
public class CameraImpl implements Camera {
    private float pitch = 0.0f;

    private float yaw = 0.0f;

    private float x = 0.0f;

    private float y = 0.0f;

    private float z = 0.0f;

    @Override
    public void transform() {
        float sinPitch = (float) Math.sin(pitch);
        float cosPitch = (float) Math.cos(pitch);
        float sinYaw = (float) Math.sin(yaw);
        float cosYaw = (float) Math.cos(yaw);
        float cx = -cosPitch * sinYaw;
        float cy = sinPitch;
        float cz = cosPitch * cosYaw;
        GLU glu = new GLU();
        glu.gluLookAt(x, y, z, x + cx, y + cy, z + cz, 0.0f, 1.0f, 0.0f);
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public void addPitch(float pitch) {
        this.pitch += pitch;
        this.pitch = MathUtils.clamp(this.pitch, (float) -Math.PI / 2.0f + 0.01f, (float) Math.PI / 2.0f - 0.01f);
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    @Override
    public void addYaw(float yaw) {
        this.yaw += yaw;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void addX(float x) {
        this.x += x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void addY(float y) {
        this.y += y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public void addZ(float z) {
        this.z += z;
    }

    @Override
    public void forward(float d) {
        float sinPitch = (float) Math.sin(pitch);
        float cosPitch = (float) Math.cos(pitch);
        float sinYaw = (float) Math.sin(yaw);
        float cosYaw = (float) Math.cos(yaw);
        float cx = -cosPitch * sinYaw;
        float cy = sinPitch;
        float cz = cosPitch * cosYaw;
        x += d * cx;
        y += d * cy;
        z += d * cz;
    }

    @Override
    public void left(float d) {
        x += d * (float) Math.cos(yaw);
        z += d * (float) Math.sin(yaw);
    }

    @Override
    public void up(float d) {
        y += d;
    }
}
