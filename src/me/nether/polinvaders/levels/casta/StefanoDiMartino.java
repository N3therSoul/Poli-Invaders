package me.nether.polinvaders.levels.casta;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.Entity;
import me.nether.polinvaders.utils.HasLifeBar;
import me.nether.polinvaders.utils.Timer;

import java.awt.geom.Point2D;

public class StefanoDiMartino extends Entity implements HasLifeBar {

    public final Timer timer = new Timer();

    public StefanoDiMartino(float x, float y, int width, int height, int level) {
        super(x, y, width, height, "stefanodimartino.png");
        timer.reset();
        this.setupLifePoints(4 * level);
        this.level = level;
    }

    @Override
    public void onUpdate() {
        float diffX = this.x - Main.DISPLAY.currentLevel.player.x;
        float diffY = this.y - Main.DISPLAY.currentLevel.player.y;

        this.xSpeed = -Math.signum(diffX) * Math.max(1.5f, diffX/30);
        this.ySpeed = Math.min(4, -diffY / 30);

        for (Point2D p : this.getBounds()) {
            if(Main.DISPLAY.currentLevel.player.isInsideRect((int) p.getX(), (int) p.getY())) {
                ((LottaAllaCastaLevel)Main.DISPLAY.currentLevel).loss += 8;
                this.toDelete = true;
            }
        }

        super.onUpdate();
    }
}
