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
    public void drawPowerup(float offset) {
        if (active) {
            sketch.fill(0,255,0);
            sketch.circle(position.x - offset + sketch.displayWidth / 4, position.y, 10);
        }

        //Character: 1000 x
        //Powerup 1000 x
        //Should appear at 960

        //Character: 1500
        //Powerup 1000
        //Should appear at 460
    }
}
