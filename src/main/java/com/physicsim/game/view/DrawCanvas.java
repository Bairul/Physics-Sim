package com.physicsim.game.view;

import com.physicsim.game.utility.Vector2;
import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLDocument;

public class DrawCanvas {
    /** The width of the window screen. */
    private final int myWidth;
    /** The height of the window screen. */
    private final int myHeight;
    /** The html canvas element. */
    private final HTMLCanvasElement myCanvas;
    /** The reference to the html canvas context for drawing. */
    private final CanvasRenderingContext2D context;

    /**
     * Constructs the canvas class and creates html canvas element.
     *
     * @param theRatio the aspect ratio
     * @param theScale the scale of the screen
     */
    public DrawCanvas(final Vector2 theRatio, final int theScale) {
        myWidth = theRatio.intX() * theScale;
        myHeight = theRatio.intY() * theScale;

        // create canvas
        final HTMLDocument document = Window.current().getDocument();
        myCanvas = (HTMLCanvasElement) document.createElement("canvas");
        myCanvas.setWidth(myWidth);
        myCanvas.setHeight(myHeight);
        document.getBody().appendChild(myCanvas);
        context = (CanvasRenderingContext2D) myCanvas.getContext("2d");
    }

    /**
     * Sets the current color.
     *
     * @param theColor the color as a awt.Color
     */
    public void setColor(final String theColor) {
        context.setFillStyle(theColor);
        context.setStrokeStyle(theColor);
    }

    /**
     * Clear a rectangle area. Good for erasing after each frame.
     *
     * @param x      the x value
     * @param y      the y value
     * @param width  the width of the rect
     * @param height the height of the rect
     */
    public void clearRect(int x, int y, int width, int height) {
        context.clearRect(x, y, width, height);
    }

    /**
     * Draws a line from x1 y1 to x2 y2.
     *
     * @param x1 the x value of point 1
     * @param y1 the y value of point 1
     * @param x2 the x value of point 2
     * @param y2 the y value of point 2
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        context.beginPath();
        context.moveTo(x1, y1);
        context.lineTo(x2, y2);
        context.stroke();
    }

    /**
     * Draw an oval (ellipse outline).
     *
     * @param x      the x value
     * @param y      the y value
     * @param width  the horizontal diameter of oval
     * @param height the vertical diameter of oval
     */
    public void drawOval(int x, int y, int width, int height) {
        context.save();
        context.beginPath();
        context.translate(x + width / 2.0, y + height / 2.0);
        context.scale(width / 2.0, height / 2.0);
        context.arc(0, 0, 1, 0, 2 * Math.PI, false);
        context.restore();
        context.stroke();
    }

    /**
     * Fill an oval (ellipse filled with color)/
     *
     * @param x      the x value
     * @param y      the y value
     * @param width  the horizontal diameter of oval
     * @param height the vertical diameter of oval
     */
    public void fillOval(int x, int y, int width, int height) {
        context.save();
        context.beginPath();
        context.translate(x + width / 2.0, y + height / 2.0);
        context.scale(width / 2.0, height / 2.0);
        context.arc(0, 0, 1, 0, 2 * Math.PI, false);
        context.restore();
        context.fill();
    }

    /**
     * Gets the html canvas element.
     * @return the canvas
     */
    public HTMLCanvasElement getCanvas() {
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
