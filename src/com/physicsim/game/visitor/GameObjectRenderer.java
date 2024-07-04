package com.physicsim.game.visitor;

import com.physicsim.game.model.VerletPoint;
import com.physicsim.game.model.VerletStick;
import com.physicsim.game.utility.Vector2;

import java.awt.Color;
import java.awt.Graphics;

/**
 * GameObjectRenderer provides functionality to render all kinds of Game objects. This is a
 * collection of draw functionality to keep models free from directly handling textures.
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
     * Draws the temporary entity for testing and development.
     * @param theEntity the TempEntity
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

    @Override
    public Void visit(VerletStick theEntity) {
        int x = myOrigin.intX() + theEntity.getPoint1().getPosition().intX();
        int y = myOrigin.intY() + theEntity.getPoint1().getPosition().intY();
        int x2 = myOrigin.intX() + theEntity.getPoint2().getPosition().intX();
        int y2 = myOrigin.intY() + theEntity.getPoint2().getPosition().intY();
        myGraphics.setColor(Color.black);
        myGraphics.drawLine(x, y, x2, y2);
        return null;
    }
}
