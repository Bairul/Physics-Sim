package com.physicsim.game.model.rigidbody;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

/**
 * Class for a rigid circle physics game object.
 */
public class RigidCircle extends GameObject {
    /** The origin of the circle. */
    private final Vector2 myOrigin;
    /** The radius of the circle. */
    private final double myRadius;

    /**
     * Constructs a circle given the origin and the radius.
     * @param theOrigin the origin
     * @param theRadius the radius (must be positive)
     * @throws IllegalArgumentException if radius is a non-positive
     */
    public RigidCircle(final Vector2 theOrigin, final double theRadius) {
        if (theRadius <= 0) throw new IllegalArgumentException("radius must be positive");
        myOrigin = new Vector2(theOrigin);
        myRadius = theRadius;
    }
    @Override
    public void update() {

    }

    /**
     * Gets the radius of the circle.
     * @return the radius
     */
    public double getRadius() {
        return myRadius;
    }

    /**
     * Gets the diameter of the circle as an integer. Useful for graphics.
     * @return the diameter
     */
    public int getDiameter() {
        return Math.round((float) myRadius * 2F);
    }

    /**
     * Gets the origin vector of the circle.
     * @return the origin
     */
    public Vector2 getOrigin() {
        return myOrigin;
    }

    @Override
    public <V> V accept(final GameObjectVisitor<V> theV) {
        return theV.visit(this);
    }
}
