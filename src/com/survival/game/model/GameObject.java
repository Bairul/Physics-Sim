package com.survival.game.model;

import com.survival.game.utility.Vector2;
import com.survival.game.visitor.GameObjectVisitor;

public abstract class GameObject {
    protected Vector2 myPosition;
    // diameter
    protected float mySize;
    public GameObject(final Vector2 thePosition, final float theSize) {
        myPosition = new Vector2(thePosition);
        mySize = theSize;
    }

    public Vector2 getPosition() {
        return myPosition;
    }

    public int getSize() {
        return (int) mySize;
    }

    public void setSize(float theSize) {
        mySize = theSize;
    }

    public abstract void update();

    /**
     * The act of accepting a visitor is to apply the underlying function specified in the visitor
     * to this entity. This allows common functionalities to be extracted into one class and
     * extensions (new methods) can be introduced without modifying all classes in the GameObject
     * hierarchy.
     *
     * @param v   the incoming visitor
     * @param <V> abstract return type of visitor
     * @return output data from application of visitor
     */
    public abstract <V> V accept(GameObjectVisitor<V> v);
}
