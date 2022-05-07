import processing.core.PApplet;
import processing.core.PVector;

public class Bullet {
    PVector pos;

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
    }

    public void drawBullet(float offset) {
        sketch.circle(pos.x - offset + sketch.displayWidth / 4, pos.y, 10);
    }

    public void checkBulletHitPlayer() {
        float distance = PVector.sub(pos, Player.player.pos).mag();
        //TODO: Come up with a better way of bullet detection
        if (distance < Player.playerWidth / 2) {
            Player.player.health--;
            active = false;
        }
    }
}
