package com.watersoft.rostock.shader;

/**
 * Created by Wouter on 1/1/2015.
 */
public interface ShaderManager extends AutoCloseable, ShaderUniformManager, ShaderVertexAttributeManager {
    /**
     * Load a shader program from source code in the /glsl classpath.
     * The vertex shader is read from /glsl/name.vp;
     * the geometry shader is read from /glsl/name.gp;
     * the fragment shader is read from /glsl/name.fp.
     *
     * @param programName The name of the shader program to load.
     */
    void load(String programName);

    /**
     * Unloads and deletes the specified shader program.
     *
     * @param programName The name of the shader program to unload.
     */
    void unload(String programName);

    /**
     * Binds a shader program to use for rendering.
     *
     * @param programName The name of the shader to bind for rendering.
     */
    void bind(String programName);

    /**
     * Unbinds all shader programs from rendering.
     */
    void unbind();
}
