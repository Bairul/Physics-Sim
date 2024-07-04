package com.physicsim.game.model.mesh;

import com.physicsim.game.model.*;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

public class VerletBox extends GameObject {
    private final VerletPoint[] myVertices;
    private final VerletStick[] myEdges;

    public VerletBox(final double theX, final double theY, final double theLength, final double theMass) {
        myVertices = new VerletPoint[4];
        myEdges = new VerletStick[6];

        final Vector2 cache = new Vector2();
//        for (int i = 0; i < 4; i++) {
//            cache.set(theX + (i >> 1) * theLength, theY + i % 2 * theLength);
//            myVertices[i] = new VerletPoint(cache, theMass);
//        }
//
//        myEdges[0] = new VerletStick(myVertices[0], myVertices[1]);
//        myEdges[1] = new VerletStick(myVertices[1], myVertices[3]);
//        myEdges[2] = new VerletStick(myVertices[3], myVertices[2]);
//        myEdges[3] = new VerletStick(myVertices[2], myVertices[0]);
//        myEdges[4] = new VerletStick(myVertices[0], myVertices[3]);
//        myEdges[5] = new VerletStick(myVertices[1], myVertices[2]);

        cache.set(theX, theY);
        myVertices[0] = new VerletPoint(cache, 1);
        cache.set(theX + theLength, theY);
        myVertices[1] = new VerletPoint(cache, 1);
        cache.set(theX + theLength, theY + theLength);
        myVertices[2] = new VerletPoint(cache, 1);
        cache.set(theX, theY + theLength);
        myVertices[3] = new VerletPoint(cache, 1);

        myEdges[0] = new VerletStick(myVertices[0], myVertices[1]);
        myEdges[1] = new VerletStick(myVertices[1], myVertices[2]);
        myEdges[2] = new VerletStick(myVertices[2], myVertices[3]);
        myEdges[3] = new VerletStick(myVertices[3], myVertices[0]);
        myEdges[4] = new VerletStick(myVertices[3], myVertices[1]);
        myEdges[5] = new VerletStick(myVertices[2], myVertices[0]);

        cache.set(30,-30);
        myVertices[0].applyForce(cache);
    }

    public VerletPoint[] getVertices() {
        return myVertices;
    }

    public VerletStick[] getEdges() {
        return myEdges;
    }

    @Override
    public void update() {
        int c = 0;
        for (final VerletPoint p : myVertices) {
            p.applyForce(GameWorld.GRAVITY);
            p.bounceOffBoundary(GameWorld.BOUNDARY);
            p.update();
            c++;
        }
        for (final VerletStick s : myEdges) {
            s.update();
        }
    }

    @Override
    public <V> V accept(GameObjectVisitor<V> v) {
        return v.visit(this);
    }
}
