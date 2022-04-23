import processing.core.PApplet;
import processing.core.PVector;

public abstract class Powerup {

    PApplet sketch;
    PVector position;
    boolean active = true;

    public abstract void drawPowerup();
}
