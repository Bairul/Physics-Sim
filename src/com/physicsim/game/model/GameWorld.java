package com.physicsim.game.model;

import java.util.LinkedList;
import java.util.List;

public class GameWorld {

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
