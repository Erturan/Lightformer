import processing.core.PApplet;
import processing.core.PVector;

public abstract class Powerup {

    PApplet sketch;
    PVector position;
    boolean active = true;
    final int radius = 100;
    final int halfRadius = radius / 2;

    public enum PowerupType {
        COIN,
        HEALTH,
        RECHARGE,
        SPEED
    }

    public abstract void draw(float offset);

    public void checkCollision(Player player) {
        if (active) {
            //First check if the player's X and Y positions are each within 200 pixels of the powerup
            if (Math.abs(player.pos.x - position.x) < 200 && Math.abs(player.pos.y - position.y) < 200) {
                //Calculate distance vector, check if its magnitude is within the tolerance
                float tolerance = player.halfSizeX + halfRadius;
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
