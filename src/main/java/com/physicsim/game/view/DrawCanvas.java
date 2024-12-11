package com.physicsim.game.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.Element;

public class DrawCanvas extends Div {
    private final Element svg;

    public DrawCanvas(int width, int height) {
        // Create the SVG container
        svg = new Element("svg");
        svg.setAttribute("width", width + "px");
        svg.setAttribute("height", height + "px");
        svg.setAttribute("style", "border: 1px solid black; background-color: white;");

        // Add the SVG to the container
        getElement().appendChild(svg);
    }

    public void drawLine(int x1, int y1, int x2, int y2, String color, int strokeWidth) {
        Element line = new Element("line");
        line.setAttribute("x1", String.valueOf(x1));
        line.setAttribute("y1", String.valueOf(y1));
        line.setAttribute("x2", String.valueOf(x2));
        line.setAttribute("y2", String.valueOf(y2));
        line.setAttribute("stroke", color);
        line.setAttribute("stroke-width", String.valueOf(strokeWidth));

        svg.appendChild(line);
    }

    public void drawRectangle(int x, int y, int width, int height, String color, String fill) {
        Element rect = new Element("rect");
        rect.setAttribute("x", String.valueOf(x));
        rect.setAttribute("y", String.valueOf(y));
        rect.setAttribute("width", String.valueOf(width));
        rect.setAttribute("height", String.valueOf(height));
        rect.setAttribute("stroke", color);
        rect.setAttribute("fill", fill);

        svg.appendChild(rect);
    }

    public void drawCircle(int cx, int cy, int radius, String color, String fill) {
        Element circle = new Element("circle");
        circle.setAttribute("cx", String.valueOf(cx));
        circle.setAttribute("cy", String.valueOf(cy));
        circle.setAttribute("r", String.valueOf(radius));
        circle.setAttribute("stroke", color);
        circle.setAttribute("fill", fill);

        svg.appendChild(circle);
    }

    public void drawPolygon(String points, String color, String fill) {
        Element polygon = new Element("polygon");
        polygon.setAttribute("points", points); // e.g., "50,50 100,100 50,150"
        polygon.setAttribute("stroke", color);
        polygon.setAttribute("fill", fill);

        svg.appendChild(polygon);
    }
}
