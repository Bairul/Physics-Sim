package com.physicsim.game.model.particle;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

/**
 * This class is a way to bind 2 VerletPoints to a fixed distance between them.
 */
public class Binding extends GameObject {
    /** The start endpoint. */
    protected final Particle myStart;
    /** The end endpoint. */
    protected final Particle myEnd;
    /** A reusable vector to save some memory. */
    protected final Vector2 myCache;
    /** The strength of the stick. */
    private final double myStrength;
    /** The fixed distance between the start and end endpoints. */
    private double myLength;

    /**
     * Constructs the stick between 2 Verlet points with strength of the stick (lower is stronger).
     * @param thePointStart the reference to the start point
     * @param thePointEnd   the reference to the end point
     * @param theStrength   the strength of the binding (has to be >= 1)
     */
    public Binding(final Particle thePointStart, final Particle thePointEnd, final double theStrength) {
        myStart = thePointStart;
        myEnd = thePointEnd;
        myLength = thePointStart.getPosition().getDistance(thePointEnd.getPosition());
        myCache = new Vector2();
        myStrength = theStrength;
    }

    /**
     * Constructs the stick between 2 Verlet points.
     * @param thePointStart the reference to the start point
     * @param thePointEnd   the reference to the end point
     */
    public Binding(final Particle thePointStart, final Particle thePointEnd) {
        myStart = thePointStart;
        myEnd = thePointEnd;
        myLength = thePointStart.getPosition().getDistance(thePointEnd.getPosition());
        myCache = new Vector2();
        myStrength = 1;
    }

    /**
     * Gets the starting point.
     * @return the start verlet point
     */
    public Particle getStartPoint() {
        return myStart;
    }

    /**
     * Gets the ending point.
     * @return the end verlet point
     */
    public Particle getEndPoint() {
        return myEnd;
    }

    /**
     * Re-updates the distance between the 2 points. Useful to deform the stick.
     */
    public void updateDistance() {
        myLength = myStart.getPosition().getDistance(myEnd.getPosition());
    }

    /**
     * Maintains the distance between the 2 points.
     */
    @Override
    public void update() {
        final double dist = myStart.getPosition().getDistance(myEnd.getPosition());
        final double diff = myLength - dist;
        final double percent = diff / dist / (2 * myStrength);

        myCache.set(myStart.getPosition());
        myCache.sub(myEnd.getPosition());
        myCache.mul(percent);

        if (!myStart.isPinned()) myStart.getPosition().add(myCache);
        if (!myEnd.isPinned()) myEnd.getPosition().sub(myCache);
    }

    @Override
    public <V> V accept(final GameObjectVisitor<V> theV) {
        return theV.visit(this);
    }
}
