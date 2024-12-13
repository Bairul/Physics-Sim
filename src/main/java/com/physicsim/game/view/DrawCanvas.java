package com.physicsim.game.view;

import com.vaadin.flow.component.html.Div;

import java.awt.Color;

public class DrawCanvas extends Div {
    /** The width of the window screen. */
    private final int myWidth;
    /** The height of the window screen. */
    private final int myHeight;
    private final String canvasId = "canvas";
    private String currentColor = "#000000"; // Default color: black

    public DrawCanvas(int width, int height) {
        myWidth = width;
        myHeight = height;

        // Create the canvas element
        String canvasHtml = String.format(
                "<canvas id='%s' width='" + width + "' height='" + height + "' style='border:1px solid black;'></canvas>",
                canvasId
        );
        getElement().setProperty("innerHTML", canvasHtml);
    }

    private void executeJs(String js) {
        getElement().executeJs(js);
    }

    // Clear a rectangle area
    public void clearRect(int x, int y, int width, int height) {
        String js = String.format(
                "var canvas = document.getElementById('%s');" +
                        "var ctx = canvas.getContext('2d');" +
                        "ctx.clearRect(%d, %d, %d, %d);",
                canvasId, x, y, width, height
        );
        executeJs(js);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        String js = String.format(
                "var canvas = document.getElementById('%s');" +
                        "var ctx = canvas.getContext('2d');" +
                        "ctx.strokeStyle = '%s';" +
                        "ctx.beginPath();" +
                        "ctx.moveTo(%d, %d);" +
                        "ctx.lineTo(%d, %d);" +
                        "ctx.stroke();",
                canvasId, currentColor, x1, y1, x2, y2
        );
        executeJs(js);
    }

    // Draw an oval (ellipse outline)
    public void drawOval(int x, int y, int width, int height) {
        String js = String.format(
                "var canvas = document.getElementById('%s');" +
                        "var ctx = canvas.getContext('2d');" +
                        "ctx.strokeStyle = '%s';" +
                        "ctx.beginPath();" +
                        "ctx.ellipse(%d, %d, %d, %d, 0, 0, Math.PI * 2);" +
                        "ctx.stroke();",
                canvasId, currentColor, x + width / 2, y + height / 2, width / 2, height / 2
        );
        executeJs(js);
    }

    // Fill an oval (ellipse filled with color)
    public void fillOval(int x, int y, int width, int height) {
        String js = String.format(
                "var canvas = document.getElementById('%s');" +
                        "var ctx = canvas.getContext('2d');" +
                        "ctx.fillStyle = '%s';" +
                        "ctx.beginPath();" +
                        "ctx.ellipse(%d, %d, %d, %d, 0, 0, Math.PI * 2);" +
                        "ctx.fill();",
                canvasId, currentColor, x + width / 2, y + height / 2, width / 2, height / 2
        );
        executeJs(js);
    }

    // Draw a polygon with specified x and y coordinates
    public void drawPolygon(int[] xPoints, int[] yPoints) {
        if (xPoints.length != yPoints.length || xPoints.length < 3) {
            throw new IllegalArgumentException("Polygon must have at least 3 points.");
        }
        StringBuilder path = new StringBuilder();
        path.append(String.format(
                "var canvas = document.getElementById('%s');" +
                        "var ctx = canvas.getContext('2d');" +
                        "ctx.strokeStyle = '%s';" +
                        "ctx.beginPath();" +
                        "ctx.moveTo(%d, %d);",
                canvasId, currentColor, xPoints[0], yPoints[0]
        ));
        for (int i = 1; i < xPoints.length; i++) {
            path.append(String.format("ctx.lineTo(%d, %d);", xPoints[i], yPoints[i]));
        }
        path.append("ctx.closePath(); ctx.stroke();");
        executeJs(path.toString());
    }

    public void setColor(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        currentColor = String.format("#%02x%02x%02x", red, green, blue);
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

