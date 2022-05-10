import processing.core.PImage;
import processing.core.PVector;

public abstract class Enemy extends Character {

    int stepCount = 0;

    boolean alive = true;
    boolean spawned = false;

    public void integrate(Level currentLevel) {

    }

    public void checkSpawn() {
        if (!spawned && Math.abs(pos.x - Player.player.pos.x) < 2000) {
            spawned = true;
        }
    }

    public void checkPlayerCollision(Player player) {
        if (alive) {
            PVector distance = PVector.sub(player.pos, pos);
            if (distance.mag() < player.sizeX / 2 + sizeX / 2) {
                player.takeHit();
                alive = false;

            }
        }

    }

    public abstract void draw(float offset, PImage img);


}
