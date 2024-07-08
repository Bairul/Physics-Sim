package com.physicsim.game.model;

public class VerletEdge extends VerletStick {

    public VerletEdge(VerletPoint thePointStart, VerletPoint thePointEnd, double theStrength) {
        super(thePointStart, thePointEnd, theStrength);
    }

    public VerletEdge(VerletPoint thePointStart, VerletPoint thePointEnd) {
        super(thePointStart, thePointEnd);
    }

}
