package tetris.model;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameField {
    public static final int BOARD_HEIGHT = 20;
    public static final int BOARD_WIDTH = 10;
    private static final Point[] DEFAULT_WALLKICK_OFFSETS =  {
        new Point(0, 0),
        new Point(-1, 0),
        new Point(1, 0),
        new Point(0, -1)
    };
    private static final Point[] I_PIECE_WALLKICK_OFFSETS =  {
            new Point(0, 0),
            new Point(-2, 0),
            new Point(2, 0),
            new Point(0, -1),
            new Point(0, -2)
    };

    private int[][] board = new int[BOARD_HEIGHT][BOARD_WIDTH];
    private TetrominoBag bag = new TetrominoBag();
    private Tetromino currentPiece;
    private Tetromino ghostPiece;
    private TetrominoType holdPiece = null;
    private boolean holdOccurred = false;
    private int score = 0;
    private int level = 1;
    private int linesCleared = 0;
    private boolean pause = false;

    public GameField() {
        spawnPiece(bag.getNextPiece());
    }

    private void placePieceOnBoard(Tetromino tetromino) {
        Point pos = tetromino.getPos();
        boolean[][] shape = tetromino.getShape();
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col]) {
                    int boardX = pos.x + col;
                    int boardY = pos.y + row;
                    board[boardY][boardX] = tetromino.getType().getColorCode();
                }
            }
        }
    }

    private boolean canFit(Tetromino tetromino, Point point) {
        boolean[][] shape = tetromino.getShape();
        int rows = shape.length;
        int cols = shape[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (shape[row][col]) {
                    int boardX = point.x + col;
                    int boardY = point.y + row;
                    if (boardX < 0 || boardX >= BOARD_WIDTH || boardY < 0 || boardY >= BOARD_HEIGHT) {
                        return false;
                    }
                    if (board[boardY][boardX] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean spawnPiece(TetrominoType type) {
        Point startPoint = (type == TetrominoType.O) ? new Point(4, 0) : new Point(3,0);
        Tetromino tetromino = new Tetromino(type, startPoint, false);
        if (!canFit(tetromino, startPoint)) {
            return false;
        }
        currentPiece = tetromino;

        holdOccurred = false;
        updateGhostPiece();

        return true;
    }

    public boolean tick() {
        Point pos = currentPiece.getPos();
        Point newPos = new Point(pos.x, pos.y + 1);

        if (!canFit(currentPiece, newPos)) {
            checkClears();
            placePieceOnBoard(currentPiece);
            return spawnPiece(bag.getNextPiece());
        }

        currentPiece.setPos(newPos);
        updateGhostPiece();
        return true;
    }

    private void clearRow(int clearRow) {
        if (clearRow < 0 || clearRow >= BOARD_HEIGHT) {
            throw new IllegalArgumentException("Trying to clear row out of bounds");
        }
        for (int row = clearRow; row > 0; row--) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = board[row - 1][col];
            }
        }

        for (int col = 0; col < BOARD_WIDTH; col++) {
            board[0][col] = 0;
        }
    }

    private void checkClears() {
        int clears = 0;
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            boolean fullRow = true;
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col] == 0) fullRow = false;
            }
            if (fullRow) {
                clears++;
                clearRow(row);
            }
        }

        switch (clears) {
            case 1:
                score += 40 * level;
            case 2:
                score += 100 * level;
                break;
            case 3:
                score += 300 * level;
                break;
            case 4:
                score += 1200 * level;
                break;
        }
        linesCleared += clears;

        if (linesCleared >= level * 10) {
            level++;
        }
    }

    public void moveLeft() {
        Point pos = currentPiece.getPos();
        Point newPos = new Point(pos.x - 1, pos.y);

        if (canFit(currentPiece, newPos)) {
            currentPiece.setPos(newPos);
            updateGhostPiece();
        }
    }

    public void moveRight() {
        Point pos = currentPiece.getPos();
        Point newPos = new Point(pos.x + 1, pos.y);

        if (canFit(currentPiece, newPos)) {
            currentPiece.setPos(newPos);
            updateGhostPiece();
        }
    }

    public boolean moveDown() {
        return tick();
    }

    public void tryRotateCW() {
        Tetromino rotatedPiece = new Tetromino(currentPiece.getType(), currentPiece.getRotatedCW(), currentPiece.getPos(), currentPiece.isGhost());

        Point pos = currentPiece.getPos();
        Point[] wallKickOffsets = (currentPiece.getType() == TetrominoType.I) ? I_PIECE_WALLKICK_OFFSETS : DEFAULT_WALLKICK_OFFSETS;
        for (Point offset : wallKickOffsets) {
            Point newPos = new Point(pos.x + offset.x, pos.y + offset.y);
            if (canFit(rotatedPiece, newPos)) {
                currentPiece = rotatedPiece;
                currentPiece.setPos(newPos);
                updateGhostPiece();
                return;
            }
        }

    }

    private Point findBottomPos(Tetromino tetromino) {
        Point pos = new Point(tetromino.getPos());
        while (canFit(tetromino, new Point(pos.x, pos.y + 1))) {
            pos.y++;
        }
        return pos;
    }

    public boolean hardDrop() {
        currentPiece.setPos(findBottomPos(currentPiece));
        placePieceOnBoard(currentPiece);
        checkClears();
        return spawnPiece(bag.getNextPiece());
    }

    public boolean hold() {
        if (!holdOccurred) {
            if (holdPiece == null) {
                holdPiece = currentPiece.getType();
                boolean ret = spawnPiece(bag.getNextPiece());
                holdOccurred = true;
                return ret;
            } else {
                TetrominoType newPieceType = holdPiece;
                holdPiece = currentPiece.getType();
                boolean ret = spawnPiece(newPieceType);
                holdOccurred = true;
                return ret;

            }
        }
        return true;
    }

    private void updateGhostPiece() {
        ghostPiece = new Tetromino(currentPiece);
        ghostPiece.setPos(findBottomPos(currentPiece));
        ghostPiece.setGhostType(true);
    }

    public void togglePause() {
        pause = !pause;
    }

    public int[][] getBoard() {
        return board;
    }

    public Tetromino getCurrentPiece() {
        return currentPiece;
    }

    public Tetromino getGhostPiece() {
        return ghostPiece;
    }

    public TetrominoType getHoldPiece() {
        return holdPiece;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public List<TetrominoType> getNext(int count) {
        return bag.previewNext(count);
    }

    public boolean getPause() {
        return pause;
    }
}
