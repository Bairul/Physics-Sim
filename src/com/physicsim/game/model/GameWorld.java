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
    private final List<GameObject> myDynamicObjects;
    /** The list of boundaries for the game. */
    private final List<GameObject> myStaticObjects;

    /**
     * Constructs the game world with a list of game objects.
     */
    public GameWorld() {
        myDynamicObjects = new ArrayList<>();
        myStaticObjects = new ArrayList<>();
    }

    /**
     * Add a dynamic game object to the end of the list. These should be game objects that can move.
     * @param theObject the new dynamic game object
     */
    public void addDynamicObject(final GameObject theObject) {
        myDynamicObjects.add(theObject);
    }

    /**
     * Add a Static game object. These should be game objects that cannot move.
     * @param theObject the new static game object
     */
    public void addStaticObject(final GameObject theObject) {
        myStaticObjects.add(theObject);
    }

    /**
     * Clears all game objects.
     */
    public void clearDynamicObjects() {
        myDynamicObjects.clear();
    }

    public void clearStaticObjects() {
        myStaticObjects.clear();
    }

    /**
     * Gets the list of dynamic objects.
     * @return the list of game objects
     */
    public List<GameObject> getDynamicObjects() {
        return myDynamicObjects;
    }

    /**
     * Gets the list of static boundaries.
     * @return the list of game boundaries
     */
    public List<GameObject> getStaticObjects() {
        return myStaticObjects;
    }
}
