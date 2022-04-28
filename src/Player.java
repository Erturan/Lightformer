import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Player extends Character {

    public static Player player;

    int health = 3;
    float lightRadius = 1000f;
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
        maxXVel = 10;
        this.sketch = sketch;
        pos = new PVector(sketch.displayWidth / 4, 500);
        vel = new PVector(0, 0);
        player = this;
    }

    public void integrate(Level currentLevel) {

        boolean collidesDown = currentLevel.collidesYDown(this);

        if (vel.y > 0 && collidesDown) {
            vel.y = 0;
        }

        if (vel.y < 0 && currentLevel.collidesYUp(this)) {
            vel.y = - vel.y;
        }

        if (currentLevel.collidesXLeft(this)) {
            vel.x = 0.1f;
        }

        if (currentLevel.collidesXRight(this)) {
            vel.x = -0.1f;
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

        if (jumping && collidesDown) {
            System.out.println("Jump");
            vel.y = 0;
            acceleration.y = -8;
            //touchingGround = false;
        }

        lightRadius *= 0.999;

        vel.add(acceleration);

        if (!movingLeft && !movingRight) {
            vel.x = vel.x * 0.95f;
        }


        if (vel.x > maxXVel) {
            vel.x = maxXVel;
        }
        if (vel.x < -maxXVel) {
            vel.x = -maxXVel;
        }

        /*if (vel.y > 0 && currentLevel.collidesYDown(this)) {
            vel.y = 0;
        }*/

        //System.out.println(vel.y);
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
