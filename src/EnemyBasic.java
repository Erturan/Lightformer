import processing.core.PApplet;
import processing.core.PVector;

public class EnemyBasic extends Enemy {

    public EnemyBasic(PApplet sketch, PVector pos) {
        this.sketch = sketch;
        this.pos = pos;
        sizeX = 30;
        sizeY = 30;
        vel = new PVector(-1, 0);
        maxXVel = 1;
    }

    public void integrate(Level currentLevel) {
        boolean collidesDown = currentLevel.collidesYDown(this);

        if (vel.y > 0 && collidesDown) {
            vel.y = 0;
        }
        pos.add(vel);

        PVector acceleration = new PVector();

        acceleration.y = Main.gravity.y;

        /*if (movingLeft) {
            acceleration.x = -1;
        }
        if (movingRight) {
            acceleration.x = 1;
        }*/



        //Basic enemy does not jump
        /*if (jumping && collidesDown) {
            System.out.println("Jump");
            acceleration.y = -3;
            //touchingGround = false;
        }*/

        vel.add(acceleration);

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
    }



    public void draw(float offset) {
        if (alive) {
            sketch.fill(0,255,0);
            sketch.circle(pos.x - offset + sketch.displayWidth / 4, pos.y, sizeX);
        }

    }
}
