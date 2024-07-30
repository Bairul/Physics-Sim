package com.physicsim.game.model.rigidbody;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.utility.VMath;
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
     * Collision detection and resolution against a Verlet object (particle).
     * Tests if the particle collides against the circumference of the circle, then reflects it over the tangent
     * of the collision.
     * @see VMath#intersect(Vector2, Vector2, Vector2, double)
     * @param theVO the verlet object
     */
    public void collideAgainst(final VerletObject theVO) {
        if (theVO.getOldPosition().getDistance(myOrigin) == myRadius) return;

        final Vector2[] intersections = VMath.intersect(theVO.getOldPosition(), theVO.getPosition(), myOrigin, myRadius);
        if (intersections.length < 1) return;

        // if more than 1 intersection, get the one closest to the old position
        final Vector2 intersect = intersections.length > 1
                && intersections[1].getDistance(theVO.getOldPosition()) < intersections[0].getDistance(theVO.getOldPosition())
                ? intersections[1] : intersections[0];
        final Vector2 tanVector = new Vector2();

        try {
            final double m = VMath.slope(myOrigin, intersect);
            if (m == 0) {
                // horizontal slope (intersection point is directly left/right of center)
                tanVector.set(intersect.getX(), intersect.getY() + 1);
            } else {
                final double tangent = -1 / m;
                tanVector.set(intersect.getX() + 1, intersect.getY() + tangent);
            }
        } catch (final ArithmeticException e) {
            // vertical slope (intersection point is directly top/bot of center)
            tanVector.set(intersect.getX() + 1, intersect.getY());
        }

        theVO.getPosition().set(VMath.reflect(intersect, tanVector, theVO.getPosition()));
        theVO.getOldPosition().set(VMath.reflect(intersect, tanVector, theVO.getOldPosition()));
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
