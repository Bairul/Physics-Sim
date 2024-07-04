package com.physicsim.game.model;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

public class VerletPoint extends VerletObject {
    private boolean isPinned;

    /**
     * Creates a game object given the initial position and mass.
     *
     * @param thePosition position vector
     * @param theMass     mass
     */
    public VerletPoint(final Vector2 thePosition, final double theMass, final boolean thePinned) {
        super(thePosition, theMass);
        isPinned = thePinned;
    }

    public VerletPoint(final Vector2 thePosition, final double theMass) {
        super(thePosition, theMass);
        isPinned = false;
    }

    public void setPinned(final boolean thePinned) {
        isPinned = thePinned;
    }

    public boolean isPinned() {
        return isPinned;
    }

    @Override
    public void update() {
        if (isPinned) return;
        // must have
        move();

        // must have
        postUpdate();
    }

    @Override
    public <V> V accept(GameObjectVisitor<V> v) {
        return v.visit(this);
    }
}
