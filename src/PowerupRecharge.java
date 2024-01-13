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
    public void draw(float offset) {
        sketch.image(Main.imgPowerupEnergy, position.x - offset + sketch.displayWidth / 4f, position.y);
    }
}
