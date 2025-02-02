package tetris.model;

public enum TetrominoType {
    I(new boolean[][]{
            {false, false, false, false},
            {true, true, true, true},
            {false, false, false, false},
            {false, false, false, false}
    }, 1),

    J(new boolean[][]{
            {true, false, false},
            {true, true, true},
            {false, false, false}
    }, 2),

    T(new boolean[][]{
            {false, true, false},
            {true, true, true},
            {false, false, false}
    }, 3),

    Z(new boolean[][]{
            {true, true, false},
            {false, true, true},
            {false, false, false}
    }, 4),

    L(new boolean[][]{
            {false, false, true},
            {true, true, true},
            {false, false, false}
    }, 5),

    O(new boolean[][]{
            {true, true},
            {true, true}
    }, 6),

    S(new boolean[][]{
            {false, true, true},
            {true, true, false},
            {false, false, false}
    }, 7);

    private final boolean[][] shape;
    private final int colorCode;

    TetrominoType(boolean[][] shape, int colorCode) {
        this.shape = shape;
        this.colorCode = colorCode;
    }

    public boolean[][] getShape() {
        return this.shape;
    }

    public int getColorCode() {
        return this.colorCode;
    }

}
