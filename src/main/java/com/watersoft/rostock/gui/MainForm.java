package com.watersoft.rostock.gui;

import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

/**
 * Created by Wouter on 12/31/2014.
 */
public class MainForm extends JFrame {
    public MainForm(GLCanvas glCanvas) {
        JSplitPane splitPane = new JSplitPane();
        splitPane.setEnabled(false);
        splitPane.setLeftComponent(null);
        splitPane.setRightComponent(glCanvas);

        setContentPane(splitPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
}
