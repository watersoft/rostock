package com.watersoft.rostock.render;

import com.jogamp.opengl.util.gl2.GLUT;
import com.watersoft.rostock.shader.ShaderManager;
import com.watersoft.rostock.shader.ShaderManagerImpl;
import com.watersoft.rostock.shader.VertexBufferObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * Created by Wouter on 1/1/2015.
 */
public class GeometryShaderDemoRenderer implements GLEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeometryShaderDemoRenderer.class);

    private static final String SHADER_NAME = "geometry";

    private GLU glu;

    private GLUT glut;

    private ShaderManager shaderManager;

    private VertexBufferObject vbo;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        glu = new GLU();
        glut = new GLUT();

        float points[] = {
                0.45f, 0.45f, 1.0f, 0.0f, 0.0f, 4.0f,
                0.45f, -0.45f, 0.0f, 1.0f, 0.0f, 8.0f,
                -0.45f, 0.45f, 0.0f, 0.0f, 1.0f, 16.0f,
                -0.45f, -0.45f, 1.0f, 1.0f, 0.0f, 32.0f
        };
        vbo = new VertexBufferObject(gl);
        vbo.bind();
        vbo.write(points);
        vbo.unbind();

        shaderManager = new ShaderManagerImpl(gl2);
        shaderManager.load(SHADER_NAME);
        shaderManager.addAttribute(SHADER_NAME, "position", GL.GL_FLOAT, 2);
        shaderManager.addAttribute(SHADER_NAME, "color", GL.GL_FLOAT, 3);
        shaderManager.addAttribute(SHADER_NAME, "sides", GL.GL_FLOAT, 1);
        shaderManager.applyAttributes(SHADER_NAME, vbo);

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

        try {
            vbo.close();
        } catch (Exception e) {
            LOGGER.warn("Failed to close VBO.");
        }
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        shaderManager.bind(SHADER_NAME);
        vbo.bind();
        gl.glDrawArrays(GL.GL_POINTS, 0, 4);
        vbo.unbind();
        shaderManager.unbind();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();

        if (height == 0) {
            height = 1;
        }

        gl2.glViewport(0, 0, width, height);
    }
}
