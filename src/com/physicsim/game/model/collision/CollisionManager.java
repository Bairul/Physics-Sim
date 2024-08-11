package com.physicsim.game.model.collision;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.GameWorld;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.model.rigidbody.RigidBodyEdge;
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

//                System.out.println("Dynamic: pt:" + a[0] + ", norm: " + a[1] + ", proj: " + a[2].getX() + ", index: " + a[2].getY());
//                System.out.println("Static: pt:" + b[0] + ", norm: " + b[1] + ", proj: " + b[2].getX() + ", index: " + b[2].getY());

                final int ia = a[2].intY();
                final int ib = b[2].intY();
                final Vector2 projAB = VMath.project(r.getEdges()[ib].getStart(), r.getEdges()[ib].getEnd(), b[0]);
                final Vector2 projBA = VMath.project(theRB.getEdges()[ia].getStart(), theRB.getEdges()[ia].getEnd(), a[0]);

                Vector2 penVector;
                if (projAB == null) {
                    assert projBA != null;
                    penVector = a[0].subNew(projBA);
                    myCollisions.add(new RigidBodyAndRigidBodyCollision(theRB, r, a[0], a[1], penVector));
                } else if (projBA == null) {
                    penVector = projAB.subNew(b[0]);
                    myCollisions.add(new RigidBodyAndRigidBodyCollision(theRB, r, b[0], b[1], penVector));
                } else {
                    final Vector2 vel = theRB.getLinearVelocity();
                    vel.mul(-1);
                    final Vector2 penAB = projAB.subNew(b[0]);
                    final Vector2 penBA = a[0].subNew(projBA);
                    if (vel.dotProduct(penAB) > vel.dotProduct(penBA)) {
                        myCollisions.add(new RigidBodyAndRigidBodyCollision(theRB, r, b[0], b[1], penAB));
                    } else {
                        myCollisions.add(new RigidBodyAndRigidBodyCollision(theRB, r, a[0], a[1], penBA));
                    }
                }
            }
        }
        return true;
    }

    public void update() {
        if (myCollisions.isEmpty()) return;

        for (final Collision c: myCollisions) {
            c.handleCollision();
        }
        myCollisions.clear();
    }
}
