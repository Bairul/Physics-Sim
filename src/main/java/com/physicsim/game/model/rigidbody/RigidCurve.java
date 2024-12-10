//package com.physicsim.game.model.rigidbody;
//
//import com.physicsim.game.model.GameObject;
//import com.physicsim.game.utility.VMath;
//import com.physicsim.game.utility.Vector2;
//import com.physicsim.game.visitor.GameObjectVisitor;
//
//public class RigidCurve extends GameObject {
//    final RigidBodyEdge myStartEdge;
//    final RigidBodyEdge myEndEdge;
//    final Vector2 myInCenter;
//    final double myInCircleRadius;
//    public RigidCurve(final Vector2 theStartPoint, final Vector2 theMidPoint, final Vector2 theEndPoint) {
//        double distA = theStartPoint.getDistance(theMidPoint);
//        double distB = theMidPoint.getDistance(theEndPoint);
//        double distC = theEndPoint.getDistance(theStartPoint);
//        double sum = distA + distB + distC;
//        double area = Math.abs(
//                theStartPoint.getX() * (theMidPoint.getY() - theEndPoint.getY())
//                + theMidPoint.getX() * (theEndPoint.getY() - theStartPoint.getY())
//                + theEndPoint.getX() * (theStartPoint.getY() - theMidPoint.getY()));
//
//        myInCircleRadius = area / sum;
//        myInCenter = new Vector2(
//                (distA * theStartPoint.getX() + distB * theMidPoint.getX() + distC * theEndPoint.getX()) / sum,
//                (distA * theStartPoint.getY() + distB * theMidPoint.getY() + distC * theEndPoint.getY()) / sum);
//
//        final Vector2 startEdgeTan = VMath.project(theStartPoint, theMidPoint, myInCenter);
//        final Vector2 endEdgeTan = VMath.project(theMidPoint, theEndPoint, myInCenter);
//
//        myStartEdge = new RigidBodyEdge(startEdgeTan, new Vector2(theStartPoint));
//        myEndEdge = new RigidBodyEdge(endEdgeTan, new Vector2(theEndPoint));
//    }
//
//    @Override
//    public void update() {
//
//    }
//
//    @Override
//    public <V> V accept(final GameObjectVisitor<V> v) {
//        return null;
//    }
//}
