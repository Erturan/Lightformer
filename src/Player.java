import processing.core.PApplet;
import processing.core.PVector;

public class Player extends Character {

    PApplet sketch;


    int maxXVel = 10;
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
        this.sketch = sketch;
        pos = new PVector(500, 500);
        vel = new PVector(0, 0);
    }

    public void integrate(Level currentLevel) {

        boolean collidesDown = currentLevel.collidesYDown(this);

        if (vel.y > 0 && collidesDown) {
            vel.y = 0;
        }

        //Check if velocity > cell height


        pos.add(vel);

        PVector acceleration = new PVector();
        /*if (pos.y < 800) {
            acceleration.y = Main.gravity.y;
            touchingGround = false;
        } else {
            vel.y = 0;
            touchingGround = true;
        }*/

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

        //System.out.println(vel.y);
    }


}
