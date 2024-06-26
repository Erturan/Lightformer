import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class EnemyHard extends Enemy {

    static int hardWidth = 50;
    static int hardHeight = 80;

    ArrayList<Bullet> bullets;
    int shotCounter = 180;

    public EnemyHard(PApplet sketch, PVector pos) {
        this.sketch = sketch;
        this.pos = pos;
        sizeX = 50;
        sizeY = 80;
        vel = new PVector(-2, 0);
        maxXVel = 5;
        bullets = new ArrayList<>();
        updateFractionalSizes();
    }

    public void integrate(Level currentLevel) {
        updateGridPos();
        boolean collidesDown = !dying && currentLevel.collidesYDown(this);
        PVector acceleration = new PVector();
        acceleration.y = currentLevel.gravity.y;
        stepCount++;

        if (!collidesDown && vel.y == 0 && !dying) {
            vel.x = -vel.x;
        }

        if (vel.y > 0 && collidesDown && !dying) {
            vel.y = 0;
        }

        pos.add(vel);
        vel.add(acceleration);

        if (vel.x > maxXVel) {
            vel.x = maxXVel;
        }
        if (vel.x < -maxXVel) {
            vel.x = -maxXVel;
        }

        if (vel.y > 0 && collidesDown) {
            vel.y = 0;
            adjustY(currentLevel);
        }

        //Hard collisions: bounce off walls, ceilings
        if (vel.y < 0 && currentLevel.collidesYUp(this)) {
            vel.y = - vel.y;
        }

        if (currentLevel.collidesXLeft(this)) {
            vel.x = -vel.x;
            adjustXLeft(currentLevel);
        } else if (currentLevel.collidesXRight(this)) {
            vel.x = -vel.x;
            adjustXRight(currentLevel);
        }

        //Integrate existing bullets
        integrateBullets(currentLevel);
        //Look to fire bullet. First check x distance as heuristic, and if facing correct direction
        float xDist = Player.player.pos.x - pos.x;
        if (!dying && Math.abs(xDist) < 1500 && ((vel.x < 0 && xDist < 0) || (vel.x > 0 && xDist > 0))) {
            float distance = PVector.sub(pos, Player.player.pos).mag();
            if (distance < 1500) {
                //Within range, can fire new projectile
                if (shotCounter == 180) {
                    //Ready to fire
                    Bullet bullet = new Bullet(sketch, pos, Player.player.pos);
                    bullets.add(bullet);
                    shotCounter = 0;
                } else {
                    //Loading, 60 frames between shots
                    shotCounter++;
                }
            }
        }

    }

    public void integrateBullets(Level currentLevel) {
        sketch.pushStyle();
        sketch.fill(0, 0, 0);
        for (Bullet bullet: bullets) {
            if (bullet.active) {
                bullet.integrate();
                bullet.checkBulletHitPlayer();
                if (currentLevel.checkFallenOffLevel(bullet)) {
                    bullet.active = false;
                }
                if (currentLevel.collidesYDown(bullet) || currentLevel.collidesYUp(bullet) || currentLevel.collidesXLeft(bullet) || currentLevel.collidesXRight(bullet)) {
                    bullet.active = false;
                }
                bullet.drawBullet(Player.player.pos.x);
            }
        }
        sketch.popStyle();
    }

    public void draw(float offset) {
        if (!dead) {
            if (dying) {
                sketch.image(Main.imgEnemyHardDying, pos.x - offset + Level.playerScreenXPos, pos.y);
            } else {
                if (vel.x < 0) {
                    if (stepCount < stepSwitch) {
                        sketch.image(Main.imgEnemyHard1, pos.x - offset + Level.playerScreenXPos, pos.y);
                    } else {
                        sketch.image(Main.imgEnemyHard2, pos.x - offset + Level.playerScreenXPos, pos.y);
                        if (stepCount == stepReset) {
                            stepCount = 0;
                        }
                    }
                } else {
                    if (stepCount < stepSwitch) {
                        sketch.image(Main.imgEnemyHard1Flipped, pos.x - offset + Level.playerScreenXPos, pos.y);
                    } else {
                        sketch.image(Main.imgEnemyHard2Flipped, pos.x - offset + Level.playerScreenXPos, pos.y);
                        if (stepCount == stepReset) {
                            stepCount = 0;
                        }
                    }
                }
            }
            //Style for bullets- these are drawn  as circles
            sketch.pushStyle();
            sketch.fill(0, 0, 0);
            for (Bullet bullet: bullets) {
                if (bullet.active) {
                    bullet.drawBullet(offset);
                }
            }
            sketch.popStyle();
        }
    }
}
