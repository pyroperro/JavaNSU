package tetris.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {
    public static Image loadImage(String filename) {
        try {
            return ImageIO.read(Objects.requireNonNull(ImageLoader.class.getResource("/" + filename)));
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Failed to load image: " + filename, e);
        }
    }
}