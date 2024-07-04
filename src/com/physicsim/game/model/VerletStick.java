package com.physicsim.game.model;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

public class VerletStick extends GameObject {
    private final VerletPoint myStart;
    private final VerletPoint myEnd;
    private final Vector2 myCache;
    private final double myLength;

    public VerletStick(final VerletPoint thePointStart, final VerletPoint thePointEnd) {
        myStart = thePointStart;
        myEnd = thePointEnd;
        myLength = thePointStart.getPosition().getDistance(thePointEnd.getPosition());
        myCache = new Vector2();
    }

    public VerletObject getPoint1() {
        return myStart;
    }

    public VerletObject getPoint2() {
        return myEnd;
    }

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
    public <V> V accept(GameObjectVisitor<V> v) {
        return v.visit(this);
    }
}
