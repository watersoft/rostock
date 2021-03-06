package com.watersoft.rostock.shader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Wouter on 1/2/2015.
 */
public interface ShaderUniformManager {
    /**
     * Creates a uniform integer atom that can be used to set values in the shader program.
     *
     * @param programName The shader program to which the uniform belongs.
     * @param uniformName The name of the uniform.
     * @param value       The initial value of the uniform.
     */
    void createUniform(String programName, String uniformName, int value);

    /**
     * Creates a uniform float atom that can be used to set values in the shader program.
     *
     * @param programName The shader program to which the uniform belongs.
     * @param uniformName The name of the uniform.
     * @param value       The initial value of the uniform.
     */
    void createUniform(String programName, String uniformName, float value);

    /**
     * Creates a uniform integer vector that can be used to set values in the shader program.
     *
     * @param programName The shader program to which the uniform belongs.
     * @param uniformName The name of the uniform.
     * @param components  The number of components of the vector.
     * @param data        The initial value of the uniform.
     */
    void createUniform(String programName, String uniformName, int components, IntBuffer data);

    /**
     * Creates a uniform float vector that can be used to set values in the shader program.
     *
     * @param programName The shader program to which the uniform belongs.
     * @param uniformName The name of the uniform.
     * @param components  The number of components of the vector.
     * @param data        The initial value of the uniform.
     */
    void createUniform(String programName, String uniformName, int components, FloatBuffer data);

    /**
     * Creates a uniform float matrix that can be used to set values in the shader program.
     *
     * @param programName The shader program to which the uniform belongs.
     * @param uniformName The name of the uniform.
     * @param rows        The number of rows of the matrix.
     * @param columns     The number of columns of the matrix.
     * @param data        The initial value of the uniform.
     */
    void createUniform(String programName, String uniformName, int rows, int columns, FloatBuffer data);

    /**
     * Sets the value of a uniform integer atom that was previously created.
     *
     * @param programName The shader program to which the uniform belongs.
     * @param uniformName The name of the uniform.
     * @param value       The new value of the uniform.
     */
    void setUniform(String programName, String uniformName, int value);

    /**
     * Sets the value of a uniform float atom that was previously created.
     *
     * @param programName The shader program to which the uniform belongs.
     * @param uniformName The name of the uniform.
     * @param value       The new value of the uniform.
     */
    void setUniform(String programName, String uniformName, float value);

    /**
     * Sets the value of a uniform integer vector that was previously created.
     *
     * @param programName The shader program to which the uniform belongs.
     * @param uniformName The name of the uniform.
     * @param components  The number of components of the vector.
     * @param data        The new value of the uniform.
     */
    void setUniform(String programName, String uniformName, int components, IntBuffer data);

    /**
     * Sets the value of a uniform float vector that was previously created.
     *
     * @param programName The shader program to which the uniform belongs.
     * @param uniformName The name of the uniform.
     * @param components  The number of components of the vector.
     * @param data        The new value of the uniform.
     */
    void setUniform(String programName, String uniformName, int components, FloatBuffer data);

    /**
     * Sets the value of a uniform float matrix that was previously created.
     *
     * @param programName The shader program to which the uniform belongs.
     * @param uniformName The name of the uniform.
     * @param rows        The number of rows of the matrix.
     * @param columns     The number of columns of the matrix.
     * @param data        The new value of the uniform.
     */
    void setUniform(String programName, String uniformName, int rows, int columns, FloatBuffer data);

    /**
     * Commits all uniforms belonging to the specified program.
     *
     * @param programName The name of the program whose uniforms to commit.
     */
    void commit(String programName);

    /**
     * Commits a uniform belonging to the specified program.
     *
     * @param programName The name of the program whose uniform to commit.
     * @param uniformName The name of the uniform to commit.
     */
    void commit(String programName, String uniformName);
}
