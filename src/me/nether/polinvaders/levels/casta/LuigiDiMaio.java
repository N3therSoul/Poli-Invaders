package me.nether.polinvaders.levels.casta;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

public class LuigiDiMaio extends Player {

    public Statistic playerSpeed;
    public Statistic bulletSpeed;
    public Statistic attackSpeed;
    public Statistic bulletSize;
    public Statistic magnetForce;
    public Statistic angle;
    public Statistic bombRange;
    public Statistic bombStorage;
    public Statistic bulletsHp;

    public String categories[] = {"player", "bullets", "buffs"};

    public int level = 1;
    public int spendablePoints = 0;

    public LuigiDiMaio(int x, int y, int width, int height) {
        super(x, y, width, height, "luigidimaio.png");

        this.stats.add(attackSpeed = new Statistic("Attack Speed", 4, 4, 60, 20, "player"));
        this.stats.add(bulletSize = new Statistic("Bullet Size", 1, 0.2f, 2, 5, "bullets"));
        this.stats.add(magnetForce = new Statistic("Magnetic Force", 0, 0, 300, 4, "player"));
        this.stats.add(angle = new Statistic("Proj. Angle", 0, 0, 20, 3, "bullets"));
        this.stats.add(bombRange = new Statistic("Bomb Range", 100, 100, 400, 5, "buffs"));
        this.stats.add(playerSpeed = new Statistic("Player Speed", 5, 3, 15, 5, "player"));
        this.stats.add(bulletSpeed = new Statistic("Bullet Speed", 3, 1, 10, 5, "bullets"));
        this.stats.add(bombStorage = new Statistic("Bomb Storage", 3, 3, 10, 5, "buffs"));
        this.stats.add(bulletsHp = new Statistic("Bullet HPs", 1, 1, 10, 5, "bullets"));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.fixLevel(Main.DISPLAY.currentLevel.score);

        if (keys[KeyEvent.VK_A] && !keys[KeyEvent.VK_D]) {
            this.xAcc = -1f;
        } else if (keys[KeyEvent.VK_D] && !keys[KeyEvent.VK_A]) {
            this.xAcc = 1f;
        } else {
            if (Math.abs(this.xSpeed) < 0.5) {
                this.xSpeed = 0;
                this.xAcc = 0;
            } else {
                this.xAcc = -Math.signum(xSpeed) * 0.8f;
            }
        }

        if (Math.abs(this.xSpeed) > this.playerSpeed.value) {
            this.xSpeed = Math.signum(this.xSpeed) * this.playerSpeed.value;
        }

        if (keys[KeyEvent.VK_SPACE]) {
            if (this.bulletsPerSecond != 0 && timer.hasReach(1000 / this.attackSpeed.value)) {
                Projectile p;
                this.projectiles.add(p = new Projectile(
                        this.x,
                        this.y - 20 * bulletSize.value - 24,
                        (int) (16 * bulletSize.value),
                        (int) (40 * bulletSize.value),
                        this.xSpeed * angle.value,
                        -bulletSpeed.value));
                p.maxHits = (int) bulletsHp.value;
                timer.reset();
            }
        }

        this.fixBounds();

//        if (this.magnetForce.value > 0) {
//            float range = this.magnetForce.value / 2;
//
//            List<ImageComponent> list = Main.DISPLAY.currentLevel.getComponents().stream().filter(o -> o instanceof Entity).collect(Collectors.toList());
//            list.forEach(a -> System.out.println("a"));
//            for (int i = Main.DISPLAY.currentLevel.getComponents().size() - 1; i >= 0; i--) {
//                ImageComponent imageComponent = Main.DISPLAY.currentLevel.getComponents().get(i);
//
//                if (!(imageComponent instanceof Entity)) continue;
//
//                float average = (imageComponent.height + imageComponent.width) / 2;
//                if (average > 400) continue;
////                if (MathUtils.distance(imageComponent.x, imageComponent.y, this.x, this.y - 100) < range + average / 2) {
//                float distX = imageComponent.x - this.x;
//                float distY = imageComponent.y - this.y - 100;
//
//                float distance = (float) MathUtils.distance(imageComponent.x, imageComponent.y, this.x, this.y -100);
//
//                if (distance <= 100) {
//                    ((Entity) imageComponent).ySpeed = 0;
//                    ((Entity) imageComponent).xSpeed = 0;
//                } else {
//
//                    imageComponent.xAcc = -Math.signum(distX) * 10000 / (average * distance * distance);
//                    imageComponent.yAcc = -Math.signum(distY) * 10000 / (average * distance * distance);
//                }
//                System.out.println(((Entity) imageComponent).xAcc);
//                }
//            }
//        }
    }

    @Override
    protected void fixBounds() {
        if (this.x - this.width / 4 < 0) {
            this.x = this.width / 4;
        }
        if (this.x + this.width / 4 > Main.MAIN_FRAME.getWidth()) {
            this.x = Main.MAIN_FRAME.getWidth() - this.width / 4;
        }
    }

    @Override
    public void draw(Graphics2D g) {
//        g.setColor(ColorUtils.getColor(0x44ff0000));
//        g.fillOval((int) (this.x - this.magnetForce.value / 2), (int) (this.y - this.magnetForce.value / 2 - 100), (int) this.magnetForce.value, (int) this.magnetForce.value);

        AffineTransform t = new AffineTransform();
        t.translate(35, 0);
        g.transform(t);

        if (this.x < Main.WIDTH / 2) {
            g.transform(t);
            super.draw(g, x - width / 2, y - width / 2, -width, height);
        } else {
            super.draw(g);
        }
    }

    public void fixLevel(int x) {
        if ((int) levelFunc(x) > this.level) {
            this.level++;
            this.spendablePoints = 3;
        }
    }

    private final float n = 32, m = 2;

    public float levelFunc(int x) {
        return (float) (1.f / n * Math.pow(x, 1.f / m)) + 1;
    }

    public float reverseLevelFunc(int y) {
        return (float) Math.pow(n * y, m) - 1;
    }

    public float getProgress() {
        float length = this.reverseLevelFunc(this.level) - this.reverseLevelFunc(this.level - 1);

        System.out.println((this.reverseLevelFunc(this.level - 1) - Main.DISPLAY.currentLevel.score));
        System.out.println(length);

        return -(this.reverseLevelFunc(this.level - 1) - Main.DISPLAY.currentLevel.score) / length;
    }
}
