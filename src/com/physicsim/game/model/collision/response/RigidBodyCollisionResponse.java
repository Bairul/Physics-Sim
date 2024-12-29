package com.physicsim.game.model.collision.response;

import com.physicsim.game.model.collision.Manifold;
import com.physicsim.game.model.rigidbody.Rigid2D;
import com.physicsim.game.utility.Matrix;
import com.physicsim.game.utility.Vector3;

public class RigidBodyCollisionResponse extends CollisionResponse {
    private static final double GAMMA = 0.8;

    private final Rigid2D myA;
    private final Rigid2D myB;
    private final Manifold myManifold;
    private final Vector3 myJ;
    private Vector3 myJ_i;
    private double lambda_i;

    public RigidBodyCollisionResponse(final Rigid2D theA, final Rigid2D theB, final Manifold theManifold) {
        myA = theA;
        myB = theB;
        myManifold = theManifold;
        myManifold.getNormal().norm();

        // J = [N | r x N]
        myJ = new Vector3(myManifold.getNormal());
        myJ.setZ(myManifold.getPoint().subNew(myA.getTransform().toVector2()).crossProduct(myManifold.getNormal()));

        myJ_i = new Vector3(myJ);
    }

    @Override
    public void handleResponse() {
        // t = λ_i
        double t = lambda_i;
        // convert Velocity vector3 to matrix column vector for multiplication
        Matrix V = new Matrix(myA.getVelocity(), true);

        // convert J vector3 to matrix row vector for multiplication
        Matrix J = new Matrix(myJ);
        // take J transpose
        Matrix J_T = J.transpose();

        // convert J_i vector3 to matrix row vector for multiplication
        Matrix J_i = new Matrix(myJ_i);
        // take J_i transpose
        Matrix J_i_T = J_i.transpose();

        // effective mass
        // m = J_i * M^-1 * J_i_T, this multiplication should yield a scalar
        double m = J_i.multiply(myA.getInverseMassMatrix()).multiply(J_i_T).toScalar();

        // generalized velocity constraint
        // JV + b, this should yield a scalar
        double jv = J.multiply(V).toScalar();
        // b = (γ/h) * d, d = pen * normal
        double b = GAMMA * myManifold.getPenetration().dotProduct(myManifold.getNormal().mulNew(-1));

        // λ_i = λ_i -(1/m) (jv + b)
        lambda_i = lambda_i - (1/m) * (jv + b);
        lambda_i = Math.max(0, lambda_i);

        // imp = M^-1 * J_T * (λ_i - t)
        Vector3 impulse = myA.getInverseMassMatrix().multiply(J_T).toVector3();
        impulse.mul(lambda_i - t);

        myA.applyImpulse(impulse);
    }
}
