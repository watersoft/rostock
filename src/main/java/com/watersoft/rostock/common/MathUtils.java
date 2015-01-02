package com.watersoft.rostock.common;

/**
 * Created by Wouter on 1/2/2015.
 */
public class MathUtils {
    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
}
