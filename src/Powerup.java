import processing.core.PApplet;
import processing.core.PVector;

public abstract class Powerup {

    PApplet sketch;
    PVector position;
    boolean active = true;
    int radius = 100;

    public abstract void drawPowerup(float offset);

    public void checkCollision(Player player) {
        if (active) {
            //TODO: This may be computationally expensive. Instead consider x/y precheck
            if (Math.abs(player.pos.x - position.x) < 200 && Math.abs(player.pos.y - position.y) < 200) {
                PVector distance = PVector.sub(position, player.pos);
                if (distance.mag() < player.sizeX / 2 + radius / 2) {
                    activatePowerup(player);
                    active = false;
                }
            }
        }
    }

    public abstract void activatePowerup(Player player);
}
