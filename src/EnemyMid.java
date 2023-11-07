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
        boolean collidesDown = currentLevel.collidesYDown(this);

        pos.add(vel);
        PVector acceleration = new PVector();
        acceleration.y = Main.gravity.y;

        //Get Player direction
        float xCmp = pos.x - Player.player.pos.x;

        if (xCmp > 0) {
            acceleration.x = -1;

        } else {
            acceleration.x = 1;
        }

        //Mid collisions: Bounce off everything
        if (vel.y > 0 && currentLevel.collidesYDown(this)) {
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

    public void draw(float offset, PImage img) {
        if (alive) {
            sketch.fill(0, 0, 255);
            sketch.image(img, pos.x - offset + sketch.displayWidth / 4f, pos.y);
        }
    }
}
