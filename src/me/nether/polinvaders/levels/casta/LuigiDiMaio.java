package me.nether.polinvaders.levels.casta;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.*;

import java.awt.*;
import java.awt.event.KeyEvent;

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

    public boolean canMoveY = false;

    public LuigiDiMaio(int x, int y, int width, int height) {
        super(x, y, width, height, "luigidimaio.png");

        this.stats.add(attackSpeed = new Statistic("Attack Speed", 6, 6, 60, 20, "player"));
        this.stats.add(bulletSize = new Statistic("Bullet Size", 1, 1f, 2, 15, "bullets"));
//        this.stats.add(magnetForce = new Statistic("Magnetic Force", 0, 0, 300, 4, "player"));
        this.stats.add(angle = new Statistic("Proj. Angle", 0, 0, 20, 3, "bullets"));
//        this.stats.add(bombRange = new Statistic("Bomb Range", 100, 100, 400, 5, "buffs"));
        this.stats.add(playerSpeed = new Statistic("Player Speed", 7, 3, 25, 5, "player"));
        this.stats.add(bulletSpeed = new Statistic("Bullet Speed", 3, 1, 10, 5, "bullets"));
//        this.stats.add(bombStorage = new Statistic("Bomb Storage", 3, 3, 10, 5, "buffs"));
        this.stats.add(bulletsHp = new Statistic("Bullet HPs", 1, 1, 10, 5, "bullets"));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.fixLevel(Main.DISPLAY.currentLevel.score);

        if (canMoveY) {
            if (keys[KeyEvent.VK_W] && !keys[KeyEvent.VK_S]) {
                this.yAcc = -1f;
            } else if (keys[KeyEvent.VK_S] && !keys[KeyEvent.VK_W]) {
                this.yAcc = 1f;
            } else {
                if (Math.abs(this.xSpeed) < 0.5) {
                    this.ySpeed = 0;
                    this.yAcc = 0;
                } else {
                    this.yAcc = -Math.signum(ySpeed) * 0.8f;
                }
            }

            if (Math.abs(this.ySpeed) > this.playerSpeed.value / 4) {
                this.ySpeed = Math.signum(this.ySpeed) * this.playerSpeed.value / 4;
            }
        } else if(this.y < Main.HEIGHT - 200) {
            this.yAcc = 0.6f;
        } else {
            this.yAcc = 0;
            this.ySpeed = 0;
        }

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

        if (Math.abs(this.xSpeed) > this.playerSpeed.value / 4) {
            this.xSpeed = Math.signum(this.xSpeed) * this.playerSpeed.value / 4;
        }

        if (keys[KeyEvent.VK_SPACE]) {
            if (this.bulletsPerSecond != 0 && timer.hasReach(1000 / this.attackSpeed.value)) {
                Projectile p;
                this.projectiles.add(p = new Projectile(
                        this.x,
                        this.y - 20 * bulletSize.value - 24,
                        (int) (16 * bulletSize.value),
                        (int) (40 * bulletSize.value),
                        this.xSpeed * angle.value / 80,
                        -bulletSpeed.value) {
                    @Override
                    public void onUpdate() {
                        if (this.y < Main.HEIGHT - Main.HEIGHT * Main.DISPLAY.currentLevel.player.bulletsRange)
                            toDelete = true;
                        else
                            super.onUpdate();
                    }
                });
                p.maxHits = (int) bulletsHp.value;
                timer.reset();
            }
        }

        this.fixBounds();
    }

    @Override
    protected void fixBounds() {
        if (this.x - this.width / 4 < 0) {
            this.x = this.width / 4;
        }
        if (this.x + this.width / 4 > Main.MAIN_FRAME.getWidth()) {
            this.x = Main.MAIN_FRAME.getWidth() - this.width / 4;
        }

        if (this.y - this.height / 2 < 0) {
            this.y = this.height / 2;
        }
        if (this.y + 200 > Main.MAIN_FRAME.getHeight()) {
            this.y = Main.MAIN_FRAME.getHeight() - 200;
        }
    }

    @Override
    public void draw(Graphics2D g) {
//        g.fillRect((int) (x - width / 2), (int) (y - height / 2), (int) width, (int) height);
        super.draw(g);
    }

    public void fixLevel(int x) {
        if ((int) levelFunc(x) > this.level) {
            this.level++;
            this.spendablePoints += 3;
        }
    }

    private final float n = 16, m = 2;

    public float levelFunc(int x) {
        return (float) (1.f / n * Math.pow(x, 1.f / m)) + 1;
    }

    public float reverseLevelFunc(int y) {
        return (float) Math.pow(n * y, m) - 1;
    }

    public float getProgress() {
        float length = this.reverseLevelFunc(this.level) - this.reverseLevelFunc(this.level - 1);
        return -(this.reverseLevelFunc(this.level - 1) - Main.DISPLAY.currentLevel.score) / length;
    }

    public Statistic getStatFromName(String statName) {
        return this.stats.stream().filter(s -> s.name.equalsIgnoreCase(statName)).findFirst().get();
    }
}
