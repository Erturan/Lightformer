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
    }

    public void integrate(Level currentLevel) {
        boolean collidesDown = !dying && currentLevel.collidesYDown(this);
        PVector acceleration = new PVector();
        acceleration.y = Main.gravity.y;
        stepCount++;

        if (!collidesDown && vel.y == 0 && !dying) {
            vel.x = -vel.x;
            collidesDown = true;
        }

        if (vel.y > 0 && collidesDown) {
            vel.y = 0;
            adjustY();
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
            adjustY();
        }

        //Hard collisions: bounce off walls, ceilings
        if (vel.y < 0 && currentLevel.collidesYUp(this)) {
            vel.y = - vel.y;
        }

        if (currentLevel.collidesXLeft(this)) {
            vel.x = -vel.x;
            adjustXLeft();
        } else if (currentLevel.collidesXRight(this)) {
            vel.x = -vel.x;
            adjustXRight();
        }

        //Look for player in range(3/4 displaywidth)
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

    public void draw(float offset, PImage img) {
        if (alive) {
            sketch.image(img, pos.x - offset + sketch.displayWidth / 4f, pos.y);
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
