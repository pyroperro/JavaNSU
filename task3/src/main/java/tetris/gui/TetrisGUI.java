package tetris.gui;

import tetris.model.GameField;
import tetris.model.ScoreHandler;
import tetris.model.Tetromino;
import tetris.model.TetrominoType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.List;

public class TetrisGUI extends JFrame {
    private GameField gameField;
    private GamePanel gamePanel;
    private Timer gameTimer;
    private ScoreHandler scoreHandler;

    public TetrisGUI() {
        setTitle("Tetris Game");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        gameField = new GameField();
        gamePanel = new GamePanel(gameField);
        scoreHandler = new ScoreHandler();

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
        new MenuWindow();
    }

    public static void main(String[] args) {
        new MenuWindow();
    }
}

class GamePanel extends JPanel {
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
        renderTetromino(g, gameField.getGhostPiece());
        renderTetromino(g, gameField.getCurrentPiece());
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
        for (int row = 0; row < GameField.BOARD_HEIGHT; row++) {
            for (int col = 0; col < GameField.BOARD_WIDTH; col++) {
                int type = gameField.getBoard()[row][col];
                if (type != 0) {
                    Image blockImage = blockSprites.get(type);
                    g.drawImage(blockImage, startPos.x + col * BLOCK_SIZE, startPos.y + row * BLOCK_SIZE,this);
                }
            }
        }
    }

    private void renderTetromino(Graphics g, Tetromino tetromino) {
        Point startPos = new Point(300, 100);
        boolean[][] shape = tetromino.getShape();
        Point pos = tetromino.getPos();
        int type = tetromino.getType().getColorCode();
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col]) {
                    int x = startPos.x + (pos.x + col) * BLOCK_SIZE;
                    int y = startPos.y + (pos.y + row) * BLOCK_SIZE;
                    Image blockImage = (tetromino.isGhost()) ? blockSprites.get(type + 7) : blockSprites.get(type);
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

class MenuWindow extends JFrame {
    public MenuWindow() {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        MenuPanel menuPanel = new MenuPanel();
        setContentPane(menuPanel);
        pack();

        Insets insets = getInsets();
        int extraWidth = insets.left + insets.right;
        int extraHeight = insets.top + insets.bottom;

        setSize(360 + extraWidth, 660 + extraHeight);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class MenuPanel extends JPanel {
    private enum ButtonType {
        START(
                "buttons/startButton.png",
                "buttons/startButtonHover.png",
                121, 229,
                menuPanel -> {
                    Window menuWindow = SwingUtilities.getWindowAncestor(menuPanel);
                    if (menuWindow != null) menuWindow.dispose();
                    new TetrisGUI();
                }
        ),
        LEADERBOARD(
                "buttons/leaderboardButton.png",
                "buttons/leaderboardButtonHover.png",
                47, 301,
                menuPanel -> {
                    SwingUtilities.getWindowAncestor(menuPanel).dispose();
                    new HighscoreWindow();
                }
        ),
        ABOUT(
                "buttons/aboutButton.png",
                "buttons/aboutButtonHover.png",
                114, 372,
                menuPanel -> {
                    SwingUtilities.getWindowAncestor(menuPanel).dispose();
                    new AboutWindow();
                }
        ),
        EXIT(
                "buttons/exitButton.png",
                "buttons/exitButtonHover.png",
                131, 444,
                menuPanel -> System.exit(0) // Exit the application
        );

        private final String imagePath;
        private final String hoverImagePath;
        private final int x;
        private final int y;
        private final Consumer<MenuPanel> action;

        ButtonType(String imagePath, String hoverImagePath, int x, int y, Consumer<MenuPanel> action) {
            this.imagePath = imagePath;
            this.hoverImagePath = hoverImagePath;
            this.x = x;
            this.y = y;
            this.action = action;
        }

        public String getImagePath() { return imagePath; }
        public String getHoverImagePath() { return hoverImagePath; }
        public int getX() { return x; }
        public int getY() { return y; }
        public Consumer<MenuPanel> getAction() { return action; }
    }

    public MenuPanel() {
        setPreferredSize(new Dimension(360, 660));
        setLayout(null);

        Image backdrop = ImageLoader.loadImage("backgrounds/mainMenu.png");

        for (ButtonType type : ButtonType.values()) {
            JButton button = createMenuButton(type);
            add(button);
        }
    }

    private JButton createMenuButton(ButtonType type) {
        ImageIcon icon = new ImageIcon(ImageLoader.loadImage(type.getImagePath()));
        ImageIcon hoverIcon = new ImageIcon(ImageLoader.loadImage(type.getHoverImagePath()));
        JButton button = new JButton(icon);
        setupMenuButton(button, type.getX(), type.getY(), icon, hoverIcon);
        button.addActionListener(e -> type.getAction().accept(this));

        return button;
    }

    void setupMenuButton(JButton button, int posX, int posY, ImageIcon icon, ImageIcon hoverIcon) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setBounds(posX, posY, icon.getIconWidth(), icon.getIconHeight());
        button.setRolloverEnabled(true);
        button.setRolloverIcon(hoverIcon);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backdrop = ImageLoader.loadImage("backgrounds/mainMenu.png");
        g.drawImage(backdrop, 0, 0, this);
    }
}

class AboutWindow extends JFrame {
    public AboutWindow() {
        setTitle("About");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        AboutPanel aboutPanel = new AboutPanel();
        setContentPane(aboutPanel);
        pack();

        Insets insets = getInsets();
        int extraWidth = insets.left + insets.right;
        int extraHeight = insets.top + insets.bottom;

        setSize(360 + extraWidth, 660 + extraHeight);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class AboutPanel extends JPanel {
    public AboutPanel() {
        setPreferredSize(new Dimension(360, 660));
        setLayout(null);

        ImageIcon exitButtonIcon = new ImageIcon(ImageLoader.loadImage("buttons/exitButton.png"));
        ImageIcon exitButtonHoverIcon = new ImageIcon(ImageLoader.loadImage("buttons/exitButtonHover.png"));
        JButton exitButton = new JButton(exitButtonIcon);

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(false);
        exitButton.setBounds(132, 525, exitButtonIcon.getIconWidth(), exitButtonIcon.getIconHeight());
        exitButton.setRolloverEnabled(true);
        exitButton.setRolloverIcon(exitButtonHoverIcon);
        exitButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            new MenuWindow();
        });
        add(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backdrop = ImageLoader.loadImage("backgrounds/aboutScreen.png");
        g.drawImage(backdrop, 0, 0, this);
    }
}

class HighscoreWindow extends JFrame {
    public HighscoreWindow() {
        setTitle("Highscores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        HighscorePanel highscorePanel = new HighscorePanel();
        setContentPane(highscorePanel);
        pack();

        Insets insets = getInsets();
        int extraWidth = insets.left + insets.right;
        int extraHeight = insets.top + insets.bottom;

        setSize(360 + extraWidth, 660 + extraHeight);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class HighscorePanel extends JPanel {
    private final List<String> scores;

    public HighscorePanel() {
        setPreferredSize(new Dimension(360, 660));
        setLayout(null);

        ImageIcon exitButtonIcon = new ImageIcon(ImageLoader.loadImage("buttons/exitButton.png"));
        ImageIcon exitButtonHoverIcon = new ImageIcon(ImageLoader.loadImage("buttons/exitButtonHover.png"));
        JButton exitButton = new JButton(exitButtonIcon);

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(false);
        exitButton.setBounds(132, 592, exitButtonIcon.getIconWidth(), exitButtonIcon.getIconHeight());
        exitButton.setRolloverEnabled(true);
        exitButton.setRolloverIcon(exitButtonHoverIcon);
        exitButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            new MenuWindow();
        });
        add(exitButton);

        ScoreHandler scoreHandler = new ScoreHandler();
        scores = scoreHandler.getHighScores();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backdrop = ImageLoader.loadImage("backgrounds/leaderboardScreen.png");
        g.drawImage(backdrop, 0, 0, this);

        Font labelFont = new Font("Ness", Font.PLAIN, 18);

        int posX = 50;
        int posY = 100;
        int i = 1;
        for (String scoreEntry : scores) {
            JLabel label = new JLabel(i + ". " + scoreEntry);
            label.setFont(labelFont);
            label.setForeground(Color.WHITE);
            label.setBounds(posX, posY, 500, 100);
            label.setHorizontalAlignment(SwingConstants.LEFT);
            label.setVerticalAlignment(SwingConstants.CENTER);
            add(label);

            posY += 22;
            i++;
        }

    }
}