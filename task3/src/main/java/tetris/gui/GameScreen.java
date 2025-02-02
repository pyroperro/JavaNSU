package tetris.gui;

import tetris.model.GameField;
import tetris.model.LeaderboardsHandler;
import tetris.model.Tetromino;
import tetris.model.TetrominoType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameScreen {

    public GameScreen() {
        new GameWindow();
    }

    public static class GameWindow extends JFrame {
        private GameField gameField;
        private GamePanel gamePanel;
        private Timer gameTimer;
        private LeaderboardsHandler scoreHandler;

        public GameWindow() {
            setTitle("Tetris Game");
            setSize(900, 800);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);

            gameField = new GameField();
            gamePanel = new GamePanel(gameField);
            scoreHandler = new LeaderboardsHandler();

            setContentPane(gamePanel);
            addKeyListener(new TetrisKeyListener(gameField, gamePanel));
            setVisible(true);

            int delay = 550;
            gameTimer = new Timer(delay, e -> {
                if (!gameField.getPause()) {
                    if (!gameField.tick()) {
                        gameTimer.stop();
                        handleGameOver();
                    }
                    int newDelay = Math.max(100, 500 - (gameField.getLevel() * 50));
                    gameTimer.setDelay(newDelay);
                    gamePanel.repaint();
                }
            });
            gameTimer.start();
        }

        private void handleGameOver() {
            String name;
            while (true) {
                name = JOptionPane.showInputDialog(null, "Game over. Enter your name:", "", JOptionPane.QUESTION_MESSAGE);
                if (name == null || name.isEmpty() || name.length() > 10 || !name.matches("[a-zA-Z0-9]+")) {
                    JOptionPane.showMessageDialog(null,"Name should contain no more than 10 english symbols or numbers.");
                } else break;
            }
            scoreHandler.addScore(name, gameField.getScore());
            dispose();
            new MenuScreen();
        }
    }

    public static class GamePanel extends JPanel {
        private static final int BLOCK_SIZE = 30;
        private final GameField gameField;
        private Map<Integer, Image> blockSprites;
        private Map<Integer, Image> tetrominoSprites;
        private JLabel scoreLabel;
        private JLabel levelLabel;

        public GamePanel(GameField gameField) {
            this.gameField = gameField;
            setLayout(null);

            scoreLabel = new JLabel(String.valueOf(gameField.getScore()));
            levelLabel = new JLabel(String.valueOf(gameField.getLevel()));

            configureLabel(scoreLabel, 77, 469);
            configureLabel(levelLabel, 77, 649);

            add(scoreLabel);
            add(levelLabel);

            loadBlockSprites();
            loadTetrominoSprites();
        }

        private void loadBlockSprites() {
            blockSprites = new HashMap<>();

            String[] tetrominoes = {"I", "J", "T", "Z", "L", "O", "S"};

            for (int i = 0; i < tetrominoes.length; i++) {
                blockSprites.put(i + 1, ImageLoader.loadImage("tetromino/" + tetrominoes[i] + ".png"));
            }

            for (int i = 0; i < tetrominoes.length; i++) {
                blockSprites.put(i + 8, ImageLoader.loadImage("tetromino/" + tetrominoes[i] + "G.png"));
            }
        }

        private void loadTetrominoSprites() {
            tetrominoSprites = new HashMap<>();

            String[] tetrominoes = {"Empty", "I", "J", "T", "Z", "L", "O", "S"};

            for (int i = 0; i < tetrominoes.length; i++) {
                tetrominoSprites.put(i, ImageLoader.loadImage("hold/hold" + tetrominoes[i] + ".png"));
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Image backdrop = ImageLoader.loadImage("backgrounds/gameScreen.png");
            g.drawImage(backdrop, 0, 0, this);
            renderField(g);
            renderTetromino(g, gameField.getGhostPiece(), true);
            renderTetromino(g, gameField.getCurrentPiece(), false);
            renderHold(g);
            renderNext(g);

            String scoreString = String.valueOf(gameField.getScore());
            String levelString = String.valueOf(gameField.getLevel());
            scoreLabel.setText(scoreString);
            levelLabel.setText(levelString);
        }

        private void configureLabel(JLabel label, int posX, int posY) {
            Font labelFont = new Font("Ness", Font.PLAIN, 22);
            label.setFont(labelFont);
            label.setForeground(Color.WHITE);
            label.setBounds(posX, posY, 136, 59);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
        }

        private void renderField(Graphics g) {
            Point startPos = new Point(300, 100);
            for (int row = 2; row < GameField.BOARD_HEIGHT; row++) {
                for (int col = 0; col < GameField.BOARD_WIDTH; col++) {
                    int type = gameField.getBoard()[row][col];
                    if (type != 0) {
                        Image blockImage = blockSprites.get(type);
                        g.drawImage(blockImage, startPos.x + col * BLOCK_SIZE, startPos.y + (row - 2) * BLOCK_SIZE,this);
                    }
                }
            }
        }

        private void renderTetromino(Graphics g, Tetromino tetromino, boolean isGhost) {
            Point startPos = new Point(300, 40);
            boolean[][] shape = tetromino.getShape();
            Point pos = tetromino.getPos();
            int type = tetromino.getType().getColorCode();
            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[row].length; col++) {
                    if (shape[row][col] && pos.y + row >= 2) {
                        int x = startPos.x + (pos.x + col) * BLOCK_SIZE;
                        int y = startPos.y + (pos.y + row) * BLOCK_SIZE;
                        Image blockImage = (isGhost) ? blockSprites.get(type + 7) : blockSprites.get(type);
                        g.drawImage(blockImage, x, y, BLOCK_SIZE, BLOCK_SIZE, this);
                    }
                }
            }
        }

        private void renderHold(Graphics g) {
            Point startPos = new Point(85, 130);
            Image tetrominoSprite = (gameField.getHoldPiece() == null) ? tetrominoSprites.get(0) : tetrominoSprites.get(gameField.getHoldPiece().getColorCode());
            g.drawImage(tetrominoSprite, startPos.x, startPos.y, this);
        }

        private void renderNext(Graphics g) {
            Point startPos = new Point(695, 220);
            for (TetrominoType type : gameField.getNext(3)) {
                Image tetrominoSprite = tetrominoSprites.get(type.getColorCode());
                g.drawImage(tetrominoSprite, startPos.x, startPos.y, this);
                startPos.move(startPos.x, startPos.y + tetrominoSprite.getHeight(this));
            }
        }
    }
}
