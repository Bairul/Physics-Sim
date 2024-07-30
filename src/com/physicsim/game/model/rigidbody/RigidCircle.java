package com.physicsim.game.model.rigidbody;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

public class RigidCircle extends GameObject {
    private final Vector2 myOrigin;
    private final double myRadius;
    public RigidCircle(final Vector2 theOrigin, final double theRadius) {
        myOrigin = new Vector2(theOrigin);
        myRadius = theRadius;
    }
    @Override
    public void update() {

    }

    public void collideAgainst(final VerletObject theVO) {
        if (theVO.getOldPosition().getDistance(myOrigin) == myRadius) return;

        final Vector2[] intersections = VMath.intersect(theVO.getOldPosition(), theVO.getPosition(), myOrigin, myRadius);

        if (intersections.length < 1) return;
        if (intersections.length < 2) {
            final Vector2 tanVector = new Vector2();
            try {
                final double m = VMath.slope(myOrigin, intersections[0]);
                if (m == 0) {
                    // horizontal slope (intersection point is directly left/right of center)
                    tanVector.set(intersections[0].getX(), intersections[0].getY() + 1);
                } else {
                    double tangent = -1 / m;
                    tanVector.set(intersections[0].getX() + 1, intersections[0].getY() + tangent);
                }
            } catch (final ArithmeticException e) {
                // vertical slope (intersection point is directly top/bot of center)
                tanVector.set(intersections[0].getX() + 1, intersections[0].getY());
            }

            theVO.getPosition().set(VMath.reflect(intersections[0], tanVector, theVO.getPosition()));
            theVO.getOldPosition().set(VMath.reflect(intersections[0], tanVector, theVO.getOldPosition()));
        } else {
            //TODO: find the closest to the old position and reflect over it
        }
    }

    public double getRadius() {
        return myRadius;
    }

    public Vector2 getOrigin() {
        return myOrigin;
    }

    @Override
    public <V> V accept(final GameObjectVisitor<V> v) {
        return v.visit(this);
    }
}
