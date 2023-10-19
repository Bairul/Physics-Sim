package com.survival.game.visitor;

import com.survival.game.model.GameObject;
import com.survival.game.model.TempEntity;

public abstract class GameObjectVisitor<V> {
    public V visit(final GameObject theDefault) {
        return null;
    }

    public V visit(final TempEntity theEntity) {
        return visit((GameObject) theEntity);
    }
}
