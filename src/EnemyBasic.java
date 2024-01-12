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
        boolean collidesDown = !dying && currentLevel.collidesYDown(this);

        PVector acceleration = new PVector();
        acceleration.y = Main.gravity.y;
        if (vel.y == 0 && !collidesDown && !dying) {
            vel.y = 0;
            acceleration.y = -7;
        }

        stepCount++;

        if (vel.y > 0 && collidesDown && !dying) {
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
            adjustY();
        }

        if (vel.y < 0 && currentLevel.collidesYUp(this)) {
            vel.y = - vel.y;
        }

        if (currentLevel.collidesXLeft(this)) {
            vel.x = -vel.x;
            adjustXLeft();
        } else if (currentLevel.collidesXRight(this)) {
            vel.x = -vel.x;
            adjustXRight();
        }
    }

    public void draw(float offset) {
        sketch.imageMode(CENTER);
        if (dying) {
            sketch.image(Main.imgEnemyBasicDying,pos.x - offset + sketch.displayWidth / 4f, pos.y);
        } else {
            if (vel.x < 0) {
                if (stepCount < stepSwitch) {
                    sketch.image(Main.imgEnemyBasic1,pos.x - offset + sketch.displayWidth / 4f, pos.y);
                } else {
                    sketch.image(Main.imgEnemyBasic2,pos.x - offset + sketch.displayWidth / 4f, pos.y);
                    if (stepCount == stepReset) {
                        stepCount = 0;
                    }
                }
            } else {
                if (stepCount < stepSwitch) {
                    sketch.image(Main.imgEnemyBasic1Flipped,pos.x - offset + sketch.displayWidth / 4f, pos.y);
                } else {
                    sketch.image(Main.imgEnemyBasic2Flipped,pos.x - offset + sketch.displayWidth / 4f, pos.y);
                    if (stepCount == stepReset) {
                        stepCount = 0;
                    }
                }
            }
        }
    }
}
