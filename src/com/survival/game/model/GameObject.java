package com.survival.game.model;

import com.survival.game.utility.Vector2;
import com.survival.game.visitor.GameObjectVisitor;

/**
 * Abstract class for all game objects such as dynamic or static.
 */
public abstract class GameObject {
    /** Constant for the sizing. */
    private static final int SIZE_SCALE = 5;
    /** The position vector. */
    protected Vector2 myPosition;
    /** The radius of the object. */
    protected float myRadius;
    /** The mass of the object. */
    protected float myMass;

    /**
     * Creates a game object given the initial position and mass.
     * @param thePosition position vector
     * @param theMass mass
     */
    public GameObject(final Vector2 thePosition, final float theMass) {
        myPosition = new Vector2(thePosition);
        myMass = theMass;
        // sets radius to be the root of the mass because of the area of a circle
        myRadius = (float) Math.sqrt(theMass) * SIZE_SCALE;
    }

    /**
     * Gets the position: the x and y coordinate of the object.
     * @return the position vector
     */
    public Vector2 getPosition() {
        return myPosition;
    }

    /**
     * Gets the object's mass.
     * @return the mass
     */
    public float getMass() {
        return myMass;
    }

    /**
     * Gets the radius of the object.
     * @return the radius as a int
     */
    public int getRadius() {
        return (int) myRadius;
    }

    /**
     * Gets the diameter.
     * @return the diameter as a int
     */
    public int getDiameter() {
        return (int) (myRadius * 2);
    }

    /**
     * Updates the object.
     */
    public abstract void update();

    /**
     * Updates the object at the very end. Useful to reset fields.
     */
    protected abstract void postUpdate();

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
