package com.watersoft.rostock.shader;

import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import com.jogamp.opengl.util.glsl.ShaderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.media.opengl.GL2ES2;
import java.util.HashMap;

/**
 * Created by Wouter on 12/31/2014.
 */
public class ShaderManager implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShaderManager.class);
    private final GL2ES2 gl2ES2;
    private final ShaderState shaderState;
    private final HashMap<String, ShaderProgram> programs;

    public ShaderManager(GL2ES2 gl2ES2) {
        this.gl2ES2 = gl2ES2;
        shaderState = new ShaderState();
        shaderState.setVerbose(true);
        programs = new HashMap<>();
    }

    public void unload(String name) {
        LOGGER.debug("Unloading shader program '{}'", name);
        programs.get(name).release(gl2ES2, true);
        programs.remove(name);
    }

    public void load(String name) {
        LOGGER.debug("Loading vertex shader '{}'", name);
        ShaderCode vs = ShaderCode.create(gl2ES2, GL2ES2.GL_VERTEX_SHADER, this.getClass(), "/glsl", "/glsl/bin", name, true);
        LOGGER.debug("Loading fragment shader '{}", name);
        ShaderCode fs = ShaderCode.create(gl2ES2, GL2ES2.GL_FRAGMENT_SHADER, this.getClass(), "/glsl", "/glsl/bin", name, true);
        ShaderProgram sp = new ShaderProgram();
        sp.add(gl2ES2, vs, System.err);
        sp.add(gl2ES2, fs, System.err);
        programs.put(name, sp);
    }

    public void use(String name) {
        shaderState.attachShaderProgram(gl2ES2, programs.get(name), true);
    }

    public void use(boolean on) {
        shaderState.useProgram(gl2ES2, on);
    }

    public ShaderState getShaderState() {
        return shaderState;
    }

    @Override
    public void close() throws Exception {
        shaderState.destroy(gl2ES2);
        for (String name : programs.keySet()) {
            unload(name);
        }
    }
}
