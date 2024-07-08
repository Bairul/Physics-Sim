package com.physicsim.game.model;

import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

public class Boundary extends GameObject {

    final VerletEdge[] myEdges;

    /**
     * Creates a boundary shape given an initial x and y. The following vectors will be the sides of the boundary
     * relative to the initial x and y. The last vector will connect with the initial vector to enclose the shape.
     * @param theOrigin the origin
     * @param theBounds the boundaries
     */
    public Boundary(final Vector2 theOrigin, final Vector2... theBounds) {
        if (theBounds.length < 1) throw new IllegalArgumentException("Empty bounds");

        myEdges = new VerletEdge[theBounds.length + 1];
        myEdges[0] = new VerletEdge(
                new VerletPoint(theOrigin, 1, true),
                new VerletPoint(theOrigin.addNew(theBounds[0]), 1, true));

        for (int i = 1; i < theBounds.length; i++) {
            myEdges[i] = new VerletEdge(myEdges[i - 1].getEndPoint(),
                    new VerletPoint(myEdges[i - 1].getEndPoint().getPosition().addNew(theBounds[i]), 1, true));
        }

        myEdges[theBounds.length] = new VerletEdge(myEdges[theBounds.length - 1].getEndPoint(), myEdges[0].getStartPoint());
    }

    /**
     * Checks if a vector point is within the boundary. Uses linear interpolation for ray tracing.
     * @param thePoint the point vector
     * @return if the box contains the point
     */
    public boolean contains(final Vector2 thePoint) {
        boolean count = false;
        for (final VerletEdge myEdge : myEdges) {
            if (myEdge.rayCast(thePoint)) {
                count = !count;
            }
        }
        return count;
    }

    public void handleCollision(final VerletPoint theVp) {
//        for (final VerletEdge edge : myEdges) {
//            if (edge.getIntersect(theVp) != null) {
//                theVp.getPosition().set(edge.reflectPoint(theVp.getPosition()));
//                theVp.getOldPosition().set(edge.reflectPoint(theVp.getOldPosition()));
//                return;
//            }
//        }
    }

    @Override
    public void update() {
    }

    public VerletEdge[] getEdges() {
        return myEdges;
    }

    @Override
    public <V> V accept(GameObjectVisitor<V> v) {
        return v.visit(this);
    }
}
