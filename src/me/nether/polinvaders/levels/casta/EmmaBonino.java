package me.nether.polinvaders.levels.casta;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.Entity;
import me.nether.polinvaders.utils.HasLifeBar;
import me.nether.polinvaders.utils.Timer;

public class EmmaBonino extends Entity implements HasLifeBar {

    public final Timer timer = new Timer();

    public EmmaBonino(float x, float y, int width, int height, int level) {
        super(x, y, width, height, "emmabonino.png");
        timer.reset();
        this.setupLifePoints(10 * level);
        this.level = level;
    }

    @Override
    public void onDeath() {
        super.onDeath();
        new Turbante(x, y, (int) width, (int) height, level);
    }

    public class Turbante extends Entity {

        public Turbante(float x, float y, int width, int height, int level) {
            super(x, y, width, height, "turbante.png");
            this.level = level;
            this.setupLifePoints(2 * level);
            this.yAcc = 0.3f;
        }

        @Override
        public void onDeath() {
            ((LottaAllaCastaLevel) Main.DISPLAY.currentLevel).loss += 2;
            super.onDeath();
        }
    }
}
