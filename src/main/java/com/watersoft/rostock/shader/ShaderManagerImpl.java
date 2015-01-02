package com.watersoft.rostock.shader;

import com.watersoft.rostock.common.FileUtils;
import com.watersoft.rostock.common.TypeUtils;
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

    private final Map<String, List<String>> attributes = new HashMap<>();

    private final Map<String, Map<String, Integer>> attributeTypes = new HashMap<>();

    private final Map<String, Map<String, Integer>> attributeSizes = new HashMap<>();

    private final GL2 gl2;

    public ShaderManagerImpl(GL2 gl2) {
        this.gl2 = gl2;
    }

    @Override
    public void load(String programName) {
        List<Integer> shaders = new ArrayList<>();

        String vpPath = String.format("/glsl/%s.vp", programName);
        String gpPath = String.format("/glsl/%s.gp", programName);
        String fpPath = String.format("/glsl/%s.fp", programName);

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
            LOGGER.error(String.format("Failed to load vertex shader '%s'", programName), e);
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
            LOGGER.error(String.format("Failed to load geometry shader '%s'", programName), e);
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
            LOGGER.error(String.format("Failed to load fragment shader '%s'", programName), e);
        }

        if (shaders.size() <= 0) {
            LOGGER.warn(String.format("Shader '%s' has no code", programName));
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
            programs.put(programName, sp);
            uniforms.put(programName, new HashMap<>());
            attributes.put(programName, new ArrayList<>());
            attributeTypes.put(programName, new HashMap<>());
            attributeSizes.put(programName, new HashMap<>());
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
    public void unload(String programName) {
        int sp = programs.get(programName);
        gl2.glDeleteProgram(sp);
        programs.remove(programName);
        uniforms.remove(programName);
        attributes.put(programName, new ArrayList<>());
        attributeTypes.remove(programName);
        attributeSizes.remove(programName);
    }

    @Override
    public void bind(String programName) {
        int sp = programs.get(programName);
        gl2.glUseProgram(sp);
    }

    @Override
    public void unbind() {
        gl2.glUseProgram(0);
    }

    @Override
    public void createUniform(String programName, String uniformName, int value) {
        int sp = programs.get(programName);
        GLUniformData uniformData = new GLUniformData(uniformName, value);
        int location = gl2.glGetUniformLocation(sp, uniformName);
        uniformData.setLocation(location);
        uniforms.get(programName).put(uniformName, uniformData);
    }

    @Override
    public void createUniform(String programName, String uniformName, float value) {
        int sp = programs.get(programName);
        GLUniformData uniformData = new GLUniformData(uniformName, value);
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
    public void setUniform(String programName, String uniformName, int value) {
        uniforms.get(programName).get(uniformName).setData(value);
    }

    @Override
    public void setUniform(String programName, String uniformName, float value) {
        uniforms.get(programName).get(uniformName).setData(value);
    }

    @Override
    public void setUniform(String programName, String uniformName, int components, IntBuffer data) {
        uniforms.get(programName).get(uniformName).setData(data);
    }

    @Override
    public void setUniform(String programName, String uniformName, int components, FloatBuffer data) {
        uniforms.get(programName).get(uniformName).setData(data);
    }

    @Override
    public void setUniform(String programName, String uniformName, int rows, int columns, FloatBuffer data) {
        uniforms.get(programName).get(uniformName).setData(data);
    }

    @Override
    public void commit(String programName) {
        uniforms.get(programName).values().forEach(gl2::glUniform);
    }

    @Override
    public void commit(String programName, String uniformName) {
        GLUniformData uniformData = uniforms.get(programName).get(uniformName);
        gl2.glUniform(uniformData);
    }

    @Override
    public void close() throws Exception {
        programs.values().forEach(gl2::glDeleteProgram);
        programs.clear();
        uniforms.clear();
        attributes.clear();
        attributeTypes.clear();
        attributeSizes.clear();
    }

    @Override
    public void addAttribute(String programName, String attributeName, int type, int size) {
        attributes.get(programName).add(attributeName);
        attributeTypes.get(programName).put(attributeName, type);
        attributeSizes.get(programName).put(attributeName, size);
    }

    @Override
    public void applyAttributes(String programName, VertexBufferObject vbo) {
        vbo.bind();
        int sum = 0;
        for (String attribute : attributes.get(programName)) {
            int type = attributeTypes.get(programName).get(attribute);
            int size = attributeSizes.get(programName).get(attribute);
            sum += TypeUtils.sizeOf(type) * size;
        }
        long index = 0;
        for (String attribute : attributes.get(programName)) {
            int type = attributeTypes.get(programName).get(attribute);
            int size = attributeSizes.get(programName).get(attribute);
            int location = gl2.glGetAttribLocation(programs.get(programName), attribute);
            gl2.glEnableVertexAttribArray(location);
            gl2.glVertexAttribPointer(location, size, type, false, sum, index);
            index += TypeUtils.sizeOf(type) * size;
        }
        vbo.unbind();
    }
}
