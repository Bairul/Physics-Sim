package com.survival.game.visitor;

import com.survival.game.model.TempEntity;
import com.survival.game.utility.Vector2;

import java.awt.Color;
import java.awt.Graphics;

public class GameObjectRenderer extends GameObjectVisitor<Void> {
    private Graphics myGraphics;
    private final Vector2 myOrigin;
    public GameObjectRenderer(final Vector2 theOrigin) {
        myOrigin = new Vector2(theOrigin);
    }

    public void updateGraphics(final Graphics theG) {
        myGraphics = theG;
    }

    @Override
    public Void visit(TempEntity theEntity) {
        int x = myOrigin.intX() + theEntity.getPosition().intX();
        int y = myOrigin.intY() + theEntity.getPosition().intY();
        myGraphics.setColor(Color.black);
        myGraphics.fillOval(x - theEntity.getSize() / 2,y - theEntity.getSize() / 2, theEntity.getSize(), theEntity.getSize());
        myGraphics.setColor(Color.yellow);
        myGraphics.fillOval(x - theEntity.getSize() / 2 + 1,y - theEntity.getSize() / 2 + 1, theEntity.getSize() - 2, theEntity.getSize() - 2);
        return null;
    }
}
