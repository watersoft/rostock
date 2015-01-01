package com.watersoft.rostock.shader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Wouter on 1/1/2015.
 */
public interface ShaderManager extends AutoCloseable {
    void load(String name);

    void unload(String name);

    void bind(String name);

    void unbind();

    void createUniform(String programName, String uniformName, int val);

    void createUniform(String programName, String uniformName, float val);

    void createUniform(String programName, String uniformName, int components, IntBuffer data);

    void createUniform(String programName, String uniformName, int components, FloatBuffer data);

    void createUniform(String programName, String uniformName, int rows, int columns, FloatBuffer data);

    void commit(String programName);

    void commit(String programName, String uniformName);
}
