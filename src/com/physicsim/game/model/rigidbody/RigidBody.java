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
    private final Vector2 myImpulse;

    // Rotational components
    // In 2D space, angular components are scalars not vectors
    private double myAngularPos; // theta
    private double myOldAngularPos;
    private double myAngularAccel;
    private double myAngularImpulse;

    protected boolean hasPhysics;

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
        myMoi = VMath.findMomentOfInertia(myVertices, myMass);

        myPosition = VMath.findCentroid(myVertices);
        myOldPosition = new Vector2(myPosition);
        myAcceleration = new Vector2();
        myImpulse = new Vector2();
    }

    public void setPhysics(final boolean thePhysics) {
        hasPhysics = thePhysics;
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

        // if closest edge exists and the old position is not a point on the edge (solves a tunneling bug when the
        // point collides perfectly with the edge)
        if (closestEdge != null
                // check co-linearity
                && closestEdge.getEdge().crossProduct(theVO.getOldPosition().subNew(closestEdge.getStart())) != 0) {
            theVO.getPosition().set(closestEdge.reflectPoint(theVO.getPosition()));
            theVO.getOldPosition().set(closestEdge.reflectPoint(theVO.getOldPosition()));
        }
    }

    // physics
    public void applyLinearForce(final Vector2 theForce) {
        // F = MA --> A = F / M
        myCache.set(theForce);
        myCache.div(myMass);
        myAcceleration.add(myCache);
    }

    /**
     * Sets the velocity of the rigid body. Velocity in verlet integration is done by changing the old
     * position vector.
     * @param theVelocity the velocity vector
     */
    public void setLinearVelocity(final Vector2 theVelocity) {
        myCache.set(myPosition);
        myCache.sub(theVelocity);
        myOldPosition.set(myCache);
    }

    // ************========  rotational physics  ========************ \\

    public void applyTorque(final Vector2 theLinearForce, final Vector2 thePointOfAction) {
        // T = r x F
        // T = I * a --> a = T / I
        // r is the lever arm from the force of action to the center of mass
        myCache.set(thePointOfAction);
        myCache.sub(myPosition);
        // negative to correct the spin
        myAngularAccel += -1 * myCache.crossProduct(theLinearForce) / myMoi;
    }

    public void applyTorque(final double theTorque) {
        // T = I * a --> a = T / I
        myAngularAccel += theTorque / myMoi;
    }

    public void setAngularVelocity(final double theAngVelocity) {
        myOldAngularPos = myAngularPos - theAngVelocity;
    }

    public boolean collideAgainst(final RigidBody theRB) {
        final Vector2[] a = VMath.findAxisOfLeastPenetration(this, theRB);
        if (a.length == 0) return false;

        final Vector2[] b = VMath.findAxisOfLeastPenetration(theRB, this);
        if (b.length == 0) return false;
//
//        System.out.println(a[0] + " " + a[1] + " " + a[2]);
//        System.out.println(b[0] + " " + b[1] + " " + b[2]);

        final boolean isACollision = a[2].getX() > b[2].getX();
        final Vector2 collisionPoint = isACollision ? a[0] : b[0];
        final Vector2 collisionNormal = isACollision ? a[1] : b[1];

        final double sumInvMassA = 1 / myMass + (theRB.hasPhysics ? 1 / theRB.myMass : 0);
        final double normalSquared = collisionNormal.dotProduct(collisionNormal);
        final Vector2 relVelA = getLinearVelocity().addNew(getLinearAngularVelocity(collisionPoint));
        final Vector2 relVelB = theRB.getLinearVelocity().addNew(theRB.getLinearAngularVelocity(collisionPoint));
        final double relNorm = relVelB.subNew(relVelA).dotProduct(collisionNormal);

        final Vector2 rA = collisionPoint.subNew(myPosition);
        final Vector2 rB = collisionPoint.subNew(theRB.myPosition);
        final double angA = collisionNormal.crossProduct(rA);
        final double angB = collisionNormal.crossProduct(rB);

        final double denom = normalSquared * sumInvMassA + (angA * angA / myMoi) + (theRB.hasPhysics ? (angB * angB / theRB.myMoi) : 0);
        final double impulse = -2 * relNorm / denom;

        if (hasPhysics) applyImpulse(-impulse, collisionNormal, rA);
        if (theRB.hasPhysics) theRB.applyImpulse(impulse, collisionNormal, rB);

        return true;
    }

    public void applyImpulse(final double theImpulseMag, final Vector2 theDirection, final Vector2 theDistance) {
        final Vector2 jn = theDirection.mulNew(theImpulseMag);
        myImpulse.add(jn);
        myAngularImpulse += jn.crossProduct(theDistance);
    }

    protected void move() {
        // impulsive movement
        setLinearVelocity(getLinearVelocity().addNew(myImpulse.divNew(myMass)));
        setAngularVelocity(getAngularVelocity() + myAngularImpulse / myMoi);

        // linear movement
        myCache.set(myPosition);
        translateBody(myPosition.subNew(myOldPosition).addNew(myAcceleration));
        myOldPosition.set(myCache);

        // angular movement
        double current = myAngularPos;
        rotateBody(myAngularPos - myOldAngularPos + myAngularAccel);
        myOldAngularPos = current;
    }

    protected void postUpdate() {
        myAngularImpulse = 0;
        myImpulse.set(0, 0);
        myAngularAccel = 0;
        myAcceleration.set(0, 0);
        if (Math.abs(myAngularPos) > 10) {
            int adjustment = myAngularPos > 10 ? -10 : 10;
            myAngularPos += adjustment;
            myOldAngularPos += adjustment;
        }
    }

    /**
     * Rotates the rigid body about its center of mass by some radian.
     * @param theRadians the degree
     */
    private void rotateBody(final double theRadians) {
        for (final Vector2 v : myVertices) {
            VMath.rotate(v, myPosition, theRadians);
        }
        myAngularPos += theRadians;
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

    public Vector2 getLinearVelocity() {
        return myPosition.subNew(myOldPosition);
    }

    public double getAngularVelocity() {
        return myAngularPos - myOldAngularPos;
    }

    public Vector2 getLinearAngularVelocity(final Vector2 thePoint) {
        return thePoint.subNew(myPosition).perpNew().mulNew(getAngularVelocity());
    }

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
    public Vector2 getCenterOfMass() {
        return myPosition;
    }

    public double getMass() {
        return myMass;
    }

    public double getMoi() {
        return myMoi;
    }

    public boolean hasPhysics() {
        return hasPhysics;
    }
}
