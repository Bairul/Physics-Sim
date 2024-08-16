package com.physicsim.game.model.rigidbody;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

/**
 * Class for a rigid circle physics game object.
 */
public class RigidCircle extends Rigid2D {
    /** The radius of the circle. */
    private final double myRadius;

    /**
     * Constructs a circle given the origin and the radius.
     * @param theOrigin the origin
     * @param theRadius the radius (must be positive)
     * @throws IllegalArgumentException if radius is a non-positive
     */
    public RigidCircle(final Vector2 theOrigin, final double theRadius, final double theMass) {
        super(theOrigin, theMass);
        if (theRadius <= 0) throw new IllegalArgumentException("radius must be positive");

        myRadius = theRadius;
        myMoi = myRadius * myRadius * myMass / 2;
    }

    @Override
    public void translate(final Vector2 theTranslation) {
        myPosition.add(theTranslation);
        myOldPosition.add(theTranslation);
    }

    @Override
    public void rotate(final double theOrientation) {
        myAngularPos += theOrientation;
        myOldAngularPos += theOrientation;
    }

    @Override
    protected void move() {
        // linear movement
        myCache.set(myPosition);
        myPosition.add(myPosition.subNew(myOldPosition).addNew(myAcceleration));
        myOldPosition.set(myCache);

        // angular movement
        double current = myAngularPos;
        myAngularPos += myAngularPos - myOldAngularPos + myAngularAccel;
        myOldAngularPos = current;
    }
    /**
     * Gets the radius of the circle.
     * @return the radius
     */
    public double getRadius() {
        return myRadius;
    }

    /**
     * Gets the diameter of the circle as an integer. Useful for graphics.
     * @return the diameter
     */
    public int getDiameter() {
        return Math.round((float) myRadius * 2F);
    }

    /**
     * Gets the orientation (the angular position) of the rigid body.
     * @return the orientation in radians
     */
    public Vector2 getOrientationVector() {
        myCache.set(myRadius, 0);
        VMath.rotate(myCache, new Vector2(), myAngularPos);
        return myPosition.addNew(myCache);
    }

    @Override
    public String getName() {
        return "RigidCircle";
    }

    @Override
    public <V> V accept(final GameObjectVisitor<V> theV) {
        return theV.visit(this);
    }
}
