package com.watersoft.rostock.render.camera;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Wouter on 1/2/2015.
 */
public interface FpsCameraController extends KeyListener, MouseListener, MouseMotionListener {
    void update();
}
