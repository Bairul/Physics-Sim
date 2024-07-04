package com.survival.game.model;

import com.survival.game.utility.Vector2;
import com.survival.game.visitor.GameObjectVisitor;

public class VerletEntity extends VerletObject {
    /**
     * Creates a game object given the initial position and mass.
     *
     * @param thePosition position vector
     * @param theMass     mass
     */
    public VerletEntity(Vector2 thePosition, float theMass) {
        super(thePosition, theMass);
    }

    @Override
    public void update() {
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
