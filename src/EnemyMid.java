import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class EnemyMid extends Enemy {

    public EnemyMid(PApplet sketch, PVector pos) {
        this.sketch = sketch;
        this.pos = pos;
        vel = new PVector(-3, 0);
        maxXVel = 5;
    }

    public void integrate(Level currentLevel) {
        boolean collidesDown = !dying && currentLevel.collidesYDown(this);

        pos.add(vel);
        PVector acceleration = new PVector();
        acceleration.y = currentLevel.gravity.y;

        //Get Player direction
        float xCmp = pos.x - Player.player.pos.x;

        if (xCmp > 0) {
            acceleration.x = -1;

        } else {
            acceleration.x = 1;
        }

        //Mid collisions: Bounce off everything
        if (vel.y > 0 && currentLevel.collidesYDown(this) && !dying) {
            vel.y = 0;
            adjustY();
            acceleration.y = -7;
        }

        vel.add(acceleration);

        if (vel.x > 2.5) {
            vel.x = 2.5f;
        }
        if (vel.x < -2.5) {
            vel.x = -2.5f;
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
        if (!dead) {
            sketch.fill(0, 0, 255);
            if (dying) {
                sketch.image(Main.imgEnemyMidDying, pos.x - offset + sketch.displayWidth / 4f, pos.y);
            } else {
                sketch.image(Main.imgEnemyMid, pos.x - offset + sketch.displayWidth / 4f, pos.y);
            }
        }
    }
}
