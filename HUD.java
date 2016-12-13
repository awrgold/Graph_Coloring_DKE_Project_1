import constants.*;
import static constants.Drawing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class HUD {

    private static final int STD_SCORE_X = (int) HUD_SPACE.getX() + 20;
    private static final int STD_SCORE_Y = (int) HUD_SPACE.getY() + 20;

    private static final int STD_TIMER_X = (int) HUD_SPACE.getMaxX() - 160;
    private static final int STD_TIMER_Y = (int) HUD_SPACE.getY() + 20;


    private static final int STD_FPS_X  = (int) HUD_SPACE.getMaxX() - 160;
    private static final int STD_FPS_Y  = (int) HUD_SPACE.getY() + 40;

    private String score;
    private int scoreX;
    private int scoreY;

    private int timer;
    private int timerMillis;
    private int timerX;
    private int timerY;

    private Game game;
    private int fps;
    private int fpsX;
    private int fpsY;

    public HUD() {
        setScoreLocation(STD_SCORE_X, STD_SCORE_Y);
        setTimerLocation(STD_TIMER_X, STD_TIMER_Y);
    }

    public HUD(int scoreX, int scoreY, int timerX, int timerY) {
        setScoreLocation(scoreX, scoreY);
        setTimerLocation(timerX, timerY);
    }

    public HUD(Game game) {
        this();
        setFpsLocation(STD_FPS_X, STD_FPS_Y);
        this.game = game;
    }

    public void setScoreLocation(int scoreX, int scoreY) {
        this.scoreX = scoreX;
        this.scoreY = scoreY;
    }
    public void setTimerLocation(int timerX, int timerY) {
        this.timerX = timerX;
        this.timerY = timerY;
    }
    public void setFpsLocation(int fpsX, int fpsY) {
        this.fpsX = fpsX;
        this.fpsY = fpsY;
    }

    public void tick(){
        timerMillis++;
        if (timerMillis == 60) {
            timer++;
            timerMillis = 0;
            fps = game.getFps();
        }
        score = getScore();
    }

    public void draw(Graphics2D g){
        g.setFont(Optics.HUD_FONT);
        g.setColor(Optics.HUD_COLOR);
        g.drawString(score, scoreX, scoreY);
        g.drawString("Time be tickin': " + timer, timerX, timerY);
        g.drawString("FPS: " + fps, fpsX, fpsY);
    }

    /**
     * One possible place to put the score computation.
     *
     * Not implemented.
     */
    private String getScore() {
        return "There is no score!";
    }

}
