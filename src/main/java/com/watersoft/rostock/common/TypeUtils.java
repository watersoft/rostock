package com.watersoft.rostock.common;

import javax.media.opengl.GL2;

/**
 * Created by Wouter on 1/2/2015.
 */
public class TypeUtils {
    public static int sizeOf(int type) {
        switch (type) {
            case GL2.GL_BYTE:
                return 1;
            case GL2.GL_SHORT:
                return 2;
            case GL2.GL_INT:
                return 4;
            case GL2.GL_FLOAT:
                return 4;
            case GL2.GL_DOUBLE:
                return 8;
            default:
                throw new IllegalStateException("Unsupported type.");
        }
    }
}
