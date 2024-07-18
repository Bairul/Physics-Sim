package com.physicsim.game.model.rigidbody;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.utility.Vector2;

public abstract class RigidBody extends GameObject {

    private final Vector2[] myVertices;
    private final RigidBodyEdge[] myEdges;

    public RigidBody(final Vector2... theVertices) {
        if (theVertices.length < 2) throw new IllegalArgumentException("A rigid body must have 3 or more vertices");
        myVertices = theVertices;
        myEdges = new RigidBodyEdge[theVertices.length];

        for (int i = 0; i < myEdges.length; i++) {
            myEdges[i] = new RigidBodyEdge(myVertices[i], myVertices[(i + 1) % myEdges.length]);
        }
    }

    public RigidBodyEdge[] getEdges() {
        return myEdges;
    }
}
