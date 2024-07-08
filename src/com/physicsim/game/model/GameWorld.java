package com.physicsim.game.model;

import com.physicsim.game.utility.Vector2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameWorld {
    /** Constant field for setting the world gravity. */
    public static final Vector2 GRAVITY = new Vector2(0, 0);
    /** Constant field for setting the boundary of the world screen. */
    public static final Vector2 SCREEN_BOUNDARY = new Vector2(0, 0);

    /** The list of game objects. */
    private final List<GameObject> myObjects;
    /** The list of boundaries for the game. */
    private final List<VerletStick> myBindings;

    /**
     * Constructs the game world with a list of game objects.
     */
    public GameWorld() {
        myObjects = new LinkedList<>();
        myBindings = new ArrayList<>();
    }

    /**
     * Add a game object to the end of the list.
     * @param theObject the new game object
     */
    public void addGameObject(final GameObject theObject) {
        myObjects.add(theObject);
    }

    /**
     * Add a binding (stick or edge) to the end of the list. These bind game objects together.
     * @param theBinding the new game object
     */
    public void addBinding(final VerletStick theBinding) {
        myBindings.add(theBinding);
    }

    /**
     * Clears all game objects.
     */
    public void clearGameObjects() {
        myObjects.clear();
    }

    /**
     * Gets the list of game objects.
     * @return the list of game objects
     */
    public List<GameObject> getObjects() {
        return myObjects;
    }

    /**
     * Gets the list of game boundaries.
     * @return the list of game boundaries
     */
    public List<VerletStick> getBindings() {
        return myBindings;
    }
}
