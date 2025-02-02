package tetris.model;

import java.awt.*;
import java.util.List;

public class GameField {
    public static final int BOARD_HEIGHT = 22;
    public static final int BOARD_WIDTH = 10;

    private final int[][] board = new int[BOARD_HEIGHT][BOARD_WIDTH];
    private final TetrominoBag bag = new TetrominoBag();
    private Tetromino currentPiece;
    private TetrominoType holdPiece = null;
    private boolean holdOccurred = false;
    private boolean pause = false;

    private final TetrominoController tetrominoController = new TetrominoController(this);
    private final ScoreManager scoreManager = new ScoreManager();

    public GameField() {
        spawnPiece(bag.getNextPiece());
    }

    public boolean tick() {
        return tetrominoController.tick();
    }

    public boolean spawnPiece(TetrominoType type) {
        Point startPoint = (type == TetrominoType.O) ? new Point(4, 0) : new Point(3, 0);
        Tetromino tetromino = new Tetromino(type, startPoint);
        if (!tetrominoController.canFit(tetromino, startPoint)) {
            return false;
        }
        currentPiece = tetromino;
        holdOccurred = false;
        return true;
    }

    public void checkClears() {
        int clears = 0;
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            boolean fullRow = true;
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col] == 0) {
                    fullRow = false;
                    break;
                }
            }
            if (fullRow) {
                clears++;
                clearRow(row);
            }
        }
        scoreManager.updateScore(clears);
    }

    private void clearRow(int clearRow) {
        if (clearRow < 0 || clearRow >= BOARD_HEIGHT) {
            throw new IllegalArgumentException("Trying to clear row out of bounds");
        }
        for (int row = clearRow; row > 0; row--) {
            System.arraycopy(board[row - 1], 0, board[row], 0, BOARD_WIDTH);
        }
        for (int col = 0; col < BOARD_WIDTH; col++) {
            board[0][col] = 0;
        }
    }

    public void hold() {
        if (!holdOccurred) {
            if (holdPiece == null) {
                holdPiece = currentPiece.getType();
                spawnPiece(bag.getNextPiece());
            } else {
                TetrominoType newPieceType = holdPiece;
                holdPiece = currentPiece.getType();
                spawnPiece(newPieceType);
            }
            holdOccurred = true;
        }
    }

    public void togglePause() {
        pause = !pause;
    }

    public void moveLeft() {
        tetrominoController.moveLeft();
    }

    public void moveRight() {
        tetrominoController.moveRight();
    }

    public boolean moveDown() {
        return tick();
    }

    public void tryRotateCW() {
        tetrominoController.tryRotateCW();
    }

    public void hardDrop() {
        tetrominoController.hardDrop();
    }

    public int[][] getBoard() {
        return board;
    }

    public Tetromino getCurrentPiece() {
        return currentPiece;
    }

    public TetrominoType getHoldPiece() {
        return holdPiece;
    }

    // Get score-related information via ScoreManager.
    public int getScore() {
        return scoreManager.getScore();
    }

    public int getLevel() {
        return scoreManager.getLevel();
    }

    public List<TetrominoType> getNext(int count) {
        return bag.previewNext(count);
    }

    public boolean getPause() {
        return pause;
    }

    public int getBoardHeight() {
        return BOARD_HEIGHT;
    }

    public int getBoardWidth() {
        return BOARD_WIDTH;
    }

    public Tetromino getGhostPiece() {
        return tetrominoController.getGhostPiece();
    }

    public TetrominoType getNextPiece() {
        return bag.getNextPiece();
    }
}
