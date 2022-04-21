import processing.core.PApplet;
import processing.core.PVector;

public class Player {

    PApplet sketch;

    PVector pos;
    PVector vel;
    int maxXVel = 10;
    int health = 3;
    float lightRadius = 1000f;
    boolean jumping = false;
    boolean touchingGround = false;

    boolean movingLeft, movingRight;

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
        this.sketch = sketch;
        pos = new PVector(500, 500);
        vel = new PVector(0, 0);
    }

    public void integrate() {
        pos.add(vel);

        PVector acceleration = new PVector();
        if (pos.y < 800) {
            acceleration.y = Main.gravity.y;
            touchingGround = false;
        } else {
            vel.y = 0;
            touchingGround = true;
        }
        if (movingLeft) {
            acceleration.x = -1;
        }
        if (movingRight) {
            acceleration.x = 1;
        }


        if (jumping && touchingGround) {
            acceleration.y = -5;
            touchingGround = false;
        }

        lightRadius *= 0.999;

        vel.add(acceleration);


        if (vel.x > maxXVel) {
            vel.x = maxXVel;
        }
        if (vel.x < -maxXVel) {
            vel.x = -maxXVel;
        }
    }


}
