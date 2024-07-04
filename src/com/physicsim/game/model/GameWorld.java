package com.physicsim.game.model;

import com.physicsim.game.utility.Vector2;

import java.util.LinkedList;
import java.util.List;

public class GameWorld {
    public static final Vector2 GRAVITY = new Vector2(0, 0);
    /** The boundary of the screen. */
    public static final Vector2 BOUNDARY = new Vector2(0, 0);

    private LinkedList<GameObject> myObjects;

    public GameWorld() {
        myObjects = new LinkedList<>();
    }

    public void addGameObject(final GameObject theObject) {
        myObjects.add(theObject);
    }

    public List<GameObject> getObjects() {
        return myObjects;
    }
}
