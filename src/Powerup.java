import processing.core.PApplet;
import processing.core.PVector;

public abstract class Powerup {

    PApplet sketch;
    PVector position;
    boolean active = true;
    int radius = 100;

    public enum PowerupType  {
        COIN,
        HEALTH,
        RECHARGE,
        SPEED
    }

    public abstract void draw(float offset);

    public void checkCollision(Player player) {
        if (active) {
            if (Math.abs(player.pos.x - position.x) < 200 && Math.abs(player.pos.y - position.y) < 200) {
                float tolerance = player.sizeX / 2f + radius / 2f;
                PVector distance = PVector.sub(position, player.pos);
                if (distance.x < tolerance && distance.mag() < tolerance) {
                    activatePowerup(player);
                    active = false;
                }
            }
        }
    }

    public abstract void activatePowerup(Player player);
}
