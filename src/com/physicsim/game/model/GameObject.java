package com.physicsim.game.model;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

/**
 * Abstract class for all game objects. Everything in the game world must be a Game Object.
 * Therefore, for it to be in the game, it needs to have a position vector.
 * Inheriting from Vector2, allows all game objects to a vector themselves as their in-game position.
 *
 * @author Bairu Li
 */
public abstract class GameObject extends Vector2 {
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
