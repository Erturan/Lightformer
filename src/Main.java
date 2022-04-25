import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    static final int MY_WIDTH = 1920;
    static final int MY_HEIGHT = 1080;

    static int levelNo = 1;

    static Level level;

    static PImage imgHeart;

    ArrayList<Powerup> powerups = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();

    static final PVector gravity = new PVector(0f, 0.2f);
    Player player;
    public void settings() {
        fullScreen();
    }

    public void setup() {
        player = new Player(this);
        imgHeart = loadImage("heart.png");
        imgHeart.resize(50,50);
        level = new Level(this, levelNo, powerups, enemies);
    }

    public void draw() {
        background(128);
        player.integrate(level);
        fill(220,220,220);
        //circle(player.pos.x, player.pos.y, player.getLightRadius());
        circle(displayWidth / 4, player.pos.y, player.getLightRadius());
        fill(25,25,255);
        circle(displayWidth / 4, player.pos.y, 5);
        for (int i = 0; i < player.health; i++) {
            image(imgHeart, 50 + i * 60, 1000);
        }

        float offset = player.pos.x;

        level.drawLevel(offset);

        pushStyle();
        textSize(50);
        textAlign(CENTER, TOP);
        text("Level " + levelNo, displayWidth / 2, 1000);
        popStyle();

        for (Powerup powerup: powerups) {
            powerup.checkCollision(player);
            powerup.drawPowerup(player.pos.x);
        }

        for (Enemy enemy: enemies) {
            enemy.integrate(level);
            enemy.checkPlayerCollision(player);
            enemy.draw(offset);
        }

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
