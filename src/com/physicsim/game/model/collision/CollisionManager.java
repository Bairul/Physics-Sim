package com.physicsim.game.model.collision;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.GameWorld;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.model.rigidbody.RigidCircle;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;

import java.util.ArrayList;
import java.util.List;

public final class CollisionManager {
    private final GameWorld myWorld;
    private final List<Collision> myCollisions;
    public CollisionManager(final GameWorld theWorld) {
        myWorld = theWorld;
        myCollisions = new ArrayList<>();
    }

    public void testAndHandle(final VerletObject theVO) {
        for (final GameObject staticObject : myWorld.getStaticObjects()) {
            if (staticObject instanceof final RigidBody r) {
                r.collideAgainst(theVO);
            }
            else if (staticObject instanceof final RigidCircle r) {
                r.collideAgainst(theVO);
            }
        }
    }

    public boolean testAndHandle(final RigidBody theRB) {
        for (final GameObject staticObject : myWorld.getStaticObjects()) {
            if (staticObject instanceof final RigidBody r) {
                final Vector2[] a = VMath.findAxisOfLeastPenetration(theRB, r);
                if (a.length == 0) return false;

                final Vector2[] b = VMath.findAxisOfLeastPenetration(r, theRB);
                if (b.length == 0) return false;

                System.out.println("Dynamic: pt:" + a[0] + ", norm: " + a[1] + ", proj: " + a[2].getX() + ", index: " + a[2].getY());
                System.out.println("Static: pt:" + b[0] + ", norm: " + b[1] + ",proj:  " + b[2].getX() + ", index: " + b[2].getY());

                System.out.println(theRB.getEdges()[a[2].intY()].getEdge().crossProduct(r.getEdges()[b[2].intY()].getEdge()));

                if (Math.abs(a[2].getX() - b[2].getX()) < 0.001) {
                    System.out.println(true);
                    myCollisions.add(new RigidBodyAndRigidBodyCollision(theRB, r, a[0], a[1]));
                    myCollisions.add(new RigidBodyAndRigidBodyCollision(theRB, r, b[0], b[1].mulNew(-1)));
                } else {
                    final boolean isACollision = a[2].getX() > b[2].getX();
                    final Vector2 collisionPoint = isACollision ? a[0] : b[0];
                    final Vector2 collisionNormal = isACollision ? a[1] : b[1];
                    System.out.println("Collision Point: " + collisionPoint + ", Collision Normal: " + collisionNormal);

                    myCollisions.add(new RigidBodyAndRigidBodyCollision(theRB, r, collisionPoint, collisionNormal));
                }
            }
        }
        return true;
    }

    public void update() {
        for (final Collision c: myCollisions) {
            c.handleCollision();
        }
        myCollisions.clear();
    }
}
