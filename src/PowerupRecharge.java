import processing.core.PApplet;
import processing.core.PVector;

public class PowerupRecharge extends Powerup {

    public PowerupRecharge(PApplet sketch, PVector position) {
        this.sketch = sketch;
        this.position = position;
    }

    @Override
    public void drawPowerup() {
        if (active) {
            sketch.fill(255,0,0);
            sketch.circle(position.x, position.y, 10);
        }
    }
}
