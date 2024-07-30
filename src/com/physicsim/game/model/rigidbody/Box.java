package com.physicsim.game.model.rigidbody;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

public class Box extends RigidBody {

    public Box(final Vector2 theOrigin, final double theLength) {
        super(new Vector2(theOrigin),
                theOrigin.addNew(new Vector2(theLength, 0)),
                theOrigin.addNew(new Vector2(theLength, theLength)),
                theOrigin.addNew(new Vector2(0, theLength)));
    }
    @Override
    public void update() {

    }

    @Override
    public <V> V accept(final GameObjectVisitor<V> theV) {
        return theV.visit(this);
    }
}
