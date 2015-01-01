package com.watersoft.rostock.render;

import com.jogamp.opengl.util.PMVMatrix;
import com.jogamp.opengl.util.gl2.GLUT;
import com.watersoft.rostock.shader.ShaderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLUniformData;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;

/**
 * Created by Wouter on 12/31/2014.
 */
public class ShaderRenderer implements GLEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShaderRenderer.class);
    private GLU glu;
    private GLUT glut;

    private PMVMatrix pmvMatrix;
    private GLUniformData pmvMatrixUniform;
    private ShaderManager shaderManager;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        glu = new GLU();
        glut = new GLUT();

        pmvMatrix = new PMVMatrix();
        pmvMatrix.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        pmvMatrix.glLoadIdentity();
        pmvMatrix.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        pmvMatrix.glLoadIdentity();
        pmvMatrixUniform = new GLUniformData("mgl_PMVMatrix", 4, 4, pmvMatrix.glGetPMvMatrixf());

        shaderManager = new ShaderManager(gl2.getGL2ES2());
        shaderManager.load("test");
        shaderManager.use("test");

        shaderManager.getShaderState().ownUniform(pmvMatrixUniform);
        shaderManager.getShaderState().uniform(gl2.getGL2ES2(), pmvMatrixUniform);

        shaderManager.use(false);

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

        shaderManager.use(true);

        pmvMatrix.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        pmvMatrix.glLoadIdentity();
        pmvMatrix.glTranslatef(0.0f, 0.0f, -10.0f);
        shaderManager.getShaderState().uniform(gl2.getGL2ES2(), pmvMatrixUniform);
        glut.glutSolidTeapot(1.0);

        shaderManager.use(false);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();

        if (height == 0) {
            height = 1;
        }

        gl2.glViewport(0, 0, width, height);

        // float aspect = (float) width / height;
        float aspect = 16.0f / 9.0f;
        pmvMatrix.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        pmvMatrix.glLoadIdentity();
        pmvMatrix.gluPerspective(45.0f, aspect, 0.1f, 100.0f);
        pmvMatrix.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        pmvMatrix.glLoadIdentity();

        shaderManager.use(true);
        shaderManager.getShaderState().uniform(gl2.getGL2ES2(), pmvMatrixUniform);
        shaderManager.use(false);
    }
}
