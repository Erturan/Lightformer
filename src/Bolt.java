import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Bolt {

    PApplet sketch;
    PVector startPos;
    PVector direction;
    PVector end;

    int frame = 0;
    int boltLength = 1000;
    boolean active = true;

    public Bolt(PApplet sketch, PVector startPos, PVector target) {
        this.sketch = sketch;
        this.startPos = startPos;
        this.direction = PVector.sub(target, startPos).normalize().mult(boltLength);
        this.end = PVector.add(Player.player.pos, direction);
    }

    public void updateBolt(PVector pos, PVector target) {
        startPos = pos;
        direction = PVector.sub(target, startPos).normalize().mult(boltLength);
        end = PVector.add(Player.player.pos, direction);
    }

    public void incFrame() {
        frame++;
        if (frame >= 60) {
            active = false;
        }
    }

    public void checkEnemyCollisions(ArrayList<Enemy> enemies) {
        for (Enemy enemy: enemies) {
            //Only check enemies with x coords between start and end
            if (startPos.x < enemy.pos.x && enemy.pos.x < end.x || startPos.x >= enemy.pos.x && enemy.pos.x >= end.x) {
                //Tolerance- allow a small range of distances to allow hit detection for more than a single point
                if (Math.abs(PVector.dist(startPos, enemy.pos) + PVector.dist(enemy.pos, end) - PVector.dist(startPos, end)) < 2) {
                    enemy.alive = false;
                }
            }
        }
    }

    public void drawBolt() {
        //end = PVector.add(Player.player.pos, direction);
        float addition = Player.playerWidth / 2;
        if (Player.player.vel.x < 0) {
            addition = -addition;
        }
        sketch.line(Player.player.pos.x - Player.player.pos.x + addition + sketch.displayWidth / 4, Player.player.pos.y, end.x - Player.player.pos.x + sketch.displayWidth / 4, end.y);
    }

}
