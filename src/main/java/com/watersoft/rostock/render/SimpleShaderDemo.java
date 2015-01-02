package com.watersoft.rostock.render;

import com.jogamp.opengl.util.PMVMatrix;
import com.jogamp.opengl.util.gl2.GLUT;
import com.watersoft.rostock.shader.ShaderManager;
import com.watersoft.rostock.shader.ShaderManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Wouter on 12/31/2014.
 */
public class SimpleShaderDemo implements Renderer, GLEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleShaderDemo.class);

    private static final String SHADER_NAME = "simple";

    private GLUT glut;

    private PMVMatrix pmvMatrix;

    private ShaderManager shaderManager;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        glut = new GLUT();

        pmvMatrix = new PMVMatrix();
        pmvMatrix.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        pmvMatrix.glLoadIdentity();
        pmvMatrix.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        pmvMatrix.glLoadIdentity();

        shaderManager = new ShaderManagerImpl(gl2);
        shaderManager.load(SHADER_NAME);
        shaderManager.createUniform(SHADER_NAME, "projectionMatrix", 4, 4, pmvMatrix.glGetPMatrixf());
        shaderManager.createUniform(SHADER_NAME, "modelViewMatrix", 4, 4, pmvMatrix.glGetMvMatrixf());

        shaderManager.bind(SHADER_NAME);
        shaderManager.commit(SHADER_NAME);
        shaderManager.unbind();

        gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl2.glClearDepth(1.0f);
        gl2.glEnable(GL.GL_DEPTH_TEST);
        gl2.glDepthFunc(GL.GL_LEQUAL);
        gl2.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        try {
            shaderManager.close();
        } catch (Exception e) {
            LOGGER.warn("Exception during disposal of shader loader", e);
        }
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        pmvMatrix.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        pmvMatrix.glLoadIdentity();
        pmvMatrix.glTranslatef(0.0f, 0.0f, -10.0f);

        shaderManager.bind(SHADER_NAME);
        shaderManager.commit(SHADER_NAME);
        glut.glutSolidTeapot(1.0);
        shaderManager.unbind();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();

        if (height == 0) {
            height = 1;
        }

        gl2.glViewport(0, 0, width, height);

        float aspect = 16.0f / 9.0f;
        pmvMatrix.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        pmvMatrix.glLoadIdentity();
        pmvMatrix.gluPerspective(45.0f, aspect, 0.1f, 100.0f);
        pmvMatrix.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        pmvMatrix.glLoadIdentity();

        shaderManager.bind(SHADER_NAME);
        shaderManager.commit(SHADER_NAME);
        shaderManager.unbind();
    }

    @Override
    public GLEventListener getGlEventsListener() {
        return this;
    }

    @Override
    public MouseListener getMouseListener() {
        return null;
    }

    @Override
    public MouseMotionListener getMouseMotionListener() {
        return null;
    }

    @Override
    public KeyListener getKeyListener() {
        return null;
    }
}
