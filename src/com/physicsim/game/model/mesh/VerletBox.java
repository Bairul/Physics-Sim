package com.physicsim.game.model.mesh;

import com.physicsim.game.model.*;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

/**
 * Creates a box mesh that obeys physics using Verlet points and sticks.
 *
 * @author Bairu Li
 */
public class VerletBox extends GameObject {
    /** The array of vertices of the box. */
    private final VerletPoint[] myVertices;
    /** The array of edges of the box. */
    private final VerletStick[] myEdges;

    /**
     * Constructs a box give the x and y and the side length of the box with its mass.
     * The x and y parameters would be the top-left corner of the box.
     * @param theX      the x
     * @param theY      the y
     * @param theLength the side length
     * @param theMass   the mass
     */
    public VerletBox(final double theX, final double theY, final double theLength, final double theMass) {
        myVertices = new VerletPoint[4];
        myEdges = new VerletStick[6];

        final Vector2 cache = new Vector2();
        cache.set(theX, theY);
        myVertices[0] = new VerletPoint(cache, theMass);
        cache.set(theX + theLength, theY);
        myVertices[1] = new VerletPoint(cache, theMass);
        cache.set(theX + theLength, theY + theLength);
        myVertices[2] = new VerletPoint(cache, theMass);
        cache.set(theX, theY + theLength);
        myVertices[3] = new VerletPoint(cache, theMass);

        myEdges[0] = new VerletStick(myVertices[0], myVertices[1]);
        myEdges[1] = new VerletStick(myVertices[1], myVertices[2]);
        myEdges[2] = new VerletStick(myVertices[2], myVertices[3]);
        myEdges[3] = new VerletStick(myVertices[3], myVertices[0]);
        myEdges[4] = new VerletStick(myVertices[3], myVertices[1]);
        myEdges[5] = new VerletStick(myVertices[2], myVertices[0]);

        // apply rotation for testing
        cache.set(30, -30);
        myVertices[0].applyForce(cache);
    }

    /**
     * Gets the box vertices.
     * @return the array of verlet points
     */
    public VerletPoint[] getVertices() {
        return myVertices;
    }

    /**
     * Gets the box edges.
     * @return the array of verlet sticks
     */
    public VerletStick[] getEdges() {
        return myEdges;
    }

    /**
     * Updates the mesh by updating the vertices first then edges.
     */
    @Override
    public void update() {
        for (final VerletPoint p : myVertices) {
            p.applyForce(GameWorld.GRAVITY);
            p.bounceOffBoundary(GameWorld.BOUNDARY);
            p.update();
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
