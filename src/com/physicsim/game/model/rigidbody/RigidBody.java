package com.physicsim.game.model.rigidbody;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.utility.Vector3;

/**
 * Abstract class for a rigid body physics game object. Must be a convex polygon.
 */
public abstract class RigidBody extends Rigid2D {
    /** The vertices of this rigid body. */
    private final Vector2[] myVertices;
    /** The edges of this rigid body. */
    private final RigidBodyEdge[] myEdges;

    /**
     * Creates a rigid body given a bunch of vertices without specifying the center of mass.
     * The center of mass is calculated using the vertices.
     * @param theMass     the center of mass
     * @param theVertices the number of new vertices as vectors
     */
    public RigidBody(final double theMass, final Vector2... theVertices) {
        this(VMath.findCentroid(theVertices), theMass, theVertices);
    }

    /**
     * Creates a rigid body given a bunch of vertices.
     * @param theCenterOfMass the center of mass
     * @param theMass         the uniformly distributed mass
     * @param theVertices     the number of new vertices as vectors
     * @throws IllegalArgumentException if it is not a convex 2d polygon that have 3 or more vertices
     */
    public RigidBody(final Vector2 theCenterOfMass, final double theMass, final Vector2... theVertices) {
        super(theCenterOfMass, theMass);
        // valid vertices
        if (theVertices.length < 3) throw new IllegalArgumentException("A rigid body must have 3 or more vertices");
        myVertices = theVertices;
        myEdges = new RigidBodyEdge[theVertices.length];

        for (int i = 0; i < myEdges.length; i++) {
            myEdges[i] = new RigidBodyEdge(myVertices[i], myVertices[(i + 1) % myEdges.length]);
        }
        if (!VMath.isConvex(myEdges)) throw new IllegalArgumentException("A rigid body must be convex");

        myMassMatrix.getData()[2][2] = VMath.findMomentOfInertia(myVertices, theCenterOfMass, theMass);
    }

    @Override
    protected void move() {
        final Vector2 vel = myVelocity.toVector2();

        for (final Vector2 v : myVertices) {
            v.add(vel);
        }

        for (final Vector2 v : myVertices) {
            VMath.rotate(v, new Vector2(myTransform.getX(), myTransform.getY()), myVelocity.getZ());
        }

        myTransform.add(myVelocity);
    }


    public RigidBodyEdge[] getEdges() {
        return myEdges;
    }

    public Vector2[] getVertices() {
        return myVertices;
    }

    @Override
    public String getName() {
        return "RigidBody";
    }
}
