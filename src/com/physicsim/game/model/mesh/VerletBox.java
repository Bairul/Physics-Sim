package com.physicsim.game.model.mesh;

import com.physicsim.game.controller.input.ClickType;
import com.physicsim.game.controller.input.InputController;
import com.physicsim.game.model.*;
import com.physicsim.game.model.particle.Particle;
import com.physicsim.game.model.particle.Binding;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

/**
 * Creates a box mesh that obeys physics using Verlet points and sticks.
 *
 * @author Bairu Li
 */
public class VerletBox extends GameObject {
    /** The center of gravity. */
    private final Particle myCenter;
    /** The array of vertices of the box. */
    private final Particle[] myVertices;
    /** The array of edges of the perimeter of the box. */
    private final Binding[] myOuterEdges;
    /** The array of edges of inside the box. */
    private final Binding[] myInnerEdges;
    /** The point used to anchor the box. */
    private Particle myAnchor;
    /** The stick used to anchor to the center. */
    private Binding myAnchorStick;
    /** The user inputs. */
    private InputController myInputs;

    /**
     * Constructs a box give the x and y and the side length of the box with its mass.
     * The x and y parameters would be the top-left corner of the box.
     * @param theX      the x
     * @param theY      the y
     * @param theLength the side length
     * @param theMass   the mass
     */
    public VerletBox(final double theX, final double theY, final double theLength, final double theMass) {
        final Vector2 cache = new Vector2();
        myVertices = new Particle[4];
        myOuterEdges = new Binding[4];
        myInnerEdges = new Binding[6];

        for (int i = 0; i < 4; i++) {
            cache.set(theX + theLength * ((i >> 1) ^ (i % 2)), theY + theLength * (i >> 1));
            myVertices[i] = new Particle(cache, theMass);
        }
        for (int i = 0; i < 4; i++) {
            myOuterEdges[i] = new Binding(myVertices[i % 4], myVertices[(i + 1) % 4]);
        }
        // cross edges for stability and rigidity
        myInnerEdges[0] = new Binding(myVertices[0], myVertices[2]);
        myInnerEdges[1] = new Binding(myVertices[1], myVertices[3]);

        // center
        cache.set(theX + theLength / 2, theY + theLength / 2);
        myCenter = new Particle(cache, theMass);
        myInnerEdges[2] = new Binding(myCenter, myVertices[0]);
        myInnerEdges[3] = new Binding(myCenter, myVertices[1]);
        myInnerEdges[4] = new Binding(myCenter, myVertices[2]);
        myInnerEdges[5] = new Binding(myCenter, myVertices[3]);
    }

    /**
     * Checks if a vector point is within the box. Uses linear interpolation for ray tracing.
     * @param thePoint the point vector
     * @return if the box contains the point
     */
    public boolean overlaps(final Vector2 thePoint) {
//        int count = 0;
//        for (final VerletStick e : myOuterEdges) {
//            double x = e.getStartPoint().getPosition().getX();
//            double y = e.getStartPoint().getPosition().getY();
//            if (thePoint.getX() < x != thePoint.getX() < e.getEndPoint().getPosition().getX()
//                    && thePoint.getY() < y + (thePoint.getX() - x) * e.getStartPoint().getPosition().getSlope(e.getEndPoint().getPosition())) {
//                count++;
//            }
//        }
//        return count % 2 == 1;
        return false;
    }

    /**
     * Adds a input listener/controller to the box.
     * @param theInput the input
     */
    public void addInputListener(final InputController theInput) {
        myInputs = theInput;
    }

    /**
     * Gets the box vertices.
     * @return the array of verlet points
     */
    public Particle[] getVertices() {
        return myVertices;
    }

    /**
     * Gets the box edges.
     * @return the array of verlet sticks
     */
    public Binding[] getEdges() {
        return myOuterEdges;
    }

    /**
     * Handles when the mouse clicks and drags on this box.
     */
    private void handleMouseClick() {
        if (myInputs == null) return;

        // let go of the anchor when the mouse is not being held down
        if (!myInputs.getMouse().isButtonHeld(ClickType.LeftClick)) {
            myAnchor = null;
            myAnchorStick = null;
            return;
        }

        // this is reached when the mouse is currently held down
        if (myAnchor == null) {
            // create and put an anchor on the box if the mouse is within the box
            if (overlaps(myInputs.getMousePos())) {
                myAnchor = new Particle(myInputs.getMousePos(), 1, true);

                // change the center point and the center to corner sticks of the box to the mouse
                myCenter.getPosition().set(myInputs.getMousePos());
                for (int i = 2; i < myInnerEdges.length; i++) {
                    myInnerEdges[i].updateDistance();
                }
                myAnchorStick = new Binding(myAnchor, myCenter, 1);
            }
        } else {
            // if an anchor already exists, just update its position to the mouse
            myAnchor.getPosition().set(myInputs.getMousePos());
            myAnchor.update();
            myAnchorStick.update();
        }
    }

    /**
     * Apply a force to each of the vertices of the box.
     * @param theForce the force to apply
     */
    public void applyUniformForce(final Vector2 theForce) {
        for (final Particle p : myVertices) {
            p.applyForce(theForce);
        }
    }

    /**
     * Updates the mesh by updating the vertices first then edges.
     */
    @Override
    public void update() {
        for (final Particle p : myVertices) {
            p.applyForce(GameWorld.GRAVITY.mulNew(p.getMass()));
            p.bounceOffBoundary(GameWorld.SCREEN_BOUNDARY);
            p.update();
        }
        myCenter.update();
        for (final Binding s : myOuterEdges) {
            s.update();
        }
        for (final Binding s : myInnerEdges) {
            s.update();
        }

        handleMouseClick();
    }

    @Override
    public <V> V accept(GameObjectVisitor<V> v) {
        return v.visit(this);
    }
}
