import processing.core.PApplet;
import processing.core.PVector;

public class PowerupCoin extends Powerup {
    final int coinExtent = 20;
    public PowerupCoin(PApplet sketch, PVector position) {
        this.sketch = sketch;
        this.position = position;
    }

    public void activatePowerup(Player player) {
        player.coinBalance++;
    }

    @Override
    public void draw(float offset) {
        sketch.pushStyle();
        sketch.fill(255, 223, 0);
        sketch.circle(position.x - offset + sketch.displayWidth / 4f, position.y, coinExtent);
        sketch.popStyle();
    }
}
