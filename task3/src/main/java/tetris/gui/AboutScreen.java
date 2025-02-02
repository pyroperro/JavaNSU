package tetris.gui;

import javax.swing.*;
import java.awt.*;

public class AboutScreen {
    public AboutScreen() {
        new AboutWindow();
    }

    public static class AboutWindow extends JFrame {
        public AboutWindow() {
            setTitle("About");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);

            setIconImage(ImageLoader.loadImage("icon.png"));

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

    public static class AboutPanel extends JPanel {
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
                new MenuScreen();
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
}
