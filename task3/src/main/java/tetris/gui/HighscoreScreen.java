package tetris.gui;

import tetris.model.LeaderboardsHandler;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighscoreScreen {
    public HighscoreScreen() {
        new HighscoreWindow();
    }

    public static class HighscoreWindow extends JFrame {
        public HighscoreWindow() {
            setTitle("Highscores");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);

            setIconImage(ImageLoader.loadImage("icon.png"));

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

    public static class HighscorePanel extends JPanel {
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
                new MenuScreen();
            });
            add(exitButton);

            LeaderboardsHandler scoreHandler = new LeaderboardsHandler();
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
}
