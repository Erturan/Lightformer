import processing.core.PApplet;
import processing.core.PVector;

public class PowerupSpeed extends Powerup {

    public PowerupSpeed(PApplet sketch, PVector position) {
        this.sketch = sketch;
        this.position = position;
    }

    public void activatePowerup(Player player) {
        player.addSpeedBoost();
    }

    @Override
    public void drawPowerup(float offset) {
        if (active) {
            sketch.fill(255,0,0);
            sketch.circle(position.x - offset + sketch.displayWidth / 4f, position.y, 10);
        }
    }
}
