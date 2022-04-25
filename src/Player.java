import processing.core.PApplet;
import processing.core.PVector;

public class Player extends Character {

    int health = 3;
    float lightRadius = 1000f;
    boolean jumping = false;
    //boolean touchingGround = false;

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
        maxXVel = 10;
        this.sketch = sketch;
        pos = new PVector(sketch.displayWidth / 4, 500);
        vel = new PVector(0, 0);
    }

    public void integrate(Level currentLevel) {

        boolean collidesDown = currentLevel.collidesYDown(this);

        if (vel.y > 0 && collidesDown) {
            vel.y = 0;
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
            acceleration.y = -3;
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

        if (vel.y > 0 && currentLevel.collidesYDown(this)) {
            vel.y = 0;
        }

        if (vel.y < 0 && currentLevel.collidesYUp(this)) {
            vel.y = - vel.y;
        }

        //System.out.println(vel.y);
    }

    public void takeHit() {
        if (health > 0) {
            health--;
        }
    }


}
