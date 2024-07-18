package com.physicsim.game.model.rigidbody;

import com.physicsim.game.utility.Vector2;

public final class RigidBodyEdge {
    private final Vector2 myStart;
    private final Vector2 myEnd;
    public RigidBodyEdge(final Vector2 theStart, final Vector2 theEnd) {
        myStart = theStart;
        myEnd = theEnd;
    }

    public Vector2 getStart() {
        return myStart;
    }

    public Vector2 getEnd() {
        return myEnd;
    }
}
