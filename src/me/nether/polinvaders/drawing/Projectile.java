package me.nether.polinvaders.drawing;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.utils.MathUtils;

import java.awt.*;
import java.awt.geom.Point2D;

public class Projectile extends ImageComponent {

    public double theta = 0;
    public int maxHits = 1, doneHits = 0;

    public ImageComponent lastHit;

    public Projectile(float x, float y, int width, int height, float xSpeed, float ySpeed) {
        super(x, y, width, height, "projectile.png");
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    @Override
    public void draw(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();
        theta = Math.PI / 2 + Math.atan(this.ySpeed / this.xSpeed) + (this.xSpeed < 0 ? Math.PI : 0);
        g2.rotate(theta, this.x, this.y);
        g2.drawImage(this.img,
                (int) (this.x - this.width / 2),
                (int) (this.y - this.height / 2),
                (int) this.width,
                (int) this.height,
                null);
        g2.rotate(-theta, this.x, this.y);

    }

    @Override
    public void onUpdate() {
        if (this.y < Main.HEIGHT - Main.HEIGHT * Main.DISPLAY.currentLevel.player.bulletsRange)
            toDelete = true;
        else
            super.onUpdate();
//        this.xSpeed = 5 * (float) Math.sin(this.y/20);
    }

    @Override
    public Point2D[] getBounds() {
        Point2D points[] = super.getBounds();

        return new Point2D[]{
                MathUtils.rotatePoint(theta, points[0], x, y),
                MathUtils.rotatePoint(theta, points[1], x, y),
                MathUtils.rotatePoint(theta, points[2], x, y),
                MathUtils.rotatePoint(theta, points[3], x, y)
        };
    }
}
