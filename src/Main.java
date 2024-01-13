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
    final int stepSwitch = 8;
    final int stepReset = 15;
    final int boltThickness = 8;
    final float lightMultiplerFast = 0.997f;
    final int crosshairThickness = 6;


    static Level level;

    static PImage imgHeart;
    static PImage imgPowerupHealth;
    static PImage imgPowerupEnergy;
    static PImage imgPowerupSpeed;

    static PImage imgPlayer;

    static PImage imgPlayer1;
    static PImage imgPlayer1Flipped;

    static PImage imgPlayer2;
    static PImage imgPlayer2Flipped;

    static PImage imgEnemyBasic1;
    static PImage imgEnemyBasic1Flipped;

    static PImage imgEnemyBasic2;
    static PImage imgEnemyBasic2Flipped;

    static PImage imgEnemyBasicDying;

    static PImage imgEnemyMid;
    static PImage imgEnemyMidDying;

    static PImage imgEnemyHard1;
    static PImage imgEnemyHard1Flipped;

    static PImage imgEnemyHard2;
    static PImage imgEnemyHard2Flipped;

    static PImage imgEnemyHardDying;

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
        //displayWidth = 1920;
        //displayHeight = 1080;
        fullScreen();
        textAlign(CENTER, CENTER);
        shapeMode(CENTER);
        imageMode(CENTER);
        loadImages();
        player = new Player(this, 0);
        level = new Level(this, levelNo, powerups, enemies);
    }

    //When player dies or progresses
    public void resetLevel(boolean died) {
        //Set coin balance according to whether the player has died or progressed to the next level
        //Ensures player doesn't keep coins they obtain during a run where they die
        if (died) {
            player = new Player(this, player.origCoinBalance);
        } else {
            player = new Player(this, player.coinBalance);
        }
        powerups = new ArrayList<>();
        enemies = new ArrayList<>();
        level = new Level(this, levelNo, powerups, enemies);
    }

    public void draw() {
        background(244, 233, 140);
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
            drawGameOver();
            return;
        }

        //Beating the final level
        if (levelNo == 5) {
            drawVictoryScreen();
            return;
        }

        //If player is in level, handle movement and draw
        if (!player.integrate(level)) {
            //If they've died, draw the game over screen
            drawGameOver();
        }

        if (level.checkEndLevelHit(player.pos)) {
            levelNo++;
            resetLevel(false);
            return;
        }

        fill(25, 25, 255);
        imageMode(CENTER);

        drawBolts();

        //Because display is fixed on player, draws the level offset by the player's position
        float offset = player.pos.x;
        level.drawLevel(offset);

        //Draws the powerups, checks if collided with player
        drawPowerups(offset);

        drawEnemies(offset);

        drawLightRadius();

        drawUI();
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
            resetLevel(true);
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
        fill(255, 233, 6);
        rect(displayWidth / 2f, displayHeight / 2f + 300, menuItemWidth, menuItemHeight);
        fill(0, 0, 89);
        text("Start Game", displayWidth / 2f, displayHeight / 2f + 300);

        fill(255, 233, 6);

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

    public void drawGameOver() {
        background(0,0,89);

        pushStyle();

        textAlign(CENTER, CENTER);

        fill(244,233,140);

        textSize(100);

        text("GAME OVER",displayWidth /2f, displayHeight /2f-300);

        text("Level "+levelNo, displayWidth /2f, displayHeight /2f-150);

        text("Click to continue",displayWidth /2f, displayHeight /2f);

        gameOverScreen =true;

        popStyle();
    }

    public void drawVictoryScreen() {
        pushStyle();
        textAlign(CENTER, CENTER);
        fill(0, 0, 89);
        textSize(72);

        text("Congratulations! You have found the infinity lantern,", displayWidth / 2f, displayHeight / 2f - 300);
        text("and in doing so have vanquished the creatures of darkness,", displayWidth / 2f, displayHeight / 2f - 150);
        text("restoring light to the world.", displayWidth / 2f, displayHeight / 2f);

        text("--Fin", displayWidth / 2f, displayHeight / 2f + 300);
        popStyle();
    }

    public void drawEnemies(float offset) {
        for (Enemy enemy : enemies) {
            //Checks if enemy has spawned- only spawn them when approaching
            enemy.checkSpawn();
            if (!enemy.dead && enemy.spawned) {
                if (level.checkFallenOffLevel(enemy)) {
                    enemy.dead = true;
                    continue;
                }
                enemy.integrate(level);
                enemy.checkPlayerCollision(player);
                enemy.draw(offset);
            }
        }
    }

    public void drawPowerups(float offset) {
        for (Powerup powerup : powerups) {
            if (powerup.active) {
                powerup.checkCollision(player);
                powerup.draw(offset);
            }
        }
    }

    public void drawBolts() {
        pushStyle();
        strokeWeight(boltThickness);
        stroke(151, 232, 255);
        //Draws the player bolts, checks if each enemy has collided
        for (Bolt bolt : player.bolts) {
            if (bolt.active) {
                bolt.updateBolt(player.pos, new PVector(mouseX + player.pos.x - displayWidth / 4f, mouseY), level);
                bolt.drawBolt();
                bolt.checkEnemyCollisions(enemies);
                bolt.incFrame();
                player.lightRadius *= lightMultiplerFast;
            }
        }
        popStyle();
    }

    public void drawLightRadius() {
        beginShape();
        fill(0, 0, 89);
        vertex(0, 0);
        vertex(displayWidth, 0);
        vertex(displayWidth, displayHeight);
        vertex(0, displayHeight);

        //Draws the circle by calculating the verticies
        final int numDetail = 200;
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
    }

    public void drawUI() {
        pushStyle();
        fill(255, 0, 0);
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

        //Draw the crosshair
        pushStyle();
        stroke(255, 0, 0);
        strokeWeight(crosshairThickness);
        line(mouseX - 20, mouseY, mouseX + 20, mouseY);
        line(mouseX, mouseY - 20, mouseX, mouseY + 20);
        popStyle();

        //Draw coin balance
        pushStyle();
        textSize(50);
        fill(255, 223, 0);
        textAlign(CENTER, CENTER);
        text(player.coinBalance, displayWidth - 100, 100);
        popStyle();
    }

    public void loadImages() {
        imgHeart = loadImage("heart.png");
        imgHeart.resize(50, 50);

        imgPowerupHealth = loadImage("heart.png");
        imgPowerupHealth.resize(40, 40);

        imgPowerupSpeed = loadImage("powerupSpeed.png");
        imgPowerupSpeed.resize(50, 50);

        imgPowerupEnergy = loadImage("lightning.png");
        imgPowerupEnergy.resize(40, 40);

        imgPlayer = loadImage("player.png");
        imgPlayer.resize(Player.playerWidth, Player.playerHeight);

        imgPlayer1 = loadImage("player1.png");
        imgPlayer1.resize(Player.playerWidth, Player.playerHeight);

        //Creates flipped image (for walking) by altering pixels
        imgPlayer1Flipped = imgPlayer1.copy();
        for (int i = 0; i < imgPlayer1Flipped.width; i++) {
            for (int j = 0; j < imgPlayer1Flipped.height; j++) {
                imgPlayer1Flipped.set(imgPlayer1.width - 1 - i, j, imgPlayer1.get(i, j));
            }
        }

        imgPlayer2 = loadImage("player2.png");
        imgPlayer2.resize(Player.playerWidth, Player.playerHeight);

        imgPlayer2Flipped = imgPlayer2.copy();
        for (int i = 0; i < imgPlayer2Flipped.width; i++) {
            for (int j = 0; j < imgPlayer2Flipped.height; j++) {
                imgPlayer2Flipped.set(imgPlayer2.width - 1 - i, j, imgPlayer2.get(i, j));
            }
        }

        imgEnemyBasic1 = loadImage("enemyBasic1.png");
        imgEnemyBasic1.resize(EnemyBasic.basicWidth, EnemyBasic.basicHeight);

        imgEnemyBasic1Flipped = imgEnemyBasic1.copy();
        for (int i = 0; i < imgEnemyBasic1Flipped.width; i++) {
            for (int j = 0; j < imgEnemyBasic1Flipped.height; j++) {
                imgEnemyBasic1Flipped.set(imgEnemyBasic1.width - 1 - i, j, imgEnemyBasic1.get(i, j));
            }
        }

        imgEnemyBasic2 = loadImage("enemyBasic2.png");
        imgEnemyBasic2.resize(EnemyBasic.basicWidth, EnemyBasic.basicHeight);

        imgEnemyBasic2Flipped = imgEnemyBasic2.copy();
        for (int i = 0; i < imgEnemyBasic2Flipped.width; i++) {
            for (int j = 0; j < imgEnemyBasic2Flipped.height; j++) {
                imgEnemyBasic2Flipped.set(imgEnemyBasic2.width - 1 - i, j, imgEnemyBasic2.get(i, j));
            }
        }

        imgEnemyBasicDying = loadImage("enemyBasicDying.png");
        //Because this image is rotated 90 degrees, flip height and width
        imgEnemyBasicDying.resize(EnemyBasic.basicHeight, EnemyBasic.basicWidth);


        imgEnemyMid = loadImage("enemyMid.png");
        imgEnemyMid.resize(40, 40);

        imgEnemyMidDying = loadImage("enemyMidDying.png");
        imgEnemyMidDying.resize(40, 40);

        imgEnemyHard1 = loadImage("enemyHard1.png");
        imgEnemyHard1.resize(EnemyHard.hardWidth, EnemyHard.hardHeight);

        imgEnemyHard1Flipped = imgEnemyHard1.copy();
        for (int i = 0; i < imgEnemyHard1Flipped.width; i++) {
            for (int j = 0; j < imgEnemyHard1Flipped.height; j++) {
                imgEnemyHard1Flipped.set(imgEnemyHard1.width - 1 - i, j, imgEnemyHard1.get(i, j));
            }
        }

        imgEnemyHard2 = loadImage("enemyHard2.png");
        imgEnemyHard2.resize(EnemyHard.hardWidth, EnemyHard.hardHeight);

        imgEnemyHard2Flipped = imgEnemyHard2.copy();
        for (int i = 0; i < imgEnemyHard2Flipped.width; i++) {
            for (int j = 0; j < imgEnemyHard2Flipped.height; j++) {
                imgEnemyHard2Flipped.set(imgEnemyHard2.width - 1 - i, j, imgEnemyHard2.get(i, j));
            }
        }

        imgEnemyHardDying = loadImage("enemyHardDying.png");
        imgEnemyHardDying.resize(EnemyHard.hardHeight, EnemyHard.hardWidth);

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
    }
}
