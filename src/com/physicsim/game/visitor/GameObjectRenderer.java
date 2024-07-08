package com.physicsim.game.visitor;

import com.physicsim.game.model.Boundary;
import com.physicsim.game.model.VerletEdge;
import com.physicsim.game.model.VerletPoint;
import com.physicsim.game.model.VerletStick;
import com.physicsim.game.model.mesh.VerletBox;
import com.physicsim.game.utility.Vector2;

import java.awt.Color;
import java.awt.Graphics;

/**
 * GameObjectRenderer provides functionality to render all kinds of Game objects. This is a
 * collection of draw functionality to keep models free from directly handling textures.
 *
 */
public class GameObjectRenderer extends GameObjectVisitor<Void> {
    /** The graphics for drawing. */
    private Graphics myGraphics;
    /** The origin of the screen. */
    private final Vector2 myOrigin;

    /**
     * Constructor initializing the origin.
     * @param theOrigin the origin
     */
    public GameObjectRenderer(final Vector2 theOrigin) {
        myOrigin = new Vector2(theOrigin);
        myGraphics = null;
    }

    /**
     * Updates the graphics.
     * @param theG the graphics
     */
    public void updateGraphics(final Graphics theG) {
        myGraphics = theG;
    }

    /**
     * Draws the verlet point for testing and development.
     * @param theEntity the VerletPoint
     * @return null
     */
    @Override
    public Void visit(final VerletPoint theEntity) {
        int x = myOrigin.intX() + theEntity.getPosition().intX();
        int y = myOrigin.intY() + theEntity.getPosition().intY();
        myGraphics.setColor(Color.black);
        myGraphics.fillOval(x - theEntity.getRadius(),y - theEntity.getRadius(), theEntity.getDiameter(), theEntity.getDiameter());
        myGraphics.setColor(Color.yellow);
        myGraphics.fillOval(x - theEntity.getRadius() + 1,y - theEntity.getRadius() + 1, theEntity.getDiameter() - 2, theEntity.getDiameter() - 2);
        return null;
    }

    /**
     * Draws the verlet stick for testing and development.
     * @param theEntity the VerletStick
     * @return null
     */
    @Override
    public Void visit(final VerletStick theEntity) {
        int x = myOrigin.intX() + theEntity.getStartPoint().getPosition().intX();
        int y = myOrigin.intY() + theEntity.getStartPoint().getPosition().intY();
        int x2 = myOrigin.intX() + theEntity.getEndPoint().getPosition().intX();
        int y2 = myOrigin.intY() + theEntity.getEndPoint().getPosition().intY();
        myGraphics.setColor(Color.black);
        myGraphics.drawLine(x, y, x2, y2);
        return null;
    }

    /**
     * Draws a box.
     * @param theEntity the VerletBox
     * @return null
     */
    @Override
    public Void visit(final VerletBox theEntity) {
        for (VerletStick s : theEntity.getEdges()) {
            s.accept(this);
        }
        for (VerletPoint p : theEntity.getVertices()) {
            p.accept(this);
        }
        return null;
    }

    /**
     * Draws a boundary.
     * @param theEntity the Boundary
     * @return null
     */
    @Override
    public Void visit(final Boundary theEntity) {
        if (theEntity.getEdges() != null) {
            myGraphics.setColor(Color.black);

            for (final VerletEdge e : theEntity.getEdges()) {
                myGraphics.drawLine(
                        myOrigin.intX() + e.getStartPoint().getPosition().intX(),
                        myOrigin.intY() + e.getStartPoint().getPosition().intY(),
                        myOrigin.intX() + e.getEndPoint().getPosition().intX(),
                        myOrigin.intY() + e.getEndPoint().getPosition().intY());
            }
        }

        return null;
    }
}
