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
        vel = new PVector(-2, -0.1f);
        maxXVel = 2;
        sizeX = basicWidth;
        sizeY = basicHeight;
    }

    public void integrate(Level currentLevel) {
        boolean collidesDown = currentLevel.collidesYDown(this);

        PVector acceleration = new PVector();
        acceleration.y = Main.gravity.y;
        if (vel.y == 0 && !currentLevel.collidesYDown(this)) {
            vel.y = 0;
            acceleration.y = -7;
        }

        stepCount++;

        if (vel.y > 0 && collidesDown) {
            vel.y = 0;
        }
        pos.add(vel);
        vel.add(acceleration);

        if (vel.x > maxXVel) {
            vel.x = maxXVel;
        }
        if (vel.x < -maxXVel) {
            vel.x = -maxXVel;
        }

        //Basic collisions: arrest downwards collisions, bounce off ceilings and walls
        if (vel.y > 0 && collidesDown) {
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
            sketch.image(img, pos.x - offset + sketch.displayWidth / 4, pos.y);
        }
    }
}
