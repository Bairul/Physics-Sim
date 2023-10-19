package com.survival.game.view;

import com.survival.game.utility.Vector2;

import javax.swing.JFrame;
import java.awt.*;

public final class GameCanvas {
    private final int myWidth;
    private final int myHeight;
    private final Canvas myCanvas;
    private final JFrame myFrame;
    public GameCanvas(final Vector2 theRatio, final int theScale, final String theTitle) {
        myWidth = theRatio.intX() * theScale;
        myHeight = theRatio.intY() * theScale;

        myCanvas = new Canvas();
        myFrame = new JFrame(theTitle);

        init();
    }

    private void init() {
        Dimension d = new Dimension(myWidth, myHeight);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(d);
        myFrame.setLocationRelativeTo(null);
        myFrame.setResizable(false);

        myCanvas.setPreferredSize(d);
        myCanvas.setMaximumSize(d);
        myCanvas.setMinimumSize(d);

        myFrame.add(myCanvas);
        myFrame.pack();
        myFrame.setVisible(true);
    }

    // ============ getters ============
    public JFrame getFrame() {
        return myFrame;
    }

    public Canvas getCanvas() {
        return myCanvas;
    }

    public int getWidth() {
        return myWidth;
    }

    public int getHeight() {
        return myHeight;
    }
}
