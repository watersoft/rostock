package com.watersoft.rostock;

import com.jogamp.opengl.util.FPSAnimator;
import com.watersoft.rostock.gui.MainForm;
import com.watersoft.rostock.render.TestRenderer;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import java.awt.Dimension;

/**
 *
 */
public class Launcher {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TestRenderer renderer = new TestRenderer();

                GLProfile glProfile = GLProfile.getDefault();
                GLCapabilities glCapabilities = new GLCapabilities(glProfile);
                GLCanvas glCanvas = new GLCanvas(glCapabilities);
                glCanvas.setPreferredSize(new Dimension(800, 450));
                glCanvas.addGLEventListener(renderer);

                FPSAnimator animator = new FPSAnimator(glCanvas, 60, true);

                MainForm mainForm = new MainForm(glCanvas);
                mainForm.setVisible(true);
                animator.start();
            }
        });
    }
}
