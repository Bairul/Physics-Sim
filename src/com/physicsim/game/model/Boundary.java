package com.physicsim.game.model;

import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

public class Boundary extends GameObject {

    final Vector2[] myBounds;

    /**
     * Creates a boundary shape given an initial x and y. The following vectors will be the sides of the boundary
     * relative to the initial x and y. The last vector will connect with the initial vector to enclose the shape.
     * @param theX      the starting x
     * @param theY      the starting y
     * @param theBounds the boundaries
     */
    public Boundary(final double theX, final double theY, final Vector2... theBounds) {
        myBounds = new Vector2[theBounds.length + 1];

        myBounds[0] = new Vector2(theX, theY);
        for (int i = 1; i < myBounds.length; i++) {
            myBounds[i] = myBounds[i - 1].addNew(theBounds[i - 1]);
        }
    }

    /**
     * Checks if a vector point is within the boundary. Uses linear interpolation for ray tracing.
     * @param thePoint the point vector
     * @return if the box contains the point
     */
    public boolean overlaps(final Vector2 thePoint) {
        boolean count = false;
        for (int i = 1; i <= myBounds.length; i++) {
            final Vector2 start = myBounds[i - 1];
            final Vector2 end = myBounds[i % myBounds.length];

            if (thePoint.getX() < start.getX() != thePoint.getX() < end.getX()
                    && thePoint.getY() < VMath.lerpY(start, end, thePoint.getX())) {
                count = !count;
            }
        }
        return count;
    }

    public void handleCollision(final VerletPoint theVp) {
        for (int i = 1; i <= myBounds.length; i++) {
            final Vector2 start = myBounds[i - 1];
            final Vector2 end = myBounds[i % myBounds.length];

            if (VMath.intersect(start, end, theVp.getPosition(), theVp.getOldPosition()) != null) {
                theVp.setPinned(true);
            }
        }
    }

    @Override
    public void update() {
    }

    public Vector2 getOrigin() {
        return myBounds[0];
    }

    public Vector2 getFirstPoint() {
        return myBounds[1];
    }

    public Vector2[] getBounds() {
        return myBounds;
    }

    @Override
    public <V> V accept(GameObjectVisitor<V> v) {
        return v.visit(this);
    }
}
