package com.physicsim.game.model;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

/**
 * Abstract class for all game objects. Everything in the game world must be a Game Object.
 *
 * @author Bairu Li
 */
public abstract class GameObject {
    /**
     * Creates a game object.
     */
    public GameObject() {}

    /**
     * Updates the object.
     */
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
    public abstract <V> V accept(final GameObjectVisitor<V> v);
}
