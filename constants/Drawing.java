package constants;

import java.awt.Rectangle;

/**
 * A non-instantiable class for holding all the game constants.
 */
public final class Drawing {

    // TODO: 12/12/2016 decide whether we implement exactly this.


    public static final int GAME_WIDTH = 900;
    public static final int GAME_HEIGHT = 600;

    public static final int RIM = 60;
    public static final Rectangle GRAPH_SPACE = new Rectangle(0, RIM, GAME_WIDTH, GAME_HEIGHT - RIM);
    public static final Rectangle HUD_SPACE = new Rectangle(0, 0,GAME_WIDTH, RIM);



    private Drawing(){
    // Prevents even the native class from calling this constructor. No clue what that means.
        throw new AssertionError();
    }
}
