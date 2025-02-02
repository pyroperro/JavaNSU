package tetris.gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class MenuScreen {
    public MenuScreen() {
        new MenuWindow();
    }

    public static class MenuWindow extends JFrame {
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

    public static class MenuPanel extends JPanel {
        private enum ButtonType {
            START(
                    "buttons/startButton.png",
                    "buttons/startButtonHover.png",
                    121, 229,
                    menuPanel -> {
                        Window menuWindow = SwingUtilities.getWindowAncestor(menuPanel);
                        if (menuWindow != null) menuWindow.dispose();
                        new GameScreen();
                    }
            ),
            LEADERBOARD(
                    "buttons/leaderboardButton.png",
                    "buttons/leaderboardButtonHover.png",
                    47, 301,
                    menuPanel -> {
                        SwingUtilities.getWindowAncestor(menuPanel).dispose();
                        new HighscoreScreen();
                    }
            ),
            ABOUT(
                    "buttons/aboutButton.png",
                    "buttons/aboutButtonHover.png",
                    114, 372,
                    menuPanel -> {
                        SwingUtilities.getWindowAncestor(menuPanel).dispose();
                        new AboutScreen();
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
}
