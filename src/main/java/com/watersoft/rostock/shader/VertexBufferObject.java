package com.watersoft.rostock.shader;

import com.watersoft.rostock.common.TypeUtils;

import javax.media.opengl.GL;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Wouter on 1/2/2015.
 */
public class VertexBufferObject implements AutoCloseable {
    private final GL gl;

    private final IntBuffer vbo;

    public VertexBufferObject(GL gl) {
        this.gl = gl;

        vbo = IntBuffer.allocate(1);
        gl.glGenBuffers(1, vbo);
    }

    public void bind() {
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo.get(0));
    }

    public void unbind() {
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
    }

    public void write(float[] data) {
        gl.glBufferData(GL.GL_ARRAY_BUFFER, data.length * TypeUtils.sizeOf(GL.GL_FLOAT), FloatBuffer.wrap(data), GL.GL_STATIC_DRAW);
    }

    @Override
    public void close() throws Exception {
        gl.glDeleteBuffers(1, vbo);
    }
}
