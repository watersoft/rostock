package com.watersoft.rostock.shader;

/**
 * Created by Wouter on 1/2/2015.
 */
public interface ShaderVertexAttributeManager {
    /**
     * Adds a shader attribute to the specified program.
     *
     * @param programName   The shader program to which the attribute belongs.
     * @param attributeName The name of the attribute.
     * @param type          The data type of each component of the attribute.
     * @param size          The number of components of the attribute.
     */
    void addAttribute(String programName, String attributeName, int type, int size);

    /**
     * Defines the attributes of the specified program and enables them for the given vertex buffer object.
     *
     * @param programName The shader program whose attributes to bind.
     * @param vbo         The vertex buffer object to enable the attributes for.
     */
    void applyAttributes(String programName, VertexBufferObject vbo);
}
