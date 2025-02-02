package tetris.model;

import java.awt.*;

public class TetrominoController {
    private final GameField gameField;

    private static final Point[] DEFAULT_WALLKICK_OFFSETS = {
            new Point(0, 0),
            new Point(-1, 0),
            new Point(1, 0),
            new Point(0, -1)
    };
    private static final Point[] I_PIECE_WALLKICK_OFFSETS = {
            new Point(0, 0),
            new Point(-2, 0),
            new Point(2, 0),
            new Point(0, -1),
            new Point(0, -2)
    };

    public TetrominoController(GameField gameField) {
        this.gameField = gameField;
    }

    public boolean placePieceOnBoard(Tetromino tetromino) {
        Point pos = tetromino.getPos();
        boolean[][] shape = tetromino.getShape();
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col]) {
                    int boardX = pos.x + col;
                    int boardY = pos.y + row;
                    if (boardY < 2) return false;
                    gameField.getBoard()[boardY][boardX] = tetromino.getType().getColorCode();
                }
            }
        }
        gameField.checkClears();
        return gameField.spawnPiece(gameField.getNextPiece());
    }

    public boolean canFit(Tetromino tetromino, Point point) {
        boolean[][] shape = tetromino.getShape();
        int rows = shape.length;
        int cols = shape[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (shape[row][col]) {
                    int boardX = point.x + col;
                    int boardY = point.y + row;
                    if (boardX < 0 || boardX >= gameField.getBoardWidth() ||
                            boardY < 0 || boardY >= gameField.getBoardHeight()) {
                        return false;
                    }
                    if (gameField.getBoard()[boardY][boardX] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean tick() {
        Tetromino currentPiece = gameField.getCurrentPiece();
        Point pos = currentPiece.getPos();
        Point newPos = new Point(pos.x, pos.y + 1);

        if (!canFit(currentPiece, newPos)) {
            gameField.checkClears();
            return placePieceOnBoard(currentPiece);
        }

        currentPiece.setPos(newPos);
        return true;
    }

    public void moveLeft() {
        movePiece(-1, 0);
    }

    public void moveRight() {
        movePiece(1, 0);
    }

    private void movePiece(int dx, int dy) {
        Tetromino piece = gameField.getCurrentPiece();
        Point newPos = new Point(piece.getPos().x + dx, piece.getPos().y + dy);
        if (canFit(piece, newPos)) {
            piece.setPos(newPos);
        }
    }

    public void tryRotateCW() {
        Tetromino currentPiece = gameField.getCurrentPiece();
        Point pos = currentPiece.getPos();
        Point[] wallKickOffsets = (currentPiece.getType() == TetrominoType.I)
                ? I_PIECE_WALLKICK_OFFSETS
                : DEFAULT_WALLKICK_OFFSETS;

        for (Point offset : wallKickOffsets) {
            Point newPos = new Point(pos.x + offset.x, pos.y + offset.y);
            Tetromino rotatedPiece = new Tetromino(currentPiece.getType(), currentPiece.getRotatedCW(), newPos);
            if (canFit(rotatedPiece, newPos)) {
                currentPiece.rotateCW();
                currentPiece.setPos(newPos);
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

    public void hardDrop() {
        Tetromino currentPiece = gameField.getCurrentPiece();
        currentPiece.setPos(findBottomPos(currentPiece));
        placePieceOnBoard(currentPiece);
    }

    public Tetromino getGhostPiece() {
        Tetromino currentPiece = gameField.getCurrentPiece();
        return new Tetromino(currentPiece.getType(), currentPiece.getShape(), findBottomPos(currentPiece));
    }
}
