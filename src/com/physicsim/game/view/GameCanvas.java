package com.physicsim.game.view;

import com.physicsim.game.utility.Vector2;

import javax.swing.JFrame;
import  java.awt.Canvas;
import java.awt.Dimension;

/**
 * Class for the game's window screen.
 */
public final class GameCanvas {
    /** The width of the window screen. */
    private final int myWidth;
    /** The height of the window screen. */
    private final int myHeight;
    /** The canvas for graphics. */
    private final Canvas myCanvas;
    /** The window screen. */
    private final JFrame myFrame;

    /**
     * Constructs the game window screen.
     *
     * @param theRatio the aspect ratio
     * @param theScale the scale of the screen
     * @param theTitle the name of the screen
     */
    public GameCanvas(final Vector2 theRatio, final int theScale, final String theTitle) {
        myWidth = theRatio.intX() * theScale;
        myHeight = theRatio.intY() * theScale;

        myCanvas = new Canvas();
        myFrame = new JFrame(theTitle);

        init();
    }

    /**
     * Helper method to set up the screen.
     */
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

    /**
     * Gets the JFrame.
     * @return the JFrame
     */
    public JFrame getFrame() {
        return myFrame;
    }

    /**
     * Gets the Canvas.
     * @return the Canvas
     */
    public Canvas getCanvas() {
        return myCanvas;
    }

    /**
     * Gets the width of the screen.
     * @return the width
     */
    public int getWidth() {
        return myWidth;
    }

    /**
     * Gets the height of the screen.
     * @return the height
     */
    public int getHeight() {
        return myHeight;
    }
}
