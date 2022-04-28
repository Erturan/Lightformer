import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Bolt {

    PApplet sketch;
    PVector startPos;
    PVector direction;
    PVector end;

    int frame = 0;
    boolean active = true;

    public Bolt(PApplet sketch, PVector startPos, PVector target) {
        this.sketch = sketch;
        this.startPos = startPos;
        this.direction = PVector.sub(target, startPos).normalize().mult(1000);
    }

    public void incFrame() {
        frame++;
        if (frame >= 60) {
            active = false;
        }
    }

    public void checkEnemyCollisions(ArrayList<Enemy> enemies) {
        for (Enemy enemy: enemies) {
            if (startPos.x < enemy.pos.x && enemy.pos.x < end.x) {
                //Tolerance- allow a small range of distances to allow hit detection for more than a single point
                if (Math.abs(PVector.dist(startPos, enemy.pos) + PVector.dist(enemy.pos, end) - PVector.dist(startPos, end)) < 1) {
                    enemy.alive = false;
                }
            }
        }
    }

    public void drawBolt() {
        end = PVector.add(Player.player.pos, direction);
        //System.out.println(end.x + ", " + end.y);
        sketch.line(Player.player.pos.x - Player.player.pos.x + sketch.displayWidth / 4, Player.player.pos.y, end.x - Player.player.pos.x + sketch.displayWidth / 4, end.y);
    }

}
