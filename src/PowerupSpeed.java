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
    public void draw(float offset) {
        sketch.image(Main.imgPowerupSpeed, position.x - offset + Level.playerScreenXPos, position.y);
    }
}
