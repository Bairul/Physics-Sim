package com.survival.game.model;

import com.survival.game.utility.Vector2;
import com.survival.game.visitor.GameObjectVisitor;

public abstract class GameObject {
    private static final int SIZE_SCALE = 20;
    protected Vector2 myPosition;
    protected float myRadius;
    public GameObject(final Vector2 thePosition, final float theRadius) {
        myPosition = new Vector2(thePosition);
        myRadius = theRadius * SIZE_SCALE;
    }

    public Vector2 getPosition() {
        return myPosition;
    }
    public int getRadius() {
        return (int) myRadius;
    }
    public int getDiameter() {
        return (int) (myRadius * 2);
    }

    public void setRadius(float theRadius) {
        myRadius = theRadius;
    }

    /**
     * Updates the object.
     */
    public abstract void update();

    /**
     * Updates the object at the very end. Useful to reset fields.
     */
    public abstract void postUpdate();

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
