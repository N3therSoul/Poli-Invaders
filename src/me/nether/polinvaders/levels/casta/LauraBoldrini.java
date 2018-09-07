package me.nether.polinvaders.levels.casta;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.AbstractBuff;
import me.nether.polinvaders.drawing.Entity;
import me.nether.polinvaders.drawing.buffs.*;
import me.nether.polinvaders.utils.HasLifeBar;
import me.nether.polinvaders.utils.MathUtils;
import me.nether.polinvaders.utils.Timer;

public class LauraBoldrini extends Entity implements HasLifeBar {

    public final Timer timer = new Timer();

    public LauraBoldrini(float x, float y, int width, int height, int level) {
        super(x, y, width, height, "lauraboldrini.png");
        timer.reset();
        this.lifePoints = 40;
        this.maxLifePoints = lifePoints;
        this.level = level;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.toDelete) return;

        if (this.isOutOfBorders()) this.lifePoints = 0;

        if (timer.hasReach(1000)) {
            spawnBuff((int) MathUtils.range(0, 2));
            timer.reset();
        }

        double yDifference = this.y - Main.HEIGHT * (1 - Main.DISPLAY.currentLevel.player.bulletsRange);

        this.yAcc = -(float) MathUtils.map(yDifference, 0, Main.HEIGHT, 0, 2);

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
                buff = new AngleBuff(this.x, this.y,
                        60,
                        60,
                        -1f);
                break;
            case 1:
                buff = new SizeBuff(this.x, this.y,
                        60,
                        60,
                        -0.2f);
                break;
            case 2:
                buff = new SpeedBuff(this.x, this.y,
                        60,
                        60,
                        -2);
                break;
        }
        if (buff != null) {
            buff.duration = 5000;
            buff.xAcc = Math.signum(Main.DISPLAY.currentLevel.player.x - this.x) * Math.abs(Main.DISPLAY.currentLevel.player.x - this.x) * 0.0005f;
            buff.yAcc = Math.abs(Main.DISPLAY.currentLevel.player.y - this.y) * 0.0005f;
            buff.isDebuff = true;
//            buff.xSpeed = (float) MathUtils.map(Math.abs(Main.DISPLAY.currentLevel.player.x - this.x), 0, Main.WIDTH, 1, 3);
//            buff.ySpeed = (float) MathUtils.map(Math.abs(Main.DISPLAY.currentLevel.player.y - this.y), 0, Main.HEIGHT, 1, 3);

//            buff.xSpeed *= Math.signum(Main.DISPLAY.currentLevel.player.x - this.x);
        }
    }
}