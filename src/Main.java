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
    boolean controlMenu = false;
    boolean gameOverScreen = false;
    final int menuItemWidth = 600;
    final int menuItemHeight = 100;


    static Level level;

    static PImage imgHeart;
    static PImage imgHealthPwrUp;
    static PImage imgEnergyPwrUp;

    static PImage imgPlayer;

    static PImage imgPlayer1;
    static PImage imgPlayer1Flipped;

    static PImage imgPlayer2;
    static PImage imgPlayer2Flipped;

    static PImage imgEnemyBasic1;
    static PImage imgEnemyBasic1Flipped;

    static PImage imgEnemyBasic2;
    static PImage imgEnemyBasic2Flipped;

    static PImage imgEnemyMid;

    static PImage imgEnemyHard1;
    static PImage imgEnemyHard1Flipped;

    static PImage imgEnemyHard2;
    static PImage imgEnemyHard2Flipped;

    static PImage imgPinhole;

    static PImage imgFloor;
    static PImage imgWall;

    static PImage imgWasd;
    static PImage imgSpace;
    static PImage imgMousePan;
    static PImage imgMouseClick;
    static PImage imgLantern;
    static PImage imgLanterLevelEnd;

    /*
    Pinhole options:
    1. PImage- png with background removed FAILED
    2. svg image- may be faster
    3. Draw shapes manually
    4. Use 3D PVectors, move pinhole forwards/backwards depending on distance

    Consider using scale instead of image resize
     */

    ArrayList<Powerup> powerups = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();

    static final PVector gravity = new PVector(0f, 0.2f);
    Player player;
    public void settings() {
        fullScreen();
    }

    public void setup() {
        fullScreen();
        textAlign(CENTER, CENTER);
        shapeMode(CENTER);
        imageMode(CENTER);
        player = new Player(this);

        imgHeart = loadImage("heart.png");
        imgHeart.resize(50,50);

        imgHealthPwrUp = loadImage("heart.png");
        imgHealthPwrUp.resize(40, 40);

        imgEnergyPwrUp = loadImage("lightning.png");
        imgEnergyPwrUp.resize(40, 40);

        imgPlayer = loadImage("player.png");
        imgPlayer.resize(Player.playerWidth, Player.playerHeight);

        imgPlayer1 = loadImage("player1.png");
        imgPlayer1.resize(Player.playerWidth, Player.playerHeight);

        //Creates flipped image (for walking) by altering pixels
        imgPlayer1Flipped = imgPlayer1.copy();
        for( int i = 0; i < imgPlayer1Flipped.width; i++ ){
            for(int j = 0; j < imgPlayer1Flipped.height; j++){
                imgPlayer1Flipped.set( imgPlayer1.width - 1 - i, j, imgPlayer1.get(i, j) );
            }
        }

        imgPlayer2 = loadImage("player2.png");
        imgPlayer2.resize(Player.playerWidth, Player.playerHeight);

        imgPlayer2Flipped = imgPlayer2.copy();
        for( int i = 0; i < imgPlayer2Flipped.width; i++ ){
            for(int j = 0; j < imgPlayer2Flipped.height; j++){
                imgPlayer2Flipped.set( imgPlayer2.width - 1 - i, j, imgPlayer2.get(i, j) );
            }
        }

        imgEnemyBasic1 = loadImage("enemyBasic1.png");
        imgEnemyBasic1.resize(EnemyBasic.basicWidth, EnemyBasic.basicHeight);

        imgEnemyBasic1Flipped = imgEnemyBasic1.copy();
        for( int i = 0; i < imgEnemyBasic1Flipped.width; i++ ){
            for(int j = 0; j < imgEnemyBasic1Flipped.height; j++){
                imgEnemyBasic1Flipped.set( imgEnemyBasic1.width - 1 - i, j, imgEnemyBasic1.get(i, j) );
            }
        }

        imgEnemyBasic2 = loadImage("enemyBasic2.png");
        imgEnemyBasic2.resize(EnemyBasic.basicWidth, EnemyBasic.basicHeight);

        imgEnemyBasic2Flipped = imgEnemyBasic2.copy();
        for( int i = 0; i < imgEnemyBasic2Flipped.width; i++ ){
            for(int j = 0; j < imgEnemyBasic2Flipped.height; j++){
                imgEnemyBasic2Flipped.set( imgEnemyBasic2.width - 1 - i, j, imgEnemyBasic2.get(i, j) );
            }
        }

        imgEnemyMid = loadImage("enemyMid.png");
        imgEnemyMid.resize(40, 40);

        imgEnemyHard1 = loadImage("enemyHard1.png");
        imgEnemyHard1.resize(EnemyHard.hardWidth, EnemyHard.hardHeight);

        imgEnemyHard1Flipped = imgEnemyHard1.copy();
        for( int i = 0; i < imgEnemyHard1Flipped.width; i++ ){
            for(int j = 0; j < imgEnemyHard1Flipped.height; j++){
                imgEnemyHard1Flipped.set( imgEnemyHard1.width - 1 - i, j, imgEnemyHard1.get(i, j) );
            }
        }

        imgEnemyHard2 = loadImage("enemyHard2.png");
        imgEnemyHard2.resize(EnemyHard.hardWidth, EnemyHard.hardHeight);

        imgEnemyHard2Flipped = imgEnemyHard2.copy();
        for( int i = 0; i < imgEnemyHard2Flipped.width; i++ ){
            for(int j = 0; j < imgEnemyHard2Flipped.height; j++){
                imgEnemyHard2Flipped.set( imgEnemyHard2.width - 1 - i, j, imgEnemyHard2.get(i, j) );
            }
        }

        imgFloor = loadImage("floor.png");
        imgWall = loadImage("wall.png");
        imgWasd = loadImage("wasd.png");
        imgWasd.resize(400, 200);

        imgSpace = loadImage("spacebar.png");
        imgSpace.resize(400, 200);

        imgMousePan = loadImage("mousemove.png");
        imgMousePan.resize(200, 250);

        imgMouseClick = loadImage("mouseclick.png");
        imgMouseClick.resize(150, 200);

        imgLantern = loadImage("lantern.png");

        imgLanterLevelEnd = loadImage("lanternonly.png");
        imgLanterLevelEnd.resize(50, 100);

        level = new Level(this, levelNo, powerups, enemies);
    }

    //Used when player dies, or progresses to next level
    public void resetLevel() {
        player = new Player(this);
        powerups = new ArrayList<>();
        enemies = new ArrayList<>();
        level = new Level(this, levelNo, powerups, enemies);
    }

    public void draw() {
        background(244,233,140);
        imageMode(CENTER);

        if (controlMenu) {
            drawControls();
            return;
        }

        if (!started) {
            drawMenu();
            return;
        }

        if (level.checkFallenOffLevel(player)) {
            player.health = 0;
        }

        if (player.health == 0) {
            background(0, 0, 89);
            pushStyle();
            textAlign(CENTER, CENTER);
            fill(244, 233, 140);
            textSize(100);
            text("GAME OVER", displayWidth / 2f, displayHeight / 2f - 300);
            text("Level " + levelNo, displayWidth / 2f, displayHeight / 2f - 150);
            text("Click to continue", displayWidth / 2f, displayHeight / 2f);

            gameOverScreen = true;

            popStyle();
            return;
        }

        //Beating the final level
        if (levelNo == 5) {
            pushStyle();
            textAlign(CENTER, CENTER);
            fill(0, 0, 89);
            textSize(72);

            text("Congratulations! You have found the infinity lantern,", displayWidth / 2f, displayHeight / 2f - 300);
            text("and in doing so have vanquished the creatures of darkness,", displayWidth / 2f, displayHeight / 2f - 150);
            text("restoring light to the world.", displayWidth / 2f, displayHeight / 2f);

            text("--Fin", displayWidth / 2f, displayHeight / 2f + 300);
            popStyle();
            return;
        }

        //Player is in level, therefore handle movement and draw
        player.integrate(level);
        //fill(220,220,220);
        //circle(player.pos.x, player.pos.y, player.getLightRadius());
        //circle(displayWidth / 4, player.pos.y, player.getLightRadius());

        if (level.checkEndLevelHit(player.pos)) {
            levelNo++;
            resetLevel();
            return;
        }

        fill(25,25,255);
        imageMode(CENTER);

        //Decides whether to display the flipped player image
        //Keep player fixed at displayWidth / 4
        //TODO: Future consider adding elasticity to view
        if (player.vel.x < -1) {
            if (player.stepCount < 8) {
                image(imgPlayer1Flipped, displayWidth / 4f, player.pos.y);
            } else {
                image(imgPlayer2Flipped, displayWidth / 4f, player.pos.y);

                if (player.stepCount == 15) {
                    player.stepCount = 0;
                }
            }
        } else if (player.vel.x > 1) {
            if (player.stepCount < 8) {
                image(imgPlayer1, displayWidth / 4f, player.pos.y);
                //enemy.draw(offset, imgEnemyBasic1Flipped);
            } else {
                image(imgPlayer2, displayWidth / 4f, player.pos.y);
                //enemy.draw(offset, imgEnemyBasic2Flipped);
                if (player.stepCount == 15) {
                    player.stepCount = 0;
                }
            }
        } else {
            if (player.vel.x < 0) {
                image(imgPlayer1Flipped, displayWidth / 4f, player.pos.y);
            } else {
                image(imgPlayer1, displayWidth / 4f, player.pos.y);
            }
        }
        //circle(displayWidth / 4, player.pos.y, 5);

        pushStyle();
        strokeWeight(8);
        stroke(151,232,255);
        //Draws the player bolts, checks if each enemy has collided
        for (Bolt bolt: player.bolts) {
            if (bolt.active) {
                bolt.updateBolt(player.pos, new PVector(mouseX + player.pos.x - displayWidth / 4f, mouseY));
                bolt.drawBolt();
                bolt.checkEnemyCollisions(enemies);
                bolt.incFrame();
            }
        }
        popStyle();

        //Because display is fixed on player, draws the level offset by the player's position
        float offset = player.pos.x;
        level.drawLevel(offset);

        //Draws the powerups, checks if collided with player
        for (Powerup powerup: powerups) {
            if (powerup.active) {
                powerup.checkCollision(player);
                if (powerup instanceof PowerupHealth) {
                    image(imgHealthPwrUp, powerup.position.x - offset + displayWidth / 4f, powerup.position.y);
                } else {
                    image(imgEnergyPwrUp, powerup.position.x - offset + displayWidth / 4f, powerup.position.y);
                }
            }
        }

        for (Enemy enemy: enemies) {
            //Checks if enemy has spawned- only spawn them when approaching
            enemy.checkSpawn();
            //Hard enemies shoot bullets- integrate these
            //TODO: Consider moving this into EnemyHard integrate?
            if (enemy instanceof EnemyHard && enemy.spawned) {
                ((EnemyHard) enemy).integrateBullets(level);
            }
            if (enemy.alive && enemy.spawned) {
                if (level.checkFallenOffLevel(enemy)) {
                    enemy.alive = false;
                    continue;
                }
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
                } else if (enemy instanceof EnemyHard) {
                    if (enemy.vel.x < 0) {
                        if (enemy.stepCount < 8) {
                            enemy.draw(offset, imgEnemyHard1);
                        } else {
                            enemy.draw(offset, imgEnemyHard2);
                            if (enemy.stepCount == 15) {
                                enemy.stepCount = 0;
                            }
                        }
                    } else {
                        if (enemy.stepCount < 8) {
                            enemy.draw(offset, imgEnemyHard1Flipped);
                        } else {
                            enemy.draw(offset, imgEnemyHard2Flipped);
                            if (enemy.stepCount == 15) {
                                enemy.stepCount = 0;
                            }
                        }
                    }
                }
            }
        }

        beginShape();
        fill(0,0,89);
        vertex(0,0);
        vertex(displayWidth, 0);
        vertex(displayWidth, displayHeight);
        vertex(0, displayHeight);

        //Draws the circle by calculating the verticies
        int numDetail = 200;
        float rot = 2 * PI / numDetail;
        beginContour();
        for (int i = 0; i < numDetail; i++) {
            float angle = i * rot;
            float x = cos(-angle);
            float y = sin(-angle);
            vertex(x * player.lightRadius + displayWidth / 4f, y * player.getLightRadius() + player.pos.y);
        }
        endContour();
        endShape();

        pushStyle();
        fill(255, 0,0);
        textSize(36);
        text(frameRate + "fps", 200, 50);
        popStyle();

        pushStyle();
        textSize(50);
        fill(255, 0, 0);
        textAlign(CENTER, CENTER);
        text("Level " + levelNo, displayWidth / 2f, 1000);
        popStyle();

        for (int i = 0; i < player.health; i++) {
            image(imgHeart, 50 + i * 60, 1000);
        }

        //Draws the crosshair
        pushStyle();
        stroke(255,0,0);
        strokeWeight(6);
        line(mouseX - 20, mouseY, mouseX + 20, mouseY);
        line(mouseX, mouseY - 20, mouseX, mouseY + 20);
        popStyle();
    }

    //Handles keypress inputs
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
        if (controlMenu) {
            System.out.println("exiting control menu");
            controlMenu = false;
            return;
        }
        if (started && !gameOverScreen) {
            player.fire(new PVector(mouseX + player.pos.x - displayWidth / 4f, mouseY));
        } else if (gameOverScreen) {
            gameOverScreen = false;
            started = false;
            resetLevel();
        } else {
            //In main menu- decide what to do based on coordinates
            if (mouseX < displayWidth / 2 + menuItemWidth / 2 && mouseX > displayWidth / 2 - menuItemWidth / 2 &&
                    mouseY < displayHeight / 2 + menuItemHeight / 2 + 300 && mouseY > displayHeight / 2 - menuItemHeight / 2 + 300) {
                started = true;
            }
            if (mouseX < displayWidth / 2 + menuItemWidth / 2 && mouseX > displayWidth / 2 - menuItemWidth / 2 &&
                    mouseY < displayHeight / 2 + menuItemHeight / 2 + 400 && mouseY > displayHeight / 2 - menuItemHeight / 2 + 400) {
                controlMenu = true;
            }
        }
    }

    public void drawMenu() {
        background(0, 0, 89);
        image(imgLantern, displayWidth / 5f, displayHeight / 2f);
        image(imgLantern, 4 * displayWidth / 5f, displayHeight / 2f);
        pushStyle();

        textAlign(CENTER, CENTER);

        textSize(108);
        text("Stay Alight", displayWidth / 2f, displayHeight / 2f - 475);

        textSize(48);
        rectMode(CENTER);
        fill(255,233,6);
        rect(displayWidth / 2f, displayHeight / 2f + 300, menuItemWidth, menuItemHeight);
        fill(0, 0, 89);
        text("Start Game", displayWidth / 2f, displayHeight / 2f + 300);

        fill(255,233,6);

        rect(displayWidth / 2f, displayHeight / 2f + 400, menuItemWidth, menuItemHeight);
        fill(0, 0, 89);
        text("Help/Controls", displayWidth / 2f, displayHeight / 2f + 400);

        popStyle();
    }

    public void drawControls() {
        pushStyle();
        fill(0, 0, 89);

        textSize(72);
        text("Controls: Click to exit", displayWidth / 2f, displayHeight / 10f);
        textSize(50);
        textAlign(LEFT, CENTER);
        image(imgWasd, 300, 3 * displayHeight / 10f);
        text("--Move", 600, 3 * displayHeight / 10f);

        image(imgSpace, 300, 5 * displayHeight / 10f);
        text("--Jump", 600, 5 * displayHeight / 10f);

        image(imgMousePan, 300, 7 * displayHeight / 10f);
        text("--Aim", 600, 7 * displayHeight / 10f);

        image(imgMouseClick, 300, 9 * displayHeight / 10f);
        text("--Fire", 600, 9 * displayHeight / 10f);

        text("Move through the level, find the lantern", displayWidth / 2f, 3 * displayHeight / 10f);
        image(imgLanterLevelEnd, displayWidth / 2f + 100, 4 * displayHeight / 10f);

        text("Shoot light bolts at the enemies", displayWidth / 2f, 5 * displayHeight / 10f);

        text("But be careful! This discharges the lantern!", displayWidth / 2f, 7 * displayHeight / 10f);

        text("Once your lantern is out you cannot see", displayWidth / 2f, 9 * displayHeight / 10f);
        popStyle();

    }
}
