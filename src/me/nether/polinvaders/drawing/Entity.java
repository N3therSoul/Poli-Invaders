package me.nether.polinvaders.drawing;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.utils.HasLifeBar;
import me.nether.polinvaders.utils.RenderUtils;
import me.nether.polinvaders.utils.Timer;

import java.awt.*;
import java.awt.geom.Point2D;

public class Entity extends ImageComponent {

    public int protectionTime = 500;
    public int lifePoints = 5, maxLifePoints = 5;

    protected Timer timer = new Timer();

    public int level = 1;

    public Entity(float x, float y, int width, int height, String directory) {
        super(x, y, width, height, directory);
        Main.DISPLAY.currentLevel.addComponent(this);
    }

    public Entity(float x, float y, int width, int height, String directory, int level) {
        this(x, y, width, height, directory);
        this.level = level;
    }

    @Override
    public void onUpdate() {
        if (this.toDelete) return;

        if (this.lifePoints <= 0) {
            Main.DISPLAY.currentLevel.score += this.maxLifePoints;
            this.toDelete = true;
            this.onDeath();
            return;
        }

        if (timer.hasReach(protectionTime)) {
            for (int i = Main.DISPLAY.OBJECTS.size() - 1; i >= 0; i--) {
                if (i > Main.DISPLAY.OBJECTS.size() - 1) continue;
                AbstractComponent object = Main.DISPLAY.OBJECTS.get(i);
                if (object instanceof Projectile) {
                    Projectile p = (Projectile) object;
                    if (p.lastHit == this) continue;
                    for (Point2D point2D : p.getBounds()) {
                        if (point2D.getX() > this.getBounds()[0].getX() && point2D.getX() < this.getBounds()[1].getX() &&
                                point2D.getY() > this.getBounds()[0].getY() && point2D.getY() < this.getBounds()[2].getY()) {
                            p.doneHits++;

                            if (p.doneHits >= p.maxHits) {
                                p.toDelete = true;
                            }
                            p.lastHit = this;
                            this.lifePoints--;
                            return;
                        }
                    }
                }
            }
            super.onUpdate();
        }
    }

    Font font1 = new Font("Century Gothic", Font.BOLD, 17);

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        if(!(this instanceof HasLifeBar)) return;

        g.setFont(font1);
        RenderUtils.drawStringWithShadow(g, "lvl: " + this.level, (int) (this.x - this.width/2 + 10), (int) (this.y - this.height/2 - 6), 0xffffffff);
    }

    public void setupLifePoints(int lf) {
        this.lifePoints = lf;
        this.maxLifePoints = lf;
    }

    public void onDeath() {

    }
}