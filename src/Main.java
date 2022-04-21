import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    static final int MY_WIDTH = 1920;
    static final int MY_HEIGHT = 1080;

    static final PVector gravity = new PVector(0f, 0.04f);
    Player player;
    public void settings() {
        fullScreen();
    }

    public void setup() {
        player = new Player(this);
    }

    public void draw() {
        background(128);
        player.integrate();
        circle(player.pos.x, player.pos.y, player.getLightRadius());

    }

    public void keyPressed() {
        if (key == 'a') {
            player.setMovingLeft();
        }
        if (key == 'd') {
            player.setMovingRight();
        }
        if (key == ' ') {
            player.setJumping();
        }
    }

    public void keyReleased() {
        if (key == 'a') {
            player.stopMovingLeft();
        }
        if (key == 'd') {
            player.stopMovingRight();
        }
        if (key == ' ') {
            player.stopJumping();
        }
    }
}
