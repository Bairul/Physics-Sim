package com.physicsim.game.model;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

/**
 * This class is a way to bind 2 VerletPoints to a fixed distance between them.
 */
public class VerletStick extends GameObject {
    /** The start endpoint. */
    protected final VerletPoint myStart;
    /** The end endpoint. */
    protected final VerletPoint myEnd;
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
    public VerletStick(final VerletPoint thePointStart, final VerletPoint thePointEnd, final double theStrength) {
        myStart = thePointStart;
        myEnd = thePointEnd;
        myLength = thePointStart.getDistance(thePointEnd);
        myCache = new Vector2();
        myStrength = theStrength;
    }

    /**
     * Constructs the stick between 2 Verlet points.
     * @param thePointStart the reference to the start point
     * @param thePointEnd   the reference to the end point
     */
    public VerletStick(final VerletPoint thePointStart, final VerletPoint thePointEnd) {
        myStart = thePointStart;
        myEnd = thePointEnd;
        myLength = thePointStart.getDistance(thePointEnd);
        myCache = new Vector2();
        myStrength = 1;
    }

    /**
     * Gets the starting point.
     * @return the start verlet point
     */
    public VerletObject getStartPoint() {
        return myStart;
    }

    /**
     * Gets the ending point.
     * @return the end verlet point
     */
    public VerletObject getEndPoint() {
        return myEnd;
    }

    /**
     * Re-updates the distance between the 2 points. Useful to deform the stick.
     */
    public void updateDistance() {
        myLength = myStart.getDistance(myEnd);
    }

    /**
     * Maintains the distance between the 2 points.
     */
    @Override
    public void update() {
        double dist = myStart.getDistance(myEnd);
        double diff = myLength - dist;
        double percent = diff / dist / (2 * myStrength);

        myCache.set(myStart);
        myCache.sub(myEnd);
        myCache.mul(percent);

        if (!myStart.isPinned()) myStart.add(myCache);
        if (!myEnd.isPinned()) myEnd.sub(myCache);
    }

    @Override
    public <V> V accept(final GameObjectVisitor<V> v) {
        return v.visit(this);
    }
}
