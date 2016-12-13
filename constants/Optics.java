package constants;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Non-instantiable class to hold all graphical information.
 */
public final class Optics {

    // FONTS
    public static final Font HUD_FONT = new Font("Comic Sans MS", Font.BOLD, 15);

    // COLORS
    public static final Color HUD_COLOR = new Color(255, 255, 255);

    // IMAGES
    //public static final BufferedImage MENU_BG = ImageIO.read((new File("/resources/Images/BackgroundGrid1.jpg")));

    private Optics() {
        // Prevents even the native class from calling this constructor. No clue what that means.
        throw new AssertionError();
    }
}
