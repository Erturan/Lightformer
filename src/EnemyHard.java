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
        //System.out.println("integrating");
        //System.out.println(vel.x);
        boolean collidesDown = currentLevel.collidesYDown(this);
        PVector acceleration = new PVector();
        acceleration.y = Main.gravity.y;

        stepCount++;

        if (!collidesDown && vel.y == 0) {
            vel.x = -vel.x;
            collidesDown = true;
        }

        if (vel.y > 0 && collidesDown) {
            vel.y = 0;
        }


        pos.add(vel);


        //Get Player direction
        //float xCmp = pos.x - Player.player.pos.x;
        //if (xCmp > 0 && vel.x > 0) {
        //    vel.x = -vel.x;
        //} else if (xCmp < 0 && vel.x < 0) {
        //    vel.x = -vel.x;
        //}

        //System.out.println("Hard sizeY " + sizeY);




        vel.add(acceleration);

        if (vel.x > maxXVel) {
            vel.x = maxXVel;
        }
        if (vel.x < -maxXVel) {
            vel.x = -maxXVel;
        }

        if (vel.y > 0 && collidesDown) {
            vel.y = 0;
        }

        if (vel.y < 0 && currentLevel.collidesYUp(this)) {
            vel.y = - vel.y;
        }

        if (currentLevel.collidesXLeft(this) || currentLevel.collidesXRight(this)) {
            vel.x = -vel.x;
        }

        //Look for player in range(3/4 displaywidth)

        float distance = PVector.sub(pos, Player.player.pos).mag();
        //System.out.println(distance);
        if (distance < 1500) {
            //Within range, can throw new projectile
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
                System.out.println("Integrating bullet");
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
            //sketch.fill(0, 0, 255);
            sketch.image(img, pos.x - offset + sketch.displayWidth / 4, pos.y);
            //sketch.image(img, pos.x - offset + sketch.displayWidth / 4, pos.y);

            sketch.pushStyle();
            sketch.fill(0, 0, 0);
            System.out.println(bullets.size());
            for (Bullet bullet: bullets) {
                if (bullet.active) {
                    bullet.drawBullet(offset);
                }
            }
            sketch.popStyle();
            //sketch.circle(pos.x - offset + sketch.displayWidth / 4, pos.y, sizeX);
        }
    }
}
