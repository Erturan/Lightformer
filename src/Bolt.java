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

    public void updateBolt(PVector pos, PVector target, Level currentLevel) {
        startPos = pos;
        PVector directionUnit = PVector.sub(target, startPos).normalize();
        direction = PVector.mult(directionUnit, boltLength);
        end = PVector.add(Player.player.pos, direction);

        PVector curPos = new PVector(startPos.x, startPos.y);
        for (int i = 0; i < boltLength; i++) {
            System.out.println(i);
            System.out.println(directionUnit.x);
            System.out.println(directionUnit.y);
            curPos.add(directionUnit);
            if (currentLevel.checkCellBlocked(Level.getCellCoordsFromPos(curPos))) {
                end = new PVector(curPos.x, curPos.y);
                break;
            }
        }
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
                    enemy.dying = true;
                }
            }
        }
    }

    public void drawBolt() {
        float addition = Player.playerWidth / 2;
        if (Player.player.vel.x < 0) {
            addition = -addition;
        }
        sketch.line(Player.player.pos.x - Player.player.pos.x + addition + sketch.displayWidth / 4f, Player.player.pos.y, end.x - Player.player.pos.x + sketch.displayWidth / 4f, end.y);
    }

}
