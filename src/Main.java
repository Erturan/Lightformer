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

    boolean started = false;
    boolean gameOverScreen = false;
    final int menuItemWidth = 600;
    final int menuItemHeight = 100;


    static Level level;

    static PImage imgHeart;
    static PImage imgHealthPwrUp;
    static PImage imgEnergyPwrUp;

    static PImage imgPlayer;

    static PImage imgEnemyBasic1;
    static PImage imgEnemyBasic1Flipped;

    static PImage imgEnemyBasic2;
    static PImage imgEnemyBasic2Flipped;

    static PImage imgEnemyMid;

    ArrayList<Powerup> powerups = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();

    static final PVector gravity = new PVector(0f, 0.2f);
    Player player;
    public void settings() {
        fullScreen();
    }

    public void setup() {
        textAlign(CENTER, CENTER);
        imageMode(CENTER);
        player = new Player(this);
        imgHeart = loadImage("heart.png");
        imgHeart.resize(50,50);
        imgHealthPwrUp = loadImage("heart.png");
        imgHealthPwrUp.resize(40, 40);
        imgEnergyPwrUp = loadImage("lightning.png");
        imgEnergyPwrUp.resize(40, 40);

        imgPlayer = loadImage("player.png");
        imgPlayer.resize(50, 80);

        imgEnemyBasic1 = loadImage("enemyBasic1.png");
        imgEnemyBasic1.resize(50, 80);

        imgEnemyBasic1Flipped = imgEnemyBasic1.copy();
        for( int i = 0; i < imgEnemyBasic1Flipped.width; i++ ){
            for(int j = 0; j < imgEnemyBasic1Flipped.height; j++){
                imgEnemyBasic1Flipped.set( imgEnemyBasic1.width - 1 - i, j, imgEnemyBasic1.get(i, j) );
            }
        }

        imgEnemyBasic2 = loadImage("enemyBasic2.png");
        imgEnemyBasic2.resize(50, 80);

        imgEnemyBasic2Flipped = imgEnemyBasic2.copy();
        for( int i = 0; i < imgEnemyBasic2Flipped.width; i++ ){
            for(int j = 0; j < imgEnemyBasic2Flipped.height; j++){
                imgEnemyBasic2Flipped.set( imgEnemyBasic2.width - 1 - i, j, imgEnemyBasic2.get(i, j) );
            }
        }

        imgEnemyMid = loadImage("enemyMid.png");
        imgEnemyMid.resize(40, 40);

        level = new Level(this, levelNo, powerups, enemies);
    }

    public void resetLevel() {
        player = new Player(this);
        powerups = new ArrayList<>();
        enemies = new ArrayList<>();
        level = new Level(this, levelNo, powerups, enemies);
    }

    public void draw() {
        background(128);

        //text(frameRate + "fps", 50, 50);

        if (!started) {
            drawMenu();
            return;
        }

        if (player.health == 0) {
            pushStyle();
            textAlign(CENTER, CENTER);
            text("GAME OVER", displayWidth / 2, displayHeight / 2);
            text("Level " + levelNo, displayWidth / 2, displayHeight / 2 + 100);
            text("Click to continue", displayWidth / 2, displayHeight / 2 + 200);


            gameOverScreen = true;

            popStyle();
            return;
        }

        player.integrate(level);
        fill(220,220,220);
        //circle(player.pos.x, player.pos.y, player.getLightRadius());
        circle(displayWidth / 4, player.pos.y, player.getLightRadius());
        fill(25,25,255);

        image(imgPlayer, displayWidth / 4, player.pos.y);
        //circle(displayWidth / 4, player.pos.y, 5);


        //player.drawBolts();

        for (Bolt bolt: player.bolts) {
            if (bolt.active) {
                bolt.drawBolt();
                bolt.checkEnemyCollisions(enemies);
                // PVector start = new PVector(sketch.displayWidth / 4, pos.y);

                //pos x
                //sketch.line(sketch.displayWidth / 4, pos.y, )
                bolt.incFrame();
            }
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
            //powerup.drawPowerup(player.pos.x);
            if (powerup.active) {
                if (powerup instanceof PowerupHealth) {
                    //sketch.circle(position.x - offset + sketch.displayWidth / 4, position.y, 10);
                    image(imgHealthPwrUp, powerup.position.x - offset + displayWidth / 4, powerup.position.y);
                } else {
                    image(imgEnergyPwrUp, powerup.position.x - offset + displayWidth / 4, powerup.position.y);
                }
            }

        }

        for (Enemy enemy: enemies) {
            if (enemy.alive) {
                enemy.integrate(level);
                enemy.checkPlayerCollision(player);
                if (enemy instanceof EnemyBasic) {
                    if (enemy.vel.x < 0) {
                        if (enemy.stepCount < 8) {
                            enemy.draw(offset, imgEnemyBasic1);
                        } else {
                            enemy.draw(offset, imgEnemyBasic2);
                            if (enemy.stepCount == 15) {
                                enemy.stepCount = 0;
                            }
                        }
                    } else {
                        if (enemy.stepCount < 8) {
                            enemy.draw(offset, imgEnemyBasic1Flipped);
                        } else {
                            enemy.draw(offset, imgEnemyBasic2Flipped);
                            if (enemy.stepCount == 15) {
                                enemy.stepCount = 0;
                            }
                        }
                    }

                } else if (enemy instanceof EnemyMid) {
                    enemy.draw(offset, imgEnemyMid);
                }
            }
        }

        for (int i = 0; i < player.health; i++) {
            image(imgHeart, 50 + i * 60, 1000);
        }

        pushStyle();
        stroke(255,0,0);
        strokeWeight(6);
        line(mouseX - 20, mouseY, mouseX + 20, mouseY);
        line(mouseX, mouseY - 20, mouseX, mouseY + 20);
        popStyle();
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

    public void mousePressed() {
        if (started && !gameOverScreen) {
            player.fire(new PVector(mouseX + player.pos.x - displayWidth / 4, mouseY));
        } else if (gameOverScreen) {
            gameOverScreen = false;
            started = false;
            resetLevel();
        } else {
            if (mouseX < displayWidth / 2 + menuItemWidth / 2 && mouseX > displayWidth / 2 - menuItemWidth / 2 &&
                    mouseY < displayHeight / 2 + menuItemHeight / 2 - 300 && mouseY > displayHeight / 2 - menuItemHeight / 2 - 300) {
                started = true;
            }
        }
    }

    public void drawMenu() {


        background(128);
        pushStyle();
        textAlign(CENTER, CENTER);

        textSize(108);
        text("Stay Alight", displayWidth / 2, displayHeight / 2 - 475);

        textSize(48);
        rectMode(CENTER);
        fill(255);
        rect(displayWidth / 2, displayHeight / 2 - 300, menuItemWidth, menuItemHeight);
        fill(0);
        text("Start Game", displayWidth / 2, displayHeight / 2 - 300);

        fill(255);
        rect(displayWidth / 2, displayHeight / 2 - 100, menuItemWidth, menuItemHeight);
        fill(0);
        text("Help/Controls", displayWidth / 2, displayHeight / 2 - 100);

        fill(255);
        rect(displayWidth / 2, displayHeight / 2 + 100, menuItemWidth, menuItemHeight);
        fill(0);
        text("Settings", displayWidth / 2, displayHeight / 2 + 100);


        popStyle();
    }
}
