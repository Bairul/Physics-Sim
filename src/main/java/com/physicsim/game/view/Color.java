package com.physicsim.game.view;

/**
 * Custom color class because TeaVm cannot use awt.
 *
 * @author Bairu Li
 */
public final class Color {
    private Color() {}
    public static final String BLACK = "black";
    public static final String WHITE = "white";
    public static final String RED = "red";
    public static final String GREEN = "green";
    public static final String BLUE = "blue";
    public static final String ORANGE = "orange";
    public static final String MAGENTA = "magenta";
    public static final String CYAN = "cyan";
    public static final String YELLOW = "yellow";

    public static String rgb(final int red, final int green, final int blue) {
        return "rgb(" + red + "," + green + "," + blue + ")";
    }
}
