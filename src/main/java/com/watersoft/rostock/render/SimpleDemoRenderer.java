package com.watersoft.rostock.render;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GL2GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;

/**
 * Created by Wouter on 12/31/2014.
 */
public class SimpleDemoRenderer implements GLEventListener {
    private GLU glu;

    private float anglePyramid = 0;

    private float angleCube = 0;

    private float speedPyramid = 2.0f;

    private float speedCube = -1.5f;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        glu = new GLU();

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

        // Render the pyramid
        gl2.glLoadIdentity();
        gl2.glTranslatef(-1.6f, 0.0f, -6.0f);
        gl2.glRotatef(anglePyramid, -0.2f, 1.0f, 0.0f);

        gl2.glBegin(GL.GL_TRIANGLES);

        gl2.glColor3f(1.0f, 0.0f, 0.0f);
        gl2.glVertex3f(0.0f, 1.0f, 0.0f);
        gl2.glColor3f(0.0f, 1.0f, 0.0f);
        gl2.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl2.glColor3f(0.0f, 0.0f, 1.0f);
        gl2.glVertex3f(1.0f, -1.0f, 1.0f);

        gl2.glColor3f(1.0f, 0.0f, 0.0f);
        gl2.glVertex3f(0.0f, 1.0f, 0.0f);
        gl2.glColor3f(0.0f, 0.0f, 1.0f);
        gl2.glVertex3f(1.0f, -1.0f, 1.0f);
        gl2.glColor3f(0.0f, 1.0f, 0.0f);
        gl2.glVertex3f(1.0f, -1.0f, -1.0f);

        gl2.glColor3f(1.0f, 0.0f, 0.0f);
        gl2.glVertex3f(0.0f, 1.0f, 0.0f);
        gl2.glColor3f(0.0f, 1.0f, 0.0f);
        gl2.glVertex3f(1.0f, -1.0f, -1.0f);
        gl2.glColor3f(0.0f, 0.0f, 1.0f);
        gl2.glVertex3f(-1.0f, -1.0f, -1.0f);

        gl2.glColor3f(1.0f, 0.0f, 0.0f);
        gl2.glVertex3f(0.0f, 1.0f, 0.0f);
        gl2.glColor3f(0.0f, 0.0f, 1.0f);
        gl2.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl2.glColor3f(0.0f, 1.0f, 0.0f);
        gl2.glVertex3f(-1.0f, -1.0f, 1.0f);

        gl2.glEnd();

        // Render the cube
        gl2.glLoadIdentity();
        gl2.glTranslatef(1.6f, 0.0f, -7.0f);
        gl2.glRotatef(angleCube, 1.0f, 1.0f, 1.0f);

        gl2.glBegin(GL2GL3.GL_QUADS);

        gl2.glColor3f(0.0f, 1.0f, 0.0f);
        gl2.glVertex3f(1.0f, 1.0f, -1.0f);
        gl2.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl2.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl2.glVertex3f(1.0f, 1.0f, 1.0f);

        gl2.glColor3f(1.0f, 0.5f, 0.0f);
        gl2.glVertex3f(1.0f, -1.0f, 1.0f);
        gl2.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl2.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl2.glVertex3f(1.0f, -1.0f, -1.0f);

        gl2.glColor3f(1.0f, 0.0f, 0.0f);
        gl2.glVertex3f(1.0f, 1.0f, 1.0f);
        gl2.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl2.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl2.glVertex3f(1.0f, -1.0f, 1.0f);

        gl2.glColor3f(1.0f, 1.0f, 0.0f);
        gl2.glVertex3f(1.0f, -1.0f, -1.0f);
        gl2.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl2.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl2.glVertex3f(1.0f, 1.0f, -1.0f);

        gl2.glColor3f(0.0f, 0.0f, 1.0f);
        gl2.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl2.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl2.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl2.glVertex3f(-1.0f, -1.0f, 1.0f);

        gl2.glColor3f(1.0f, 0.0f, 1.0f);
        gl2.glVertex3f(1.0f, 1.0f, -1.0f);
        gl2.glVertex3f(1.0f, 1.0f, 1.0f);
        gl2.glVertex3f(1.0f, -1.0f, 1.0f);
        gl2.glVertex3f(1.0f, -1.0f, -1.0f);

        gl2.glEnd();

        anglePyramid += speedPyramid;
        angleCube += speedCube;
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();

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
}
