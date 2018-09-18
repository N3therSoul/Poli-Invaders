package me.nether.polinvaders.drawing;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.utils.*;

import java.awt.*;
import java.awt.geom.Point2D;

public class Entity extends ImageComponent {

    protected int ticksExisted = 0;

    public int protectionTime = 0;
    public float lifePoints = 5, maxLifePoints = 5;

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
        this.ticksExisted++;

        if (this.toDelete) return;

        if (this.lifePoints <= 0) {
            Main.DISPLAY.currentLevel.score += this.maxLifePoints * 2;
            this.toDelete = true;
            this.onDeath();
            return;
        }

        if (timer.hasReach(protectionTime)) {
            for (ImageComponent object : Main.DISPLAY.OBJECTS) {

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
                            this.lifePoints -= Math.pow((p.width + p.height) / 50, 1.2);
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
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) MathUtils.map(this.ticksExisted, 0, 50, 0, 1));
        g.setComposite(ac);
        super.draw(g);

        this.drawLifeBar(g);

        this.drawHp(g);
    }

    protected void drawLifeBar(Graphics2D g) {
        if (!(this instanceof HasLifeBar)) return;

        g.setFont(font1);
        RenderUtils.drawStringWithShadow(g, "lvl: " + this.level, (int) (this.x - this.width / 2 + 10), (int) (this.y - this.height / 2 - 6), 0xffffffff);
    }

    protected void drawHp(Graphics2D g) {
        if (!(this instanceof ShowLifePoints)) return;

        String hp = "HP: \247a" + (int) this.lifePoints;
        RenderUtils.drawStringWithShadow(g, hp, (int) (this.x + this.width / 2 - RenderUtils.getStringWidth(g, hp) - 2), (int) (this.y - this.height / 2 - 6), 0xffffffff);
    }

    public void setupLifePoints(int lf) {
        this.lifePoints = lf;
        this.maxLifePoints = lf;
    }

    public void onDeath() {

    }


}