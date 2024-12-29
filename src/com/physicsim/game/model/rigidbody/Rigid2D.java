package com.physicsim.game.model.rigidbody;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.GameWorld;
import com.physicsim.game.utility.Matrix;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.utility.Vector3;

public abstract class Rigid2D extends GameObject {
    protected final Vector3 myTransform;
    protected final Vector3 myVelocity;
    protected final Matrix myMassMatrix;

    /** Whether physics applies to this body. Distinguishes static bodies.*/
    protected boolean hasDynamics;
    /** Whether gravity applies to this body. True by default.*/
    protected boolean hasGravity;

    public Rigid2D(final Vector2 theCenterOfMass, final double theMass) {
        myTransform = new Vector3(theCenterOfMass);
        myVelocity = new Vector3();
        myMassMatrix = new Matrix(3, theMass);
        hasGravity = true;
    }

    public void applyGravity() {
        myVelocity.add(GameWorld.GRAVITY);
    }

    public void applyImpulse(final Vector3 theImpulse) {
        myVelocity.add(theImpulse);
    }

    @Override
    public void update() {
        if (!hasDynamics) return;
        if (hasGravity) applyGravity();

        move();
    }

    /**
     * Updates the object using semi-implicit euler. Assumes dt = 1.
     */
    abstract void move();

    /**
     * Gets the center of mass of the rigid body.
     * @return the center vector
     */
    public Vector3 getTransform() {
        return myTransform;
    }

    public Vector3 getVelocity() {
        return myVelocity;
    }

    public Matrix getInverseMassMatrix() {
        return myMassMatrix.inverseDiagonal();
    }

    /**
     * Enables/Disables the dynamics of this body. By Default the dynamics are disabled. When disabled, it will be
     * treated as a static object that can still be interacted with.
     * @param theDynamics sets the physics
     */
    public void setDynamics(final boolean theDynamics) {
        hasDynamics = theDynamics;
    }

    /**
     * Gets whether this body has physics
     * @return the physics
     */
    public boolean hasDynamics() {
        return hasDynamics;
    }
}
