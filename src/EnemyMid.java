import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class EnemyMid extends Enemy {

    public EnemyMid(PApplet sketch, PVector pos) {
        this.sketch = sketch;
        this.pos = pos;
        vel = new PVector(-2, 0);
        maxXVel = 5;
    }

    public void integrate(Level currentLevel) {
        //System.out.println(vel.x);
        boolean collidesDown = currentLevel.collidesYDown(this);

        /*if (vel.y > 0 && collidesDown) {
            vel.y = 0;
        }*/
        pos.add(vel);
        PVector acceleration = new PVector();
        acceleration.y = Main.gravity.y;

        //Get Player direction
        float xCmp = pos.x - Player.player.pos.x;
        if (xCmp > 0 && vel.x > 0) {
            vel.x = -vel.x;
        } else if (xCmp < 0 && vel.x < 0) {
            vel.x = -vel.x;
        }

        if (vel.y > 0 && currentLevel.collidesYDown(this)) {
            vel.y = 0;
            acceleration.y = -7;
        }

        vel.add(acceleration);

        if (vel.x > maxXVel) {
            vel.x = maxXVel;
        }
        if (vel.x < -maxXVel) {
            vel.x = -maxXVel;
        }

        if (vel.y < 0 && currentLevel.collidesYUp(this)) {
            vel.y = - vel.y;
        }

        if (currentLevel.collidesXLeft(this) || currentLevel.collidesXRight(this)) {
            vel.x = -vel.x;
        }
    }

    public void draw(float offset, PImage img) {
        if (alive) {
            sketch.fill(0, 0, 255);
            sketch.image(img, pos.x - offset + sketch.displayWidth / 4, pos.y);
            //sketch.circle(pos.x - offset + sketch.displayWidth / 4, pos.y, sizeX);
        }
    }
}
