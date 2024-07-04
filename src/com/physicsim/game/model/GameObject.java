package com.physicsim.game.model;

import com.physicsim.game.visitor.GameObjectVisitor;

/**
 * Abstract class for all game objects such as dynamic or static.
 */
public abstract class GameObject {
    /**
     * Creates a game object.
     */
    public GameObject() {

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
