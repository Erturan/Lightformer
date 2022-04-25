import processing.core.PApplet;
import processing.core.PVector;

public class PowerupRecharge extends Powerup {

    public PowerupRecharge(PApplet sketch, PVector position) {
        this.sketch = sketch;
        this.position = position;
    }

    public void activatePowerup(Player player) {
        player.lightRadius += 250;
    }

    @Override
    public void drawPowerup(float offset) {
        if (active) {
            sketch.fill(255,0,0);
            sketch.circle(position.x - offset + sketch.displayWidth / 4, position.y, 10);
        }
    }
}
