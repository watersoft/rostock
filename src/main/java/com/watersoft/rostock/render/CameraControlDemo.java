package com.watersoft.rostock.render;

import com.watersoft.rostock.render.camera.Camera;
import com.watersoft.rostock.render.camera.CameraImpl;
import com.watersoft.rostock.render.camera.FpsCameraController;
import com.watersoft.rostock.render.camera.FpsCameraControllerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Wouter on 1/2/2015.
 */
public class CameraControlDemo implements Renderer, GLEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(CameraControlDemo.class);

    private Camera camera = new CameraImpl();

    private FpsCameraController cameraController;

    public CameraControlDemo() {
        cameraController = new FpsCameraControllerImpl(camera);
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();

        gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl2.glClearDepth(1.0f);
        gl2.glEnable(GL.GL_DEPTH_TEST);
        gl2.glDepthFunc(GL.GL_LEQUAL);
        gl2.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl2.glShadeModel(GLLightingFunc.GL_SMOOTH);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();

        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl2.glLoadIdentity();
        cameraController.update();
        camera.transform();

        gl2.glBegin(GL.GL_TRIANGLES);
        gl2.glColor3f(1.0f, 0.0f, 0.0f);
        gl2.glVertex3f(1.0f, 0.0f, 0.0f);
        gl2.glColor3f(0.0f, 1.0f, 0.0f);
        gl2.glVertex3f(0.0f, 1.0f, 0.0f);
        gl2.glColor3f(0.0f, 0.0f, 1.0f);
        gl2.glVertex3f(0.0f, 0.0f, 1.0f);
        gl2.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        GLU glu = new GLU();

        if (height == 0) {
            height = 1;
        }

        float aspect = (float) width / height;
        gl2.glViewport(0, 0, width, height);
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl2.glLoadIdentity();
        glu.gluPerspective(45.0f, aspect, 0.1f, 100.0f);

        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl2.glLoadIdentity();
    }

    @Override
    public GLEventListener getGlEventsListener() {
        return this;
    }

    @Override
    public MouseListener getMouseListener() {
        return cameraController;
    }

    @Override
    public MouseMotionListener getMouseMotionListener() {
        return cameraController;
    }

    @Override
    public KeyListener getKeyListener() {
        return cameraController;
    }
}
