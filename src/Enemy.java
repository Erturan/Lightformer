import processing.core.PImage;
import processing.core.PVector;

public abstract class Enemy extends Character {

    int stepCount = 0;

    boolean alive = true;
    boolean spawned = false;

    public void integrate(Level currentLevel) {

    }

    public void checkSpawn() {
        if (!spawned && Math.abs(pos.x - Player.player.pos.x) < sketch.displayWidth) {
            spawned = true;
        }
    }

    public void checkPlayerCollision(Player player) {
        if (alive) {
            float tolerance = player.sizeX / 2f + sizeX / 2f;
            PVector distance = PVector.sub(player.pos, pos);
            //First check the X distance. Only calculate the true distance if within the tolerance
            if (distance.x < tolerance && distance.mag() < tolerance) {
                player.takeHit();
                alive = false;
            }
        }

    }

    public abstract void draw(float offset, PImage img);


}
