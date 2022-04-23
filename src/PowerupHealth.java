import processing.core.PApplet;
import processing.core.PVector;

public class PowerupHealth extends Powerup {

    public PowerupHealth(PApplet sketch, PVector position) {
        this.sketch = sketch;
        this.position = position;
    }

    @Override
    public void drawPowerup() {
        if (active) {
            sketch.fill(0,255,0);
            sketch.circle(position.x, position.y, 10);
        }

    }
}
