package com.survival.game.model;

import com.survival.game.utility.Vector2;
import com.survival.game.visitor.GameObjectVisitor;

public class TempEntity extends MovableObject {
    public TempEntity(Vector2 thePosition, float theMass) {
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
