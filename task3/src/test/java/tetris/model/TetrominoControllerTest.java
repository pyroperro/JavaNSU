package tetris.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TetrominoControllerTest {

    private GameField gameField;
    private TetrominoController controller;

    @BeforeEach
    void setUp() {
        gameField = new GameField();
        controller = new TetrominoController(gameField);
    }

    @Test
    void placePieceOnBoard_success() {
        gameField.spawnPiece(TetrominoType.O);
        Tetromino tetromino = gameField.getCurrentPiece();
        tetromino.setPos(new Point(3, 5));
        assertTrue(controller.placePieceOnBoard(tetromino));
    }

    @Test
    void placePieceOnBoard_failsWhenTooHigh() {
        gameField.spawnPiece(TetrominoType.O);
        Tetromino tetromino = gameField.getCurrentPiece();
        tetromino.setPos(new Point(3, 1));
        assertFalse(controller.placePieceOnBoard(tetromino));
    }

    @Test
    void canFit_emptyBoard() {
        Tetromino tetromino = new Tetromino(TetrominoType.T, new boolean[][]{{true}}, new Point(5, 5));
        assertTrue(controller.canFit(tetromino, new Point(5, 5)));
    }

    @Test
    void canFit_blockedPosition() {
        Tetromino tetromino = new Tetromino(TetrominoType.T, new boolean[][]{{true}}, new Point(5, 5));
        gameField.getBoard()[5][5] = 1;
        assertFalse(controller.canFit(tetromino, new Point(5, 5)));
    }

    @Test
    void tick_pieceMovesDown() {
        gameField.spawnPiece(TetrominoType.L);
        Tetromino tetromino = gameField.getCurrentPiece();
        tetromino.setPos(new Point(4, 5));
        assertTrue(controller.tick());
        assertEquals(new Point(4, 6), tetromino.getPos());
    }

    @Test
    void tick_pieceLocksInPlace() {
        gameField.spawnPiece(TetrominoType.L);
        Tetromino tetromino = gameField.getCurrentPiece();
        tetromino.setPos(new Point(4, 18));
    }

    @Test
    void moveLeft_success() {
        gameField.spawnPiece(TetrominoType.J);
        Tetromino tetromino = gameField.getCurrentPiece();
        tetromino.setPos(new Point(5, 10));
        controller.moveLeft();
        assertEquals(new Point(4, 10), tetromino.getPos());
    }

    @Test
    void moveRight_success() {
        gameField.spawnPiece(TetrominoType.S);
        Tetromino tetromino = gameField.getCurrentPiece();
        tetromino.setPos(new Point(5, 10));
        controller.moveRight();
        assertEquals(new Point(6, 10), tetromino.getPos());
    }

    @Test
    void tryRotateCW_success() {
        gameField.spawnPiece(TetrominoType.T);
        Tetromino tetromino = gameField.getCurrentPiece();
        tetromino.setPos(new Point(4, 5));
        controller.tryRotateCW();
        // Add assertions based on expected rotation behavior
    }

    @Test
    void hardDrop_landsImmediately() {
        gameField.spawnPiece(TetrominoType.Z);
        Tetromino tetromino = gameField.getCurrentPiece();
        tetromino.setPos(new Point(4, 0));

        Tetromino ghost = controller.getGhostPiece();
        controller.hardDrop();
        assertEquals(tetromino.getPos(), ghost.getPos());
    }

    @Test
    void getGhostPiece_createsNewInstance() {
        gameField.spawnPiece(TetrominoType.I);
        Tetromino tetromino = gameField.getCurrentPiece();
        tetromino.setPos(new Point(4, 5));
        Tetromino ghost = controller.getGhostPiece();
        assertNotSame(tetromino, ghost);
    }
}
