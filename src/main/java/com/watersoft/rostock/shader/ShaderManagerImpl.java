package com.watersoft.rostock.shader;

import com.watersoft.rostock.common.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES2;
import javax.media.opengl.GL3;
import javax.media.opengl.GLUniformData;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wouter on 1/1/2015.
 */
public class ShaderManagerImpl implements ShaderManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShaderManagerImpl.class);

    private final Map<String, Integer> programs = new HashMap<>();

    private final Map<String, Map<String, GLUniformData>> uniforms = new HashMap<>();

    private final GL2 gl2;

    public ShaderManagerImpl(GL2 gl2) {
        this.gl2 = gl2;
    }

    @Override
    public void load(String name) {
        List<Integer> shaders = new ArrayList<>();

        String vpPath = String.format("/glsl/%s.vp", name);
        String gpPath = String.format("/glsl/%s.gp", name);
        String fpPath = String.format("/glsl/%s.fp", name);

        try {
            if (FileUtils.getResourceExists(vpPath)) {
                String vp = FileUtils.getResourceAsString(vpPath);
                int vs = gl2.glCreateShader(GL2ES2.GL_VERTEX_SHADER);
                gl2.glShaderSource(vs, 1, new String[]{vp}, null, 0);
                gl2.glCompileShader(vs);

                IntBuffer intBuffer = IntBuffer.allocate(1);
                gl2.glGetShaderiv(vs, GL2ES2.GL_COMPILE_STATUS, intBuffer);
                if (intBuffer.get(0) == GL.GL_TRUE) {
                    shaders.add(vs);
                } else {
                    gl2.glGetShaderiv(vs, GL2ES2.GL_INFO_LOG_LENGTH, intBuffer);
                    int size = intBuffer.get(0);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                    gl2.glGetShaderInfoLog(vs, size, intBuffer, byteBuffer);
                    String log = new String(byteBuffer.array());
                    LOGGER.error(String.format("Unable to compile vertex shader: %s", log));
                    gl2.glDeleteShader(vs);
                }
            }
        } catch (IOException e) {
            LOGGER.error(String.format("Failed to load vertex shader '%s'", name), e);
        }

        try {
            if (FileUtils.getResourceExists(gpPath)) {
                String gp = FileUtils.getResourceAsString(gpPath);
                int gs = gl2.glCreateShader(GL3.GL_GEOMETRY_SHADER);
                gl2.glShaderSource(gs, 1, new String[]{gp}, null, 0);
                gl2.glCompileShader(gs);

                IntBuffer intBuffer = IntBuffer.allocate(1);
                gl2.glGetShaderiv(gs, GL2ES2.GL_COMPILE_STATUS, intBuffer);
                if (intBuffer.get(0) == GL.GL_TRUE) {
                    shaders.add(gs);
                } else {
                    gl2.glGetShaderiv(gs, GL2ES2.GL_INFO_LOG_LENGTH, intBuffer);
                    int size = intBuffer.get(0);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                    gl2.glGetShaderInfoLog(gs, size, intBuffer, byteBuffer);
                    String log = new String(byteBuffer.array());
                    LOGGER.error(String.format("Unable to compile geometry shader: %s", log));
                    gl2.glDeleteShader(gs);
                }
            }
        } catch (IOException e) {
            LOGGER.error(String.format("Failed to load geometry shader '%s'", name), e);
        }

        try {
            if (FileUtils.getResourceExists(fpPath)) {
                String fp = FileUtils.getResourceAsString(fpPath);
                int fs = gl2.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER);
                gl2.glShaderSource(fs, 1, new String[]{fp}, null, 0);
                gl2.glCompileShader(fs);

                IntBuffer intBuffer = IntBuffer.allocate(1);
                gl2.glGetShaderiv(fs, GL2ES2.GL_COMPILE_STATUS, intBuffer);
                if (intBuffer.get(0) == GL.GL_TRUE) {
                    shaders.add(fs);
                } else {
                    gl2.glGetShaderiv(fs, GL2ES2.GL_INFO_LOG_LENGTH, intBuffer);
                    int size = intBuffer.get(0);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                    gl2.glGetShaderInfoLog(fs, size, intBuffer, byteBuffer);
                    String log = new String(byteBuffer.array());
                    LOGGER.error(String.format("Unable to compile fragment shader: %s", log));
                    gl2.glDeleteShader(fs);
                }
            }
        } catch (IOException e) {
            LOGGER.error(String.format("Failed to load fragment shader '%s'", name), e);
        }

        if (shaders.size() <= 0) {
            LOGGER.warn(String.format("Shader '%s' has no code", name));
        }

        int sp = gl2.glCreateProgram();
        for (int shader : shaders) {
            gl2.glAttachShader(sp, shader);
        }
        gl2.glLinkProgram(sp);
        for (int shader : shaders) {
            gl2.glDetachShader(sp, shader);
            gl2.glDeleteShader(shader);
        }
        gl2.glValidateProgram(sp);

        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl2.glGetProgramiv(sp, GL2ES2.GL_LINK_STATUS, intBuffer);
        if (intBuffer.get(0) == GL.GL_TRUE) {
            programs.put(name, sp);
            uniforms.put(name, new HashMap<String, GLUniformData>());
        } else {
            gl2.glGetProgramiv(sp, GL2ES2.GL_INFO_LOG_LENGTH, intBuffer);
            int size = intBuffer.get(0);
            ByteBuffer byteBuffer = ByteBuffer.allocate(size);
            gl2.glGetProgramInfoLog(sp, size, intBuffer, byteBuffer);
            String log = new String(byteBuffer.array());
            LOGGER.error(String.format("Unable to link shader program: %s", log));
            gl2.glDeleteProgram(sp);
        }
    }

    @Override
    public void unload(String name) {
        int sp = programs.get(name);
        gl2.glDeleteProgram(sp);
        programs.remove(name);
        uniforms.remove(name);
    }

    @Override
    public void bind(String name) {
        int sp = programs.get(name);
        gl2.glUseProgram(sp);
    }

    @Override
    public void unbind() {
        gl2.glUseProgram(0);
    }

    @Override
    public void createUniform(String programName, String uniformName, int val) {
        int sp = programs.get(programName);
        GLUniformData uniformData = new GLUniformData(uniformName, val);
        int location = gl2.glGetUniformLocation(sp, uniformName);
        uniformData.setLocation(location);
        uniforms.get(programName).put(uniformName, uniformData);
    }

    @Override
    public void createUniform(String programName, String uniformName, float val) {
        int sp = programs.get(programName);
        GLUniformData uniformData = new GLUniformData(uniformName, val);
        int location = gl2.glGetUniformLocation(sp, uniformName);
        uniformData.setLocation(location);
        uniforms.get(programName).put(uniformName, uniformData);
    }

    @Override
    public void createUniform(String programName, String uniformName, int components, IntBuffer data) {
        int sp = programs.get(programName);
        GLUniformData uniformData = new GLUniformData(uniformName, components, data);
        int location = gl2.glGetUniformLocation(sp, uniformName);
        uniformData.setLocation(location);
        uniforms.get(programName).put(uniformName, uniformData);
    }

    @Override
    public void createUniform(String programName, String uniformName, int components, FloatBuffer data) {
        int sp = programs.get(programName);
        GLUniformData uniformData = new GLUniformData(uniformName, components, data);
        int location = gl2.glGetUniformLocation(sp, uniformName);
        uniformData.setLocation(location);
        uniforms.get(programName).put(uniformName, uniformData);
    }

    @Override
    public void createUniform(String programName, String uniformName, int rows, int columns, FloatBuffer data) {
        int sp = programs.get(programName);
        GLUniformData uniformData = new GLUniformData(uniformName, rows, columns, data);
        int location = gl2.glGetUniformLocation(sp, uniformName);
        uniformData.setLocation(location);
        uniforms.get(programName).put(uniformName, uniformData);
    }

    @Override
    public void commit(String programName) {
        for (GLUniformData uniformData : uniforms.get(programName).values()) {
            gl2.glUniform(uniformData);
        }
    }

    @Override
    public void commit(String programName, String uniformName) {
        GLUniformData uniformData = uniforms.get(programName).get(uniformName);
        gl2.glUniform(uniformData);
    }

    @Override
    public void close() throws Exception {
        for (int sp : programs.values()) {
            gl2.glDeleteProgram(sp);
        }
        programs.clear();
        uniforms.clear();
    }
}
