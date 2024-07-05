package com.physicsim.game.model;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

/**
 * This class inherits from VerletObject and creates a point that obeys physics dynamically or statically.
 *
 * @author Bairu Li
 */
public class VerletPoint extends VerletObject {
    /** Whether this point is static or dynamic. */
    private boolean isPinned;

    /**
     * Creates a point given the initial position and mass. To change this to be static, put true for pinned.
     * @param thePosition position vector
     * @param theMass     mass
     * @param theRadius   radius of the object (set to 0 to scale with mass)
     * @param thePinned   pinned
     */
    public VerletPoint(final Vector2 thePosition, final double theMass, final double theRadius, final boolean thePinned) {
        super(thePosition, theMass, theRadius);
        isPinned = thePinned;
    }

    /**
     * @see #VerletPoint(Vector2, double, double, boolean)
     */
    public VerletPoint(final Vector2 thePosition, final double theMass, final double theRadius) {
        this(thePosition, theMass, theRadius, false);
    }

    /**
     * @see #VerletPoint(Vector2, double, double, boolean)
     */
    public VerletPoint(final Vector2 thePosition, final double theMass, final boolean thePinned) {
        this(thePosition, theMass, 1, thePinned);
    }

    /**
     * @see #VerletPoint(Vector2, double, double, boolean)
     */
    public VerletPoint(final Vector2 thePosition, final double theMass) {
        this(thePosition, theMass, 1, false);
    }

    /**
     * Sets the pin status.
     * @param thePinned pin to be changed to
     */
    public void setPinned(final boolean thePinned) {
        isPinned = thePinned;
    }

    /**
     * Gets whether this point is pinned or not.
     * @return the pin
     */
    public boolean isPinned() {
        return isPinned;
    }

    @Override
    public void update() {
        // don't update if pinned
        if (isPinned) return;
        // must have
        move();

        // must have
        postUpdate();
    }

    @Override
    public <V> V accept(final GameObjectVisitor<V> v) {
        return v.visit(this);
    }
}
