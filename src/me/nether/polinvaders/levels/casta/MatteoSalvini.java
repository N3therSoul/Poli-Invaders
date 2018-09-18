package me.nether.polinvaders.levels.casta;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.*;
import me.nether.polinvaders.utils.HasLifeBar;
import me.nether.polinvaders.utils.MathUtils;
import me.nether.polinvaders.utils.ShowLifePoints;
import me.nether.polinvaders.utils.Timer;

import java.awt.geom.Point2D;

public class MatteoSalvini extends EntityBoss implements HasLifeBar, ShowLifePoints {

    private final LuigiDiMaio player;

    private final float oldSpeed, oldSize, oldRange;

    public MatteoSalvini(int x, int y, int width, int height, int level) {
        super(x, y, width, height, "matteosalvini.png");
        this.level = level;
        this.setupLifePoints(1000 * level);
        timer = new Timer();

        player = (LuigiDiMaio) Main.DISPLAY.currentLevel.player;
        oldSpeed = player.attackSpeed.value;
        oldSize = player.bulletSize.value;
        oldRange = player.bulletsRange;
        player.playerSpeed.value /= 2;

        player.bulletSize.value = 0.5f;
        player.attackSpeed.value = 10f;
        player.width /= 4;
        player.height /= 4;
        player.canMoveY = true;
        player.bulletsRange = 1;
    }

    private Timer timer;

    public static int count;
    public static int count2;
    int sign = 1;

    @Override
    public void onUpdate() {
        this.handleDamage();

        if (sign > 1) {
            this.xSpeed = 1f * (float) Math.cos(count2 / 60.f);
            this.ySpeed = 1f * (float) Math.sin(count2 / 60.f);
        } else {
            this.xSpeed = 0;
            this.ySpeed = 0;
        }

        if (count2 % 60 == 0) {
            count = 0;
        }

        if (count2 % 360 == 0) {
            sign++;
            if (sign > 4) sign = 0;
        }

        int circle = 1;
        int repetition = 1;
        int ms = 200;

        float speed = 1;

        switch (sign) {
            case 0:
                circle = 8;
                repetition = 20;
                ms = 400;
                speed = 4;
                break;
            case 1:
                circle = 12;
                repetition = 50;
                ms = 50 + 100 * count;
                speed = 8 / (count + 1);
                break;
            case 2:
                circle = 12;
                repetition = 4;
                ms = 600;
                speed = 4;
                break;
            case 3:
                circle = 12;
                repetition = 30;
                ms = 50 + 75 * count;
                speed = 3;
                break;
            case 4:
                circle = 3;
                repetition = 80;
                ms = 120;
                break;
        }


        if (timer.hasReach(ms) && count < repetition) {
            int n = 360 / circle;
            int phase = count * repetition / (sign + 2);

            for (int i = phase; i < 360 + phase; i += n) {
                float sin = (float) Math.sin(Math.toRadians(i));
                float cos = (float) Math.cos(Math.toRadians(i));
                int x1 = (int) (this.x);
                int y1 = (int) (this.y);
                float xs = sin;
                float ys = cos;

                Main.DISPLAY.currentLevel.addComponent(new LogoLega(x1, y1, xs * speed, ys * speed));
            }
            timer.reset();
            count++;
        }
        count2++;
    }

    private class LogoLega extends ImageComponent {

        public LogoLega(float x, float y, float xSpeed, float ySpeed) {
            super(x, y, 25, 25, "flattax.png");
            this.xSpeed = xSpeed;
            this.ySpeed = ySpeed;
        }
    }

    private void handleDamage() {
        if (this.toDelete) return;

        if (this.lifePoints <= 0) {
            Main.DISPLAY.currentLevel.score += this.maxLifePoints * 2;
            this.toDelete = true;
            this.onDeath();
            return;
        }

        for (int i = Main.DISPLAY.OBJECTS.size() - 1; i >= 0; i--) {
            if (i > Main.DISPLAY.OBJECTS.size() - 1) continue;
            AbstractComponent object = Main.DISPLAY.OBJECTS.get(i);
            if (object instanceof Projectile) {
                Projectile p = (Projectile) object;
                if (p.lastHit == this) continue;
                for (Point2D point2D : p.getBounds()) {
                    if (isInsideRect((float) point2D.getX(), (float) point2D.getY())) {
                        p.doneHits++;
                        p.toDelete = true;
                        p.lastHit = this;

                        this.lifePoints -= Math.max(1, 500 / MathUtils.distance(this.x, this.y, player.x, player.y));
                        break;
                    }
                }
            }

            if (object instanceof LogoLega) {
                LogoLega p = (LogoLega) object;

                for (Point2D point2D : p.getBounds()) {
                    if (player.isInsideRect((float) point2D.getX(), (float) point2D.getY())) {

                        ((LottaAllaCastaLevel) Main.DISPLAY.currentLevel).loss += 350;
                        p.toDelete = true;
                        break;
                    }
                }
            }
        }
        super.onUpdate();
    }

    @Override
    public void onDeath() {
        super.onDeath();

        player.width *= 4;
        player.height *= 4;

        player.playerSpeed.value *= 2;

        player.attackSpeed.value = oldSpeed;
        player.bulletSize.value = oldSize;
        player.canMoveY = false;
        player.bulletsRange = oldRange;

        Main.DISPLAY.OBJECTS.removeIf(i -> i instanceof LogoLega);
    }
}
