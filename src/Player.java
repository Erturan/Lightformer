import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PImage;



import java.util.ArrayList;

public class Player extends Character {

    static int playerWidth = 50;
    static int playerHeight = 80;

    public static Player player;

    final float lightMultiplier = 0.999f;
    final int startYPos = 500;

    int stepCount = 0;
    int health = 3;
    float lightRadius = 2000f;
    boolean jumping = false;
    int origCoinBalance;
    int coinBalance;

    boolean movingLeft, movingRight;

    ArrayList<Bolt> bolts = new ArrayList<>();
    int speedBoostTimer = 0;
    final int speedBoostTotalTime = 300;
    final int speedBoostAmount = 13;

    float getLightRadius() {
        return lightRadius;
    }

    void setMovingLeft() {
        movingLeft = true;
    }
    void setMovingRight() {
        movingRight = true;
    }
    void stopMovingLeft() {
        movingLeft = false;
    }
    void stopMovingRight() {
        movingRight = false;
    }
    void setJumping() {
        jumping = true;
    }

    void stopJumping() {
        jumping = false;
    }

    public Player(PApplet sketch, int coinBalance) {
        maxXVel = 7;
        this.sketch = sketch;
        pos = new PVector(sketch.displayWidth / 4f, startYPos);
        vel = new PVector(0, 0);
        player = this;
        sizeX = playerWidth;
        sizeY = playerHeight;
        this.origCoinBalance = coinBalance;
        this.coinBalance = coinBalance;
    }

    public boolean integrate(Level currentLevel) {
        if (currentLevel.checkFallenOffLevel(player) || player.health == 0) {
            return false;
        }

        boolean collidesUp = currentLevel.collidesYUp(this);
        boolean collidesDown = currentLevel.collidesYDown(this);
        boolean collidesLeft = currentLevel.collidesXLeft(this);
        boolean collidesRight = currentLevel.collidesXRight(this);

        boolean collidesWallLeft = currentLevel.collidesWallLeft(this);
        boolean collidesWallRight = currentLevel.collidesWallRight(this);

        if (collidesLeft) adjustXLeft();
        if (collidesRight) adjustXRight();

        //Check wall collision returns. These also handle whether at the end of a slide
        if (collidesWallLeft || collidesWallRight) {
            vel.x = 0;
            if (!collidesDown) {
                vel.y = vel.y + 0.5f;
            }
            else {
                vel.y = 0;
            }
        } else {
            if (vel.x > 1 || vel.x < -1) {
                stepCount++;
            }
            if (vel.y > 0 && collidesDown) {
                vel.y = 0;
                adjustY();
            }

            if (vel.y < 0 && collidesUp && !collidesLeft && !collidesRight) {
                vel.y = - vel.y;
            }

            if (collidesLeft) {
                vel.x = 0.1f;
            }

            if (collidesRight) {
                vel.x = -0.1f;
            }
        }

        pos.add(vel);

        PVector acceleration = new PVector();
        acceleration.y = currentLevel.gravity.y;

        if (movingLeft) {
            acceleration.x = -1.2f;
        }
        if (movingRight) {
            acceleration.x = 1.2f;
        }

        if (jumping && collidesDown && !collidesLeft && !collidesRight) {
            System.out.println("Jump");
            vel.y = 0;
            acceleration.y = -8;
        }

        lightRadius *= lightMultiplier;

        vel.add(acceleration);
        if (!movingLeft && !movingRight) {
            vel.x = vel.x * 0.85f;
        }

        if (vel.x > maxXVel) {
            vel.x = maxXVel;
        }
        if (vel.x < -maxXVel) {
            vel.x = -maxXVel;
        }

        if (speedBoostTimer > 0) {
            speedBoostTimer--;
            if (speedBoostTimer == 0) {
                maxXVel -= speedBoostAmount;
            }
        }
        draw();
        return true;
    }

    public void takeHit() {
        if (health > 0) {
            health--;
        }
    }

    public void fire(PVector target) {
        if (lightRadius > 100) {
            bolts.add(new Bolt(sketch, pos, target));
        }
    }

    public void addSpeedBoost() {
        speedBoostTimer = speedBoostTotalTime;
        maxXVel += speedBoostAmount;
    }

    public void draw() {
        //Decides whether to display the flipped player image
        //Keep player fixed at displayWidth / 4
        //TODO: Future consider adding elasticity to view
        if (player.vel.x < -1) {
            if (player.stepCount < stepSwitch) {
                sketch.image(Main.imgPlayer1Flipped, sketch.displayWidth / 4f, player.pos.y);
            } else {
                sketch.image(Main.imgPlayer2Flipped, sketch.displayWidth / 4f, player.pos.y);

                if (player.stepCount == stepReset) {
                    player.stepCount = 0;
                }
            }
        } else if (player.vel.x > 1) {
            if (player.stepCount < stepSwitch) {
                sketch.image(Main.imgPlayer1, sketch.displayWidth / 4f, player.pos.y);
            } else {
                sketch.image(Main.imgPlayer2, sketch.displayWidth / 4f, player.pos.y);
                if (player.stepCount == stepReset) {
                    player.stepCount = 0;
                }
            }
        } else {
            if (player.vel.x < 0) {
                sketch.image(Main.imgPlayer1Flipped, sketch.displayWidth / 4f, player.pos.y);
            } else {
                sketch.image(Main.imgPlayer1, sketch.displayWidth / 4f, player.pos.y);
            }
        }
        //drawLightRadius();
    }

    public void drawLightRadius() {
        sketch.beginShape();
        sketch.fill(0, 0, 89);
        sketch.vertex(0, 0);
        sketch.vertex(sketch.displayWidth, 0);
        sketch.vertex(sketch.displayWidth, sketch.displayHeight);
        sketch.vertex(0, sketch.displayHeight);

        //Draws the circle by calculating the vertices
        final int numDetail = 200;
        float rot = 2 * sketch.PI / numDetail;
        sketch.beginContour();
        for (int i = 0; i < numDetail; i++) {
            float angle = i * rot;
            float x = PApplet.cos(-angle);
            float y = PApplet.sin(-angle);
            sketch.vertex(x * player.lightRadius + sketch.displayWidth / 4f, y * player.getLightRadius() + player.pos.y);
        }
        sketch.endContour();
        sketch.endShape();
    }

    public void drawBolts(Level currentLevel, ArrayList<Enemy> enemies) {
        sketch.pushStyle();
        sketch.strokeWeight(Bolt.boltThickness);
        sketch.stroke(151, 232, 255);
        for (Bolt bolt: bolts) {
            if (bolt.active) {
                bolt.updateBolt(pos, new PVector(sketch.mouseX + pos.x - sketch.displayWidth / 4f, sketch.mouseY), currentLevel);
                bolt.drawBolt();
                bolt.checkEnemyCollisions(enemies);
                bolt.incFrame();
                lightRadius *= Bolt.lightMultiplerFast;
            }
        }
        sketch.popStyle();
    }


}
