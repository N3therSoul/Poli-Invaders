package me.nether.polinvaders.levels.casta;

import me.nether.polinvaders.drawing.Entity;
import me.nether.polinvaders.utils.HasLifeBar;
import me.nether.polinvaders.utils.Timer;

public class PaoloGentiloni extends Entity implements HasLifeBar {

    public final Timer timer = new Timer();

    public PaoloGentiloni(float x, float y, int width, int height, int level) {
        super(x, y, width, height, "paologentiloni.png");
        timer.reset();
        this.setupLifePoints(200 * level);
        this.level = level;
    }
}
