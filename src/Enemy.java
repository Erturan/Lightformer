import processing.core.PImage;
import processing.core.PVector;

public abstract class Enemy extends Character {

    public enum EnemyType {
        BASIC,
        MID,
        HARD
    }

    boolean spawned = false;
    boolean dead = false;
    boolean dying = false;

    public void integrate(Level currentLevel) {
    }

    public void checkSpawn() {
        if (!spawned && Math.abs(pos.x - Player.player.pos.x) < sketch.displayWidth) {
            spawned = true;
        }
    }

    public void checkPlayerCollision(Player player) {
        if (!dying) {
            float tolerance = player.halfSizeX + halfSizeX;
            PVector distance = PVector.sub(player.pos, pos);
            //First check the X distance. Only calculate the true distance if within the tolerance
            if (distance.x < tolerance && distance.mag() < tolerance) {
                player.takeHit();
                dying = true;
            }
        }
    }

    public abstract void draw(float offset);


}
