import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static processing.core.PConstants.CENTER;

public class EnemyBasic extends Enemy {

    static int basicWidth = 50;
    static int basicHeight = 80;

    public EnemyBasic(PApplet sketch, PVector pos) {
        this.sketch = sketch;
        this.pos = pos;
        //sizeX = 50;
        //sizeY = 80;
        vel = new PVector(-1, 0);
        maxXVel = 1;
        sizeX = basicWidth;
        sizeY = basicHeight;
    }

    public void integrate(Level currentLevel) {
        boolean collidesDown = currentLevel.collidesYDown(this);

        stepCount++;

        if (vel.y > 0 && collidesDown) {
            vel.y = 0;
        }
        pos.add(vel);
        PVector acceleration = new PVector();
        acceleration.y = Main.gravity.y;

        vel.add(acceleration);

        if (vel.x > maxXVel) {
            vel.x = maxXVel;
        }
        if (vel.x < -maxXVel) {
            vel.x = -maxXVel;
        }

        //System.out.println("Basic sizeY " + sizeY);

        if (vel.y > 0 && currentLevel.collidesYDown(this)) {
            vel.y = 0;
        }

        if (vel.y < 0 && currentLevel.collidesYUp(this)) {
            vel.y = - vel.y;
        }

        if (currentLevel.collidesXLeft(this) || currentLevel.collidesXRight(this)) {
            vel.x = -vel.x;
        }
    }



    public void draw(float offset, PImage img) {
        sketch.imageMode(CENTER);
        if (alive) {
            //sketch.fill(0,255,0);
            sketch.image(img, pos.x - offset + sketch.displayWidth / 4, pos.y);
            //sketch.circle(pos.x - offset + sketch.displayWidth / 4, pos.y, sizeX);
        }

    }
}
