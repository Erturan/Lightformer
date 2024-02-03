import processing.core.PApplet;
import processing.core.PVector;

public class PowerupHealth extends Powerup {

    public PowerupHealth(PApplet sketch, PVector position) {
        this.sketch = sketch;
        this.position = position;
    }

    public void activatePowerup(Player player) {
        player.health++;
    }

    @Override
    public void draw(float offset) {
        sketch.image(Main.imgPowerupHealth, position.x - offset + Level.playerScreenXPos, position.y);
    }
}
