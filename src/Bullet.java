import processing.core.PApplet;
import processing.core.PVector;

public class Bullet extends Character {
    PVector direction;
    PVector frameShotTravel;
    PApplet sketch;
    boolean active = true;

    public Bullet(PApplet sketch, PVector origin, PVector target) {
        this.sketch = sketch;
        pos = origin.copy();
        direction = PVector.sub(target, origin).normalize();
        frameShotTravel = new PVector();
        PVector.mult(direction, 5, frameShotTravel);
    }

    public void integrate() {
        pos.add(frameShotTravel);
        updateGridPos();
    }

    public void drawBullet(float offset) {
        sketch.circle(pos.x - offset + Level.playerScreenXPos, pos.y, 10);
    }

    public void checkBulletHitPlayer() {
        float tolerance = Player.player.halfSizeX;
        PVector distance = PVector.sub(pos, Player.player.pos);
        if (distance.x < tolerance && distance.mag() < tolerance) {
            Player.player.health--;
            active = false;
        }
    }
}
