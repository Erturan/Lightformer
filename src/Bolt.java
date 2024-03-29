import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Bolt {

    PApplet sketch;
    PVector startPos;
    PVector direction;
    PVector end;

    final static int boltThickness = 8;
    final static float lightMultiplerFast = 0.997f;
    final int numSteps = 100;
    final int boltDuration = 60;
    final float hitTolerance = 2f;

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
        PVector step = PVector.mult(directionUnit, boltLength / numSteps);
        direction = PVector.mult(directionUnit, boltLength);
        end = PVector.add(Player.player.pos, direction);

        PVector curPos = new PVector(startPos.x, startPos.y);
        for (int i = 0; i < numSteps; i++) {
            curPos.add(step);
            if (currentLevel.checkCellBlocked(currentLevel.getCellCoordsFromPos(curPos))) {
                end = new PVector(curPos.x, curPos.y);
                break;
            }
        }
    }

    public void incFrame() {
        frame++;
        if (frame >= boltDuration) {
            active = false;
        }
    }

    public void checkEnemyCollisions(ArrayList<Enemy> enemies) {
        for (Enemy enemy: enemies) {
            //Only check enemies with x coords between start and end
            if (startPos.x < enemy.pos.x && enemy.pos.x < end.x || startPos.x >= enemy.pos.x && enemy.pos.x >= end.x) {
                //Tolerance- allow a small range of distances to allow hit detection for more than a single point
                if (Math.abs(PVector.dist(startPos, enemy.pos) + PVector.dist(enemy.pos, end) - PVector.dist(startPos, end)) < hitTolerance) {
                    enemy.dying = true;
                }
            }
        }
    }

    public void drawBolt() {
        float addition = Player.player.halfSizeX;
        if (Player.player.vel.x < 0) {
            addition = -addition;
        }
        sketch.line(Player.player.pos.x - Player.player.pos.x + addition + Level.playerScreenXPos, Player.player.pos.y, end.x - Player.player.pos.x + Level.playerScreenXPos, end.y);
    }

}
