package com.physicsim.game.visitor;

import com.physicsim.game.model.particle.Particle;
import com.physicsim.game.model.particle.Binding;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.model.rigidbody.RigidBodyEdge;
import com.physicsim.game.model.rigidbody.RigidCircle;
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
     * Draws the verlet point.
     * @param theEntity the VerletPoint
     * @return null
     */
    @Override
    public Void visit(final Particle theEntity) {
        int x = myOrigin.intX() + theEntity.getPosition().intX();
        int y = myOrigin.intY() + theEntity.getPosition().intY();
        myGraphics.setColor(Color.black);
        myGraphics.fillOval(x - theEntity.getRadius(),y - theEntity.getRadius(), theEntity.getDiameter(), theEntity.getDiameter());
        myGraphics.setColor(Color.yellow);
        myGraphics.fillOval(x - theEntity.getRadius() + 1,y - theEntity.getRadius() + 1, theEntity.getDiameter() - 2, theEntity.getDiameter() - 2);

        // draw velocity tail
//        myGraphics.setColor(Color.red);
//        myGraphics.drawLine(x, y, myOrigin.intX() + theEntity.getOldPosition().intX(), myOrigin.intY() + theEntity.getOldPosition().intY());
        return null;
    }

    /**
     * Draws the verlet binding.
     * @param theEntity the binding
     * @return null
     */
    @Override
    public Void visit(final Binding theEntity) {
        int x = myOrigin.intX() + theEntity.getStartPoint().getPosition().intX();
        int y = myOrigin.intY() + theEntity.getStartPoint().getPosition().intY();
        int x2 = myOrigin.intX() + theEntity.getEndPoint().getPosition().intX();
        int y2 = myOrigin.intY() + theEntity.getEndPoint().getPosition().intY();
        myGraphics.setColor(Color.black);
        myGraphics.drawLine(x, y, x2, y2);
        return null;
    }

    /**
     * Draws a rigid body.
     * @param theEntity the rigid body
     * @return null
     */
    @Override
    public Void visit(final RigidBody theEntity) {
        myGraphics.setColor(Color.black);
        for (final RigidBodyEdge edge : theEntity.getEdges()) {
            myGraphics.drawLine(
                    myOrigin.intX() + edge.getStart().intX(),
                    myOrigin.intY() + edge.getStart().intY(),
                    myOrigin.intX() + edge.getEnd().intX(),
                    myOrigin.intY() + edge.getEnd().intY());
        }
        myGraphics.fillOval(myOrigin.intX() + theEntity.getCenter().intX() - 1, myOrigin.intY() + theEntity.getCenter().intY() - 1, 2, 2);
        return null;
    }

    /**
     * Draws a rigid circle.
     * @param theEntity the rigid circle
     * @return null
     */
    @Override
    public Void visit(final RigidCircle theEntity) {
        myGraphics.setColor(Color.black);
        myGraphics.drawOval(
                myOrigin.intX() + theEntity.getOrigin().intX() - (int) theEntity.getRadius(),
                myOrigin.intY() + theEntity.getOrigin().intY() - (int) theEntity.getRadius(),
                theEntity.getDiameter(), theEntity.getDiameter());
        return null;
    }
}
