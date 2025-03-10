package tetris.gui;

import tetris.model.GameField;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TetrisKeyListener extends KeyAdapter {
    private final GameField gameField;
    private final GameScreen.GamePanel gamePanel;

    public TetrisKeyListener(GameField gameField, GameScreen.GamePanel gamePanel) {
        this.gameField = gameField;
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            gameField.togglePause();
            return;
        }

        if (gameField.getPause()) return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> gameField.moveLeft();
            case KeyEvent.VK_D -> gameField.moveRight();
            case KeyEvent.VK_W -> gameField.tryRotateCW();
            case KeyEvent.VK_S -> gameField.moveDown();
            case KeyEvent.VK_SPACE -> gameField.hardDrop();
            case KeyEvent.VK_SHIFT -> gameField.hold();
        }

        gamePanel.repaint();
    }
}
