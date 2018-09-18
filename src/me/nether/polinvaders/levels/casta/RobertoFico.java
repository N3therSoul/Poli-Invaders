package me.nether.polinvaders.levels.casta;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.AbstractBuff;
import me.nether.polinvaders.drawing.Entity;
import me.nether.polinvaders.drawing.buffs.*;
import me.nether.polinvaders.utils.HasLifeBar;
import me.nether.polinvaders.utils.MathUtils;
import me.nether.polinvaders.utils.Timer;

public class RobertoFico extends Entity implements HasLifeBar {

    public final Timer timer = new Timer();

    public RobertoFico(float x, float y, int width, int height, int level) {
        super(x, y, width, height, "robertofico.png");
        timer.reset();
        this.level = level;
        this.setupLifePoints(20 * this.level);
    }

    private int nextTimer = (int) MathUtils.range(1500, 7000);

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.toDelete) return;

        if (this.isOutOfBorders()) this.lifePoints = 0;

        if (timer.hasReach(nextTimer)) {
            spawnBuff((int) MathUtils.range(0, 11));
            nextTimer = (int) MathUtils.range(1500, 7000);
            timer.reset();
        }

        double yDifference = this.y - Main.HEIGHT * (1 - Main.DISPLAY.currentLevel.player.bulletsRange);

        this.yAcc = -(float) MathUtils.map(yDifference, 0, Main.HEIGHT, 0, 1);

        if (Math.abs(this.ySpeed) > 4) {
            this.ySpeed = Math.signum(this.ySpeed) * 4;
        }
    }

    @Override
    public void onDeath() {
        ((LottaAllaCastaLevel) Main.DISPLAY.currentLevel).presidenteDellaCamera = null;
    }

    public void spawnBuff(int id) {
        AbstractBuff buff = null;

        switch (id) {
            case 0:
            case 1:
            case 2:
            case 3:
                buff = new SpeedBuff(this.x, this.y,
                        60,
                        60,
                        2,
                        6000);
                break;
            case 4:
            case 5:
                buff = new RangeBuff(this.x, this.y,
                        60,
                        60,
                        1.05f,
                        15000);
                break;
            case 6:
                buff = new AngleBuff(this.x, this.y,
                        60,
                        60,
                        0.05f,
                        5000);
                break;
            case 7:
                buff = new BombBuff(this.x, this.y,
                        60,
                        60,
                        40,
                        60000);
                break;
            case 8:
            case 9:
            case 10:
                buff = new SizeBuff(this.x, this.y,
                        60,
                        60,
                        0.1f,
                        10000);
                break;
        }
        if (buff != null) {
            buff.xSpeed = Math.signum(Main.DISPLAY.currentLevel.player.x - this.x) * Math.abs(Main.DISPLAY.currentLevel.player.x - this.x) * 0.01f;
            buff.ySpeed = Math.abs(Main.DISPLAY.currentLevel.player.y - this.y) * 0.01f;

//            buff.xSpeed = (float) MathUtils.map(Math.abs(Main.DISPLAY.currentLevel.player.x - this.x), 0, Main.WIDTH, 1, 3);
//            buff.ySpeed = (float) MathUtils.map(Math.abs(Main.DISPLAY.currentLevel.player.y - this.y), 0, Main.HEIGHT, 1, 3);

//            buff.xSpeed *= Math.signum(Main.DISPLAY.currentLevel.player.x - this.x);
        }
    }
}