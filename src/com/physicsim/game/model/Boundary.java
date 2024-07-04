package com.physicsim.game.model;

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

    @Override
    public void update() {
    }

    public Vector2 getOrigin() {
        return myBounds[0];
    }

    public Vector2 getFirstPoint() {
        return myBounds[1];
    }

    public Vector2 getLastPoint() {
        return myBounds[myBounds.length - 1];
    }

    public Vector2[] getBounds() {
        return myBounds;
    }

    @Override
    public <V> V accept(GameObjectVisitor<V> v) {
        return v.visit(this);
    }
}
