package com.physicsim.game.model.rigidbody;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

public class Box extends RigidBody {
    public Box(final Vector2 theOrigin, final double width, final double height, final double theMass) {
        super(theMass,
                new Vector2(theOrigin),
                theOrigin.addNew(new Vector2(width, 0)),
                theOrigin.addNew(new Vector2(width, height)),
                theOrigin.addNew(new Vector2(0, height)));
    }

    @Override
    public <V> V accept(GameObjectVisitor<V> theV) {
        return theV.visit(this);
    }
}