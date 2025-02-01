package tetris.model;

import java.awt.*;

public enum TetrominoType {
    I(new boolean[][]{
            {false, false, false, false},
            {true, true, true, true},
            {false, false, false, false},
            {false, false, false, false}
    }, Color.CYAN, 1),

    J(new boolean[][]{
            {true, false, false},
            {true, true, true},
            {false, false, false}
    }, Color.BLUE, 2),

    T(new boolean[][]{
            {false, true, false},
            {true, true, true},
            {false, false, false}
    }, Color.MAGENTA, 3),

    Z(new boolean[][]{
            {true, true, false},
            {false, true, true},
            {false, false, false}
    }, Color.RED, 4),

    L(new boolean[][]{
            {false, false, true},
            {true, true, true},
            {false, false, false}
    }, Color.ORANGE, 5),

    O(new boolean[][]{
            {true, true},
            {true, true}
    }, Color.YELLOW, 6),

    S(new boolean[][]{
            {false, true, true},
            {true, true, false},
            {false, false, false}
    }, Color.GREEN, 7);

    private final boolean[][] shape;
    private final Color color;
    private final int colorCode;

    TetrominoType(boolean[][] shape, Color color, int colorCode) {
        this.shape = shape;
        this.color = color;
        this.colorCode = colorCode;
    }

    public boolean[][] getShape() {
        return this.shape;
    }

    public Color getColor() {
        return this.color;
    }

    public int getColorCode() {
        return this.colorCode;
    }

    public Color getColor(int colorCode) {
        for (TetrominoType type : TetrominoType.values()) {
            if (type.getColorCode() == colorCode) {
                return type.getColor();
            }
        }
        return Color.WHITE;
    }

}
