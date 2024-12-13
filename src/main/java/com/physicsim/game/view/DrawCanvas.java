package com.physicsim.game.view;

import com.vaadin.flow.component.html.Div;

import java.awt.Color;

import com.vaadin.flow.component.dependency.JsModule;

/**
 * Custom canvas class that bridges java.awt.graphics to use javascript canvas element.
 *
 * @author Bairu Li
 */
@JsModule("@vaadin/vaadin-lumo-styles/color.js")
public class DrawCanvas extends Div {
    /** The width of the window screen. */
    private final int myWidth;
    /** The height of the window screen. */
    private final int myHeight;
    /** Canvas id for html. */
    private final String canvasId = "canvas";
    /** Current color to draw. */
    private String currentColor = "#000000"; // Default color: black

    /**
     * Constructs the canvas class and creates html canvas element.
     *
     * @param theWidth  the width of the canvas
     * @param theHeight the height of the canvas
     */
    public DrawCanvas(final int theWidth, final int theHeight) {
        myWidth = theWidth;
        myHeight = theHeight;

        String canvasHtml = String.format(
                "<canvas id='%s' width='%d' height='%d' style='border:1px solid black;'></canvas>",
                canvasId, theWidth, theHeight
        );
        getElement().setProperty("innerHTML", canvasHtml);
    }

    /**
     * Helper method to execute the javascript string.
     * @param js the javascript
     */
    private void executeJs(final String js) {
        getElement().executeJs(js);
    }

    /**
     * Helper method to optimize code.
     * @return string for canvas context
     */
    private String getCanvasContext() {
        return String.format(
                "var canvas = document.getElementById('%s'); var ctx = canvas.getContext('2d');", canvasId
        );
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
        executeJs(String.format(
                "%s ctx.clearRect(%d, %d, %d, %d);",
                getCanvasContext(), x, y, width, height
        ));
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
        executeJs(String.format(
                "%s ctx.strokeStyle = '%s'; ctx.beginPath(); ctx.moveTo(%d, %d); ctx.lineTo(%d, %d); ctx.stroke();",
                getCanvasContext(), currentColor, x1, y1, x2, y2
        ));
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
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        int radiusX = width / 2;
        int radiusY = height / 2;
        executeJs(String.format(
                "%s ctx.strokeStyle = '%s'; ctx.beginPath(); ctx.ellipse(%d, %d, %d, %d, 0, 0, Math.PI * 2); ctx.stroke();",
                getCanvasContext(), currentColor, centerX, centerY, radiusX, radiusY
        ));
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
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        int radiusX = width / 2;
        int radiusY = height / 2;
        executeJs(String.format(
                "%s ctx.fillStyle = '%s'; ctx.beginPath(); ctx.ellipse(%d, %d, %d, %d, 0, 0, Math.PI * 2); ctx.fill();",
                getCanvasContext(), currentColor, centerX, centerY, radiusX, radiusY
        ));
    }

    /**
     * Sets the current color.
     *
     * @param color the color as a awt.Color
     */
    public void setColor(final Color color) {
        currentColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Gets the width of the screen.
     * @return the width
     */
    public int getCanvasWidth() {
        return myWidth;
    }

    /**
     * Gets the height of the screen.
     * @return the height
     */
    public int getCanvasHeight() {
        return myHeight;
    }
}