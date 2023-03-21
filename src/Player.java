import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Player extends Character {

    static int playerWidth = 50;
    static int playerHeight = 80;

    public static Player player;


    int stepCount = 0;

    int health = 3;
    float lightRadius = 2000f;
    boolean jumping = false;
    //boolean touchingGround = false;

    boolean movingLeft, movingRight;

    ArrayList<Bolt> bolts = new ArrayList<>();

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

    public Player(PApplet sketch) {
        maxXVel = 7;
        this.sketch = sketch;
        pos = new PVector(sketch.displayWidth / 4, 500);
        vel = new PVector(0, 0);
        player = this;
        sizeX = playerWidth;
        sizeY = playerHeight;
    }

    public void integrate(Level currentLevel) {

        boolean collidesUp = currentLevel.collidesYUp(this);
        boolean collidesDown = currentLevel.collidesYDown(this);
        boolean collidesLeft = currentLevel.collidesXLeft(this);
        boolean collidesRight = currentLevel.collidesXRight(this);

        boolean collidesWallLeft = currentLevel.collidesWallLeft(this);
        boolean collidesWallRight = currentLevel.collidesWallRight(this);

        //First check wall collision returns. These also handle whether at the end of a slide
        if (collidesWallLeft || collidesWallRight) {
            vel.x = 0;
            vel.y = vel.y + 0.5f;
        } else {
            if (vel.x > 1 || vel.x < -1) {
                stepCount++;
            }

            if (vel.y > 0 && collidesDown) {
                vel.y = 0;
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

        acceleration.y = Main.gravity.y;

        if (movingLeft) {
            acceleration.x = -1;
        }
        if (movingRight) {
            acceleration.x = 1;
        }

        if (jumping && collidesDown && !collidesLeft && !collidesRight) {
            System.out.println("Jump");
            vel.y = 0;
            acceleration.y = -8;
            //touchingGround = false;
        }

        lightRadius *= 0.999;

        vel.add(acceleration);

        if (!movingLeft && !movingRight) {
            vel.x = vel.x * 0.9f;
        }


        if (vel.x > maxXVel) {
            vel.x = maxXVel;
        }
        if (vel.x < -maxXVel) {
            vel.x = -maxXVel;
        }

    }

    public void takeHit() {
        if (health > 0) {
            health--;
        }
    }

    public void fire(PVector target) {
        if (lightRadius > 100) {
            bolts.add(new Bolt(sketch, pos, target));
            lightRadius -= 100;
        }

    }

    public void drawBolts() {

    }


}
