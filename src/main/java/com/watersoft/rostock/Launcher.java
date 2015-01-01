package com.watersoft.rostock;

import com.jogamp.opengl.util.FPSAnimator;
import com.watersoft.rostock.gui.MainForm;
import com.watersoft.rostock.render.GeometryShaderRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import java.awt.Dimension;

/**
 *
 */
public class Launcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // GLEventListener renderer = new SimpleRenderer();
                // GLEventListener renderer = new ShaderRenderer();
                GLEventListener renderer = new GeometryShaderRenderer();

                GLProfile glProfile = GLProfile.getDefault();
                LOGGER.debug("Logging profile information.");
                LOGGER.debug("isGL2: {}", glProfile.isGL2());
                LOGGER.debug("isGL3: {}", glProfile.isGL3());
                LOGGER.debug("isGL4: {}", glProfile.isGL4());

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
