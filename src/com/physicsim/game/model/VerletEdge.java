package com.physicsim.game.model;

import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;

public class VerletEdge extends VerletStick {

    public VerletEdge(VerletPoint thePointStart, VerletPoint thePointEnd, double theStrength) {
        super(thePointStart, thePointEnd, theStrength);
    }

    public VerletEdge(VerletPoint thePointStart, VerletPoint thePointEnd) {
        super(thePointStart, thePointEnd);
    }

    public boolean rayCast(final Vector2 thePoint) {
        return thePoint.getX() < myStart.getPosition().getX() != thePoint.getX() < myEnd.getPosition().getX()
                && thePoint.getY() < VMath.lerpY(myStart.getPosition(), myEnd.getPosition(), thePoint.getX());
    }

    private Vector2 getIntersect(final VerletPoint theVp) {
        return VMath.intersect(myStart.getPosition(), myEnd.getPosition(), theVp.getPosition(), theVp.getOldPosition());
    }

    private Vector2 reflectPoint(final Vector2 thePoint) {
        return VMath.reflect(myStart.getPosition(), myEnd.getPosition(), thePoint);
    }

    public void handleCollision(final VerletPoint theVp) {
        if (getIntersect(theVp) != null) {
            theVp.getPosition().set(reflectPoint(theVp.getPosition()));
            theVp.getOldPosition().set(reflectPoint(theVp.getOldPosition()));
        }
    }
}
