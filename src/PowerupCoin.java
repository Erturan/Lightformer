import processing.core.PApplet;
import processing.core.PVector;

public class PowerupCoin extends Powerup {
    public PowerupCoin(PApplet sketch, PVector position) {
        this.sketch = sketch;
        this.position = position;
    }

    public void activatePowerup(Player player) {
        player.coinBalance++;
    }

    @Override
    public void drawPowerup(float offset) {
        if (active) {
            sketch.fill(0,255,0);
            sketch.circle(position.x - offset + sketch.displayWidth / 4f, position.y, 10);
        }

    }
}