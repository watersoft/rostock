package com.watersoft.rostock.render;

import javax.media.opengl.GLEventListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Wouter on 1/2/2015.
 */
public interface Renderer {
    GLEventListener getGlEventsListener();

    MouseListener getMouseListener();

    MouseMotionListener getMouseMotionListener();

    KeyListener getKeyListener();
}
