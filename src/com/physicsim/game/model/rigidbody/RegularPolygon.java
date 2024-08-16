package com.physicsim.game.model.rigidbody;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

/**
 * Regular polygon is a polygon whose side lengths and angles are the same.
 */
public class RegularPolygon extends RigidBody {
    /**
     * Creates a regular polygon rigid body given the origin and the number of sides and its side length.
     * For a regular polygon the origin is the center of mass. Note: mass is uniformly distributed.
     *
     * @param theOrigin     the centroid
     * @param theNumSides   the number of sides
     * @param theSideLength the length of a side
     * @param theMass       the mass
     * @throws IllegalArgumentException if it is not a convex 2d polygon that have 3 or more vertices
     */
    public RegularPolygon(final Vector2 theOrigin, final int theNumSides, final double theSideLength, final double theMass) {
        super(theOrigin, theMass, RegularPolygon.generateRegularPolygon(theOrigin, theNumSides, theSideLength));
    }

    @Override
    public <V> V accept(final GameObjectVisitor<V> theV) {
        return theV.visit(this);
    }

    /**
     * Generates the positions of the vertices given an origin and number of sides and its length.
     *
     * @param theOrigin   the center of the polygon
     * @param theNumSides the number of sides (cannot be less than 3)
     * @param theLength   the side length
     * @return the coordinates of the vertices
     */
    private static Vector2[] generateRegularPolygon(final Vector2 theOrigin, final int theNumSides, final double theLength) {
        final Vector2[] vertices = new Vector2[theNumSides];
        final double ang = Math.PI / theNumSides;
        // radius formula
        final double radius = theLength / (2 * Math.sin(ang));
        double angleIncrement = 2 * ang;

        for (int i = 0; i < theNumSides; i++) {
            final double angle = i * angleIncrement;

            // Calculate x and y coordinates based on the angle
            final double x = theOrigin.getX() + radius * Math.cos(angle);
            final double y = theOrigin.getY() + radius * Math.sin(angle);

            vertices[i] = new Vector2(x, y);
        }

        return vertices;
    }
}
