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
    private final CanvasRenderingContext2D context;
    private final HTMLCanvasElement myCanvas;

    public DrawCanvas(final Vector2 theRatio, final int theScale) {
        myWidth = theRatio.intX() * theScale;
        myHeight = theRatio.intY() * theScale;

        HTMLDocument document = Window.current().getDocument();
        myCanvas = (HTMLCanvasElement) document.createElement("canvas");
        myCanvas.setWidth(myWidth);
        myCanvas.setHeight(myHeight);
        document.getBody().appendChild(myCanvas);
        context = (CanvasRenderingContext2D) myCanvas.getContext("2d");
    }

    public void setColor(String color) {
        context.setFillStyle(color);
        context.setStrokeStyle(color);
    }

    public void clearRect(int x, int y, int width, int height) {
        context.clearRect(x, y, width, height);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        context.beginPath();
        context.moveTo(x1, y1);
        context.lineTo(x2, y2);
        context.stroke();
    }

    public void drawRect(int x, int y, int width, int height) {
        context.strokeRect(x, y, width, height);
    }

    public void drawOval(int x, int y, int width, int height) {
        context.save();
        context.beginPath();
        context.translate(x + width / 2.0, y + height / 2.0);
        context.scale(width / 2.0, height / 2.0);
        context.arc(0, 0, 1, 0, 2 * Math.PI, false);
        context.restore();
        context.stroke();
    }

    public void fillOval(int x, int y, int width, int height) {
        context.save();
        context.beginPath();
        context.translate(x + width / 2.0, y + height / 2.0);
        context.scale(width / 2.0, height / 2.0);
        context.arc(0, 0, 1, 0, 2 * Math.PI, false);
        context.restore();
        context.fill();
    }

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
