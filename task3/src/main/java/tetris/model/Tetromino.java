package tetris.model;

import java.awt.*;

public class Tetromino {
    private final TetrominoType type;
    private boolean[][] shape;
    private Point pos;

    public Tetromino(TetrominoType type, Point pos) {
        this.type = type;
        this.shape = type.getShape();
        this.pos = pos;
    }

    public Tetromino(TetrominoType type, boolean[][] shape, Point pos) {
        this.type = type;
        this.shape = shape;
        this.pos = pos;
    }

    public boolean[][] getShape() {
        return shape;
    }

    public TetrominoType getType() {
        return type;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(Point newPosition) {
        this.pos = newPosition;
    }

    public boolean[][] getRotatedCW() {
        int rows = shape.length;
        int cols = shape[0].length;
        boolean[][] rotated = new boolean[cols][rows];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                rotated[col][rows - 1 - row] = shape[row][col];
            }
        }
        return rotated;
    }

    public void rotateCW() {
        shape = getRotatedCW();
    }
}
