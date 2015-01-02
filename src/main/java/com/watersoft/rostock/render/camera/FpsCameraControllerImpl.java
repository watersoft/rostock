package com.watersoft.rostock.render.camera;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;

/**
 * Created by Wouter on 1/2/2015.
 */
public class FpsCameraControllerImpl implements FpsCameraController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FpsCameraControllerImpl.class);

    private int oldX = -1;

    private int oldY = -1;

    private boolean leftMouse = false;

    private boolean middleMouse = false;

    private boolean rightMouse = false;

    private boolean[] keys = new boolean[256];

    private Camera camera;

    public FpsCameraControllerImpl(Camera camera) {
        Arrays.fill(keys, false);
        this.camera = camera;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyChar()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyChar()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                leftMouse = true;
                break;
            case MouseEvent.BUTTON2:
                middleMouse = true;
                break;
            case MouseEvent.BUTTON3:
                rightMouse = true;
                break;
            default:
                LOGGER.warn(String.format("Unsupported mouse button: %d", e.getButton()));
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                leftMouse = false;
                break;
            case MouseEvent.BUTTON2:
                middleMouse = false;
                break;
            case MouseEvent.BUTTON3:
                rightMouse = false;
                break;
            default:
                LOGGER.warn(String.format("Unsupported mouse button: %d", e.getButton()));
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (oldX == -1 || oldY == -1) {
            oldX = e.getX();
            oldY = e.getY();
        }
        int deltaX = e.getX() - oldX;
        int deltaY = oldY - e.getY();
        oldX = e.getX();
        oldY = e.getY();
        if (rightMouse) {
            camera.addPitch(0.005f * deltaY);
            camera.addYaw(0.005f * deltaX);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (oldX == -1 || oldY == -1) {
            oldX = e.getX();
            oldY = e.getY();
        }
        int deltaX = e.getX() - oldX;
        int deltaY = oldY - e.getY();
        oldX = e.getX();
        oldY = e.getY();
        if (rightMouse) {
            camera.addPitch(0.005f * deltaY);
            camera.addYaw(0.005f * deltaX);
        }
    }

    @Override
    public void update() {
        if (keys['w']) {
            camera.forward(0.05f);
        }
        if (keys['s']) {
            camera.forward(-0.05f);
        }
        if (keys['a']) {
            camera.left(0.05f);
        }
        if (keys['d']) {
            camera.left(-0.05f);
        }
        if (keys['q']) {
            camera.up(0.05f);
        }
        if (keys['e']) {
            camera.up(-0.05f);
        }
    }
}
