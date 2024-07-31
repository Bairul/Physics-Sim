package com.physicsim.game.model.rigidbody;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;

/**
 * Abstract class for a rigid body physics game object. Must be a convex polygon.
 */
public abstract class RigidBody extends GameObject {

    /** The vertices of this rigid body. */
    private final Vector2[] myVertices;
    /** The edges of this rigid body. */
    private final RigidBodyEdge[] myEdges;
    private final Vector2 myCache;
    // physics
    private final double myMass;
    private final double myMoi;
    // Linear components
    /** The position vector but also the center of mass. */
    private final Vector2 myPosition;
    private final Vector2 myOldPosition;
    private final Vector2 myAcceleration;

    // Rotational components
    // In 2D space, angular components are scalars not vectors
    private double myAngularPos; // theta
    private double myOldAngularPos;
    private double myAngularAccel;

    /**
     * Creates a rigid body given a bunch of vertices.
     * @param theVertices the number of new vertices as vectors.
     * @throws IllegalArgumentException if it is not a convex 2d polygon that have 3 or more vertices
     */
    public RigidBody(final double theMass, final Vector2... theVertices) {
        // valid vertices
        if (theVertices.length < 2) throw new IllegalArgumentException("A rigid body must have 3 or more vertices");

        myVertices = theVertices;
        myEdges = new RigidBodyEdge[theVertices.length];

        for (int i = 0; i < myEdges.length; i++) {
            myEdges[i] = new RigidBodyEdge(myVertices[i], myVertices[(i + 1) % myEdges.length]);
        }
        if (!isConvex()) throw new IllegalArgumentException("A rigid body must be convex");

        myCache = new Vector2();
        myMass = theMass;
        myMoi = VMath.getMomentOfInertia(myVertices, myMass);

        myPosition = VMath.getCentroid(myVertices);
        myOldPosition = new Vector2(myPosition);
        myAcceleration = new Vector2();
    }

    /**
     * Helper method to test to see if the vertices of this rigid body is convex or not.
     * @return whether it is convex
     */
    private boolean isConvex() {
        int convexity = (int) Math.signum(myEdges[0].getEdge().crossProduct(myEdges[1].getEdge()));
        for (int i = 1; i < myEdges.length - 1; i++) {
            if ((int) Math.signum(myEdges[i].getEdge().crossProduct(myEdges[i + 1].getEdge())) * convexity < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines whether a point is contained in this rigid body. Uses ray casting algorithm
     * with a time complexity of θ(E) where E is the number of Edges in this rigid body.
     * @param thePoint the point
     * @return whether it is contained
     */
    public boolean overlaps(final Vector2 thePoint) {
        boolean count = false;
        for (final RigidBodyEdge e : myEdges) {
            if (e.rayCast(thePoint)) {
                count = !count;
            }
        }
        return count;
    }

    /**
     * Collision detection and resolution against a verlet object (particle).
     * Tests the verlet object against every edge of this rigid body so the
     * time complexity is θ(E). If there is a collision, reflects the verlet
     * particle over the closest edge.
     * @param theVO the verlet object
     */
    public void collideAgainst(final VerletObject theVO) {
        RigidBodyEdge closestEdge = null;
        double shortestDistance = Integer.MAX_VALUE;

        for (final RigidBodyEdge e : myEdges) {
            Vector2 intersection = e.getIntersect(theVO.getPosition(), theVO.getOldPosition());
            if (intersection != null) {
                double dist = theVO.getOldPosition().getDistance(intersection);
                if (dist < shortestDistance) {
                    shortestDistance = dist;
                    closestEdge = e;
                }
            }
        }

        // if there is a closest edge and the old position is not a point on the edge (solves a tunneling bug when the
        // point collides perfectly with the edge)
        if (closestEdge != null
                // check co-linearity
                && closestEdge.getEdge().crossProduct(theVO.getOldPosition().subNew(closestEdge.getStart())) != 0) {
            theVO.getPosition().set(closestEdge.reflectPoint(theVO.getPosition()));
            theVO.getOldPosition().set(closestEdge.reflectPoint(theVO.getOldPosition()));
        }
    }

    // physics
    public void applyForce(final Vector2 theForce) {
        // F = MA --> A = F / M
        myCache.set(theForce);
        myCache.div(myMass);
        myAcceleration.add(myCache);
    }

    /**
     * Sets the velocity of the rigid body. Velocity in verlet integration is done by using changing the old
     * position vector.
     * @param theVelocity the velocity vector
     */
    public void setVelocity(final Vector2 theVelocity) {
        myCache.set(myPosition);
        myCache.sub(theVelocity);
        myOldPosition.set(myCache);
    }

    protected void move() {
        // linear movement
        myCache.set(myPosition);
        translateBody(myPosition.subNew(myOldPosition).addNew(myAcceleration));
        myOldPosition.set(myCache);
    }

    protected void postUpdate() {
        myAngularAccel = 0;
        myAcceleration.set(0, 0);
    }

    // ************========  rotational physics  ========************ \\

    public void applyTorque(final Vector2 theLinearForce, final Vector2 thePointOfAction) {
        // T = r x F
        // T = I * a --> a = T / I

        // r is the lever arm from the force of action to the center of mass
        myCache.set(thePointOfAction);
        myCache.sub(myPosition);
        double t = myCache.crossProduct(theLinearForce);
        myAngularAccel += t / myMoi;
    }

    public void applyTorque(final double theTorque, final Vector2 thePointOfAction) {
        // T = I * a --> a = T / I
        myAngularAccel += theTorque / myMoi;
    }

    public void setAngularVelocity(final double theAngVelocity) {
        myOldAngularPos = -theAngVelocity;
    }

    protected void rotate() {
        double current = myAngularPos;
        rotateBody(myAngularPos - myOldAngularPos + myAngularAccel);
        myOldAngularPos = current;
    }

    public void collideAgainst(final RigidBody theRB) {
        //TODO: collision against another rigid body
    }

    /**
     * Rotates the rigid body about its center of mass by some radian.
     * @param theRadians the degree
     */
    private void rotateBody(final double theRadians) {
        for (final Vector2 v : myVertices) {
            VMath.rotate(v, myPosition, -theRadians);
        }
        myAngularPos += theRadians;

        if (myAngularPos > Math.PI) myAngularPos -= Math.PI * 2;
        else if (myAngularPos < -Math.PI) myAngularPos += Math.PI * 2;
    }

    /**
     * Translates the rigid body by a vector.
     * @param theTranslation the vector of translation
     */
    private void translateBody(final Vector2 theTranslation) {
        for (final Vector2 v : myVertices) {
            v.add(theTranslation);
        }
        myPosition.add(theTranslation);
    }

    // ======== getters ========

    /**
     * Gets the edges of the rigid body.
     * @return the edges as rigid body edges
     */
    public RigidBodyEdge[] getEdges() {
        return myEdges;
    }

    /**
     * Gets the vertices of the rigid body.
     * @return the vertices as vectors
     */
    public Vector2[] getVertices() {
        return myVertices;
    }

    /**
     * Gets the center of mass of the rigid body.
     * @return the center vector
     */
    public Vector2 getCenter() {
        return myPosition;
    }

    public double getMass() {
        return myMass;
    }
}
