package com.physicsim.game.model;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

/**
 * This class is a way to bind 2 VerletPoints to a fixed distance between them.
 */
public class VerletStick extends GameObject {
    /** The start endpoint. */
    private final VerletPoint myStart;
    /** The end endpoint. */
    private final VerletPoint myEnd;
    /** A reusable vector to save some memory. */
    private final Vector2 myCache;
    /** The fixed distance between the start and end endpoints. */
    private final double myLength;

    /**
     * Constructs the stick between 2 Verlet points.
     * @param thePointStart the reference to the start point
     * @param thePointEnd   the reference to the end point
     */
    public VerletStick(final VerletPoint thePointStart, final VerletPoint thePointEnd) {
        myStart = thePointStart;
        myEnd = thePointEnd;
        myLength = thePointStart.getPosition().getDistance(thePointEnd.getPosition());
        myCache = new Vector2();
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
     * Maintains the distance between the 2 points.
     */
    @Override
    public void update() {
        double dist = myStart.getPosition().getDistance(myEnd.getPosition());
        double diff = myLength - dist;
        double percent = diff / dist / 2;

        myCache.set(myStart.getPosition());
        myCache.sub(myEnd.getPosition());
        myCache.mul(percent);

        if (!myStart.isPinned()) myStart.getPosition().add(myCache);
        if (!myEnd.isPinned()) myEnd.getPosition().sub(myCache);
    }

    @Override
    public <V> V accept(final GameObjectVisitor<V> v) {
        return v.visit(this);
    }
}
