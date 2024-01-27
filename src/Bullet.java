import processing.core.PApplet;
import processing.core.PVector;

public class Bullet extends Character {
    PVector direction;
    PApplet sketch;
    boolean active = true;

    public Bullet(PApplet sketch, PVector origin, PVector target) {
        this.sketch = sketch;
        pos = origin.copy();
        direction = PVector.sub(target, origin).normalize();
    }

    public void integrate() {
        pos.add(PVector.mult(direction, 2.5f));
        updateGridPos();
    }

    public void drawBullet(float offset) {
        sketch.circle(pos.x - offset + sketch.displayWidth / 4f, pos.y, 10);
    }

    public void checkBulletHitPlayer() {
        float tolerance = Player.playerWidth / 2f;
        PVector distance = PVector.sub(pos, Player.player.pos);
        if (distance.x < tolerance && distance.mag() < tolerance) {
            Player.player.health--;
            active = false;
        }
    }
}
