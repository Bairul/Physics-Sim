package com.physicsim.game.model.particle;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;

/**
 * This class uses verlet integration for the movement of game objects.
 *
 * @author Bairu Li
 */
public abstract class VerletObject extends GameObject {
    /** The previous position vector. */
    protected final Vector2 myPosition;
    /** The previous position vector. */
    protected final Vector2 myOldPosition;
    /** The NET acceleration vector. */
    protected final Vector2 myAcceleration;
    /** The Net impulse vector. */
    private final Vector2 myImpulse;
    /** A reusable vector to save some memory. */
    private final Vector2 myCache;
    /** The radius of the object. */
    protected double myRadius;
    /** The mass of the object. */
    protected double myMass;
    /** Whether physics applies to this body. Distinguishes static bodies.*/
    protected boolean hasDynamics;

    /**
     * Creates a game object given the initial position and mass and radius.
     * The set radius does not contribute to any ball-like rigid body collisions, so
     * it is only useful visually.
     *
     * @param thePosition position vector
     * @param theMass     mass
     * @param theRadius   radius of the object, can't be less than 1
     */
    public VerletObject(final Vector2 thePosition, final double theMass, final double theRadius) {
        super();
        if (theMass <= 0) throw new IllegalArgumentException("Mass must be positive");
        if (theRadius < 1) throw new IllegalArgumentException("Radius less than 1");
        // vectors
        myPosition = new Vector2(thePosition);
        myOldPosition = new Vector2(thePosition);
        myAcceleration = new Vector2();
        myImpulse = new Vector2();
        myCache = new Vector2();

        myMass = theMass;
        myRadius = theRadius;
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
     * Accumulates the net force acting on this body.
     * @param theForce the force as a vector to apply
     */
    public void applyForce(final Vector2 theForce) {
        // F = MA --> A = F / M
        myCache.set(theForce);
        myCache.div(myMass);
        myAcceleration.add(myCache); // add to NET acceleration
    }

    /**
     * Accumulates net impulse on this rigid body. Impulse is instantaneous force. This is mainly
     * used to resolve bodies that are already overlapping.
     * @param theImpulseMag the impulse magnitude
     * @param theDirection  the direction of the impulse
     */
    public void applyImpulse(final double theImpulseMag, final Vector2 theDirection) {
        myImpulse.add(theDirection.mulNew(theImpulseMag));
    }

    public void translate(final Vector2 theTranslation) {
        myPosition.add(theTranslation);
        myOldPosition.add(theTranslation);
    }

    /**
     * Updates the object using verlet integration. Assumes dt = 1.
     */
    protected void move() {
        myCache.set(myPosition);
        myPosition.add(myPosition.subNew(myOldPosition).addNew(myAcceleration));
        myOldPosition.set(myCache);
    }

    /**
     * Update before everything else. Used for impulses.
     */
    protected void preMove() {
        // impulsive movement
        if (myImpulse.getX() != 0D || myImpulse.getY() != 0D) {
            setVelocity(getVelocity().addNew(myImpulse.divNew(myMass)));
        }
    }

    /**
     * Update after everything else. Used to reset fields.
     */
    protected void postMove() {
        myAcceleration.set(0, 0);
        myImpulse.set(0, 0);
    }

    @Override
    public void update() {
        if (!hasDynamics) return;
        preMove();
        move();
        postMove();
    }

    /**
     * Sets the velocity of the object. Velocity in verlet integration is done by using changing the old
     * position vector.
     * @param theVelocity the velocity vector
     */
    public void setVelocity(final Vector2 theVelocity) {
        myCache.set(myPosition);
        myCache.sub(theVelocity);
        myOldPosition.set(myCache);
    }

    /**
     * Gets the velocity of the object by subtracting the current and old position.
     * @return the velocity vector
     */
    public Vector2 getVelocity() {
        return myPosition.subNew(myOldPosition);
    }

    /**
     * Gets the position: the x and y coordinate of the object.
     * @return the position vector
     */
    public Vector2 getPosition() {
        return myPosition;
    }

    /**
     * Gets the old position: the x and y coordinate of the object previously.
     * @return the old position vector
     */
    public Vector2 getOldPosition() {
        return myOldPosition;
    }

    /**
     * Gets the object's mass.
     * @return the mass
     */
    public double getMass() {
        return myMass;
    }

    /**
     * Gets the radius of the object.
     * @return the radius as a int
     */
    public int getRadius() {
        return (int) myRadius;
    }

    /**
     * Gets the diameter.
     * @return the diameter as a int
     */
    public int getDiameter() {
        return (int) (myRadius * 2);
    }

    /**
     * Gets whether this body has physics
     * @return the physics
     */
    public boolean hasDynamics() {
        return hasDynamics;
    }

    @Override
    public String getName() {
        return "VerletObject";
    }
}
