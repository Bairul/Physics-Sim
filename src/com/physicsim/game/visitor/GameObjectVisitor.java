package com.physicsim.game.visitor;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.VerletPoint;
import com.physicsim.game.model.VerletStick;
import com.physicsim.game.model.mesh.VerletBox;

/**
 * A (GameObject) visitor implements a function F for which objects belonging to the GameObject
 * class hierarchy can be used as inputs to generate outputs. Concrete examples of visitors
 * include:
 * <ul>
 *     <li>RenderingVisitor: applies a draw() method on every object</li>
 *     <li>SoundEffectVisitor: checks each object, plays sound effects based on object state</li>
 *     <li>DebuggingVisitor: applies a debug version of draw() on every object</li>
 * </ul>
 * none of the functions in the above example visitors would return any values (void) so the
 * class should implement with type {@link Void}. The visitor provides a set of overloaded
 * methods named {@link #visit} which is the universal name for generic F.
 *
 * @param <V> the return type of the extension
 *
 */
public abstract class GameObjectVisitor<V> {
    public V visit(final GameObject theDefault) {
        return null;
    }
    public V visit(final VerletPoint theEntity) {
        return visit((GameObject) theEntity);
    }
    public V visit(final VerletStick theEntity) {
        return visit((GameObject) theEntity);
    }
    public V visit(final VerletBox theEntity) {
        return visit((GameObject) theEntity);
    }
}
