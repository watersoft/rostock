package com.watersoft.rostock;

import com.jogamp.opengl.util.FPSAnimator;
import com.watersoft.rostock.gui.MainForm;
import com.watersoft.rostock.render.CameraControlDemo;
import com.watersoft.rostock.render.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import java.awt.Dimension;

/**
 *
 */
public class Launcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            // Renderer renderer = new SimpleDemo();
            // Renderer renderer = new SimpleShaderDemo();
            // Renderer renderer = new GeometryShaderDemo();
            Renderer renderer = new CameraControlDemo();

            GLProfile glProfile = GLProfile.getDefault();
            LOGGER.debug("Logging profile information.");
            LOGGER.debug("isGL2: {}", glProfile.isGL2());
            LOGGER.debug("isGL3: {}", glProfile.isGL3());
            LOGGER.debug("isGL4: {}", glProfile.isGL4());

            GLCapabilities glCapabilities = new GLCapabilities(glProfile);
            GLCanvas glCanvas = new GLCanvas(glCapabilities);
            glCanvas.setPreferredSize(new Dimension(1200, 675));

            if (renderer.getGlEventsListener() != null) {
                glCanvas.addGLEventListener(renderer.getGlEventsListener());
            }

            if (renderer.getKeyListener() != null) {
                glCanvas.addKeyListener(renderer.getKeyListener());
            }

            if (renderer.getMouseListener() != null) {
                glCanvas.addMouseListener(renderer.getMouseListener());
            }

            if (renderer.getMouseMotionListener() != null) {
                glCanvas.addMouseMotionListener(renderer.getMouseMotionListener());
            }

            MainForm mainForm = new MainForm(glCanvas);
            mainForm.setVisible(true);

            FPSAnimator animator = new FPSAnimator(glCanvas, 60, true);
            animator.start();
        });
    }
}
