package com.physicsim.game.view;

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

    public static String rgb(int red, int green, int blue) {
        return "rgb(" + red + "," + green + "," + blue + ")";
    }
}
