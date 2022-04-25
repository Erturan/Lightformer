import processing.core.PVector;

public abstract class Enemy extends Character {

    boolean alive = true;

    public void integrate(Level currentLevel) {

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

    public abstract void draw(float offset);


}
