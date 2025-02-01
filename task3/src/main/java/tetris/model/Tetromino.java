package tetris.model;

import java.awt.*;

public class Tetromino {
    private TetrominoType type;
    private boolean[][] shape;
    private Point pos;
    private boolean ghostType;

    public Tetromino(TetrominoType type, Point pos, boolean isGhost) {
        this.type = type;
        this.shape = type.getShape();
        this.pos = pos;
        this.ghostType = isGhost;
    }

    public Tetromino(TetrominoType type, boolean[][] shape, Point pos, boolean ghostType) {
        this.type = type;
        this.shape = shape;
        this.pos = pos;
        this.ghostType = ghostType;
    }

    public Tetromino(Tetromino other) {
        this(other.type, other.shape, other.pos, other.ghostType);
    }

    public boolean[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return this.type.getColor();
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
        this.shape = getRotatedCW();
    }

    public boolean isGhost() {
        return ghostType;
    }

    public void setGhostType(boolean ghostType) {
        this.ghostType = ghostType;
    }
}
