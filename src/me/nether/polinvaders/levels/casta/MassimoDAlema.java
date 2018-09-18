package me.nether.polinvaders.levels.casta;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.Entity;
import me.nether.polinvaders.utils.HasLifeBar;
import me.nether.polinvaders.utils.MathUtils;
import me.nether.polinvaders.utils.RenderUtils;
import me.nether.polinvaders.utils.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Comparator;

public class MassimoDAlema extends Entity implements HasLifeBar {

    public final Timer timer, timer2;
    private final Image occhi;

    private float maxHpGain;
    private int timesUp;
    private float initWidth, initHeight;

    public MassimoDAlema(float x, float y, int width, int height, int level) {
        super(x, y, width, height, "massimodalema.png");
        initWidth = width;
        initHeight = height;

        timer = new Timer();
        timer2 = new Timer();

        this.maxHpGain = 500 * this.level;
        this.setupLifePoints(30 * level + 1);
        this.level = level;

        ImageIcon icon = new ImageIcon("assets/" + "massimodalema_occhi.png");
        occhi = icon.getImage();
    }

    @Override
    public void onUpdate() {

        float pos[] = this.posFunc();
        this.x = pos[0];
        this.y = pos[1];

        if (true) {
            Main.DISPLAY.OBJECTS.stream()
                    .filter(i -> i instanceof Entity && !(i instanceof MassimoDAlema)).map(i -> (Entity) i)
//                    .filter(e -> e.x > 200 && e.x < Main.WIDTH - 200 && e.y > 200 && e.y < Main.HEIGHT - 200)
                    .sorted(Comparator.comparingDouble(e -> MathUtils.distance(x, y, e.x, e.y)))
                    .findFirst().ifPresent(e -> {
//                this.xAcc -= 1 / (e.x - this.x) / 50;
//                this.yAcc -= 1 / (e.y - this.y) / 50;
            });

//            this.xSpeed = (float) MathUtils.range(0, 5);
//            this.xSpeed *= Math.signum(Main.WIDTH / 2 - this.x);
//
//            this.ySpeed = (float) MathUtils.range(0, 5);
//            this.xSpeed *= Math.signum(Main.HEIGHT / 2 - this.y);
        }

        float multiplier = (float) MathUtils.map(maxLifePoints, 0, maxHpGain + 30 * level + 1, 1, level + 1);

        this.width = this.initWidth * multiplier;
        this.height = this.initHeight * multiplier;

        timesUp = 10000;

        if (this.level == 0) return;

        float diffX = Math.signum(Main.WIDTH / 2 - this.x);
        float diffY = Math.signum(Main.HEIGHT / 2 - this.y);

        if (this.maxLifePoints < maxHpGain) {
//            this.xAcc = (float) (Math.signum(diffX) * Math.max(0.5, Math.abs(1 / diffX / 20)));
//            this.yAcc = (float) (Math.signum(diffY) * Math.max(0.5, Math.abs(1 / diffY / 20)));

            timer.reset();

            Main.DISPLAY.currentLevel.getComponents().stream()
                    .filter(i -> i instanceof Entity && !(i instanceof MassimoDAlema))
                    .map(e -> (Entity) e).filter(e -> !e.toDelete)
                    .forEach(e -> {
                        boolean collision = Arrays.asList(e.getBounds()).stream().anyMatch(p -> this.isInsideRect((float) p.getX(), (float) p.getY()));

                        if (collision) {
                            if (e.lifePoints > 1) {
                                e.lifePoints -= 0.2f * this.level;

                                this.xSpeed = 0;
                                this.ySpeed = 0;

                                this.maxLifePoints += 0.2f * this.level * 1.2f;
                                this.lifePoints += 0.2f * this.level * 1.2f;
                            } else {
                                e.toDelete = true;
                            }
                        }
                    });

        } else {
            diffY = Math.signum(Main.HEIGHT * (1 - Main.DISPLAY.currentLevel.player.bulletsRange) - this.y);

            this.xAcc = 0;
            this.yAcc = 0;
            this.xSpeed = diffX;
            this.ySpeed = diffY;

            if (timer.hasReach(timesUp)) {
                if (timer2.hasReach(50)) {
                    Entity mini = new Entity((float) MathUtils.range(x - width / 2, x + width / 2), y, 30, 30, "massimodalema.png");
                    mini.ySpeed = (float) MathUtils.range(1, 5);
                    mini.lifePoints = this.level;
                    this.lifePoints -= this.level;

                    timer2.reset();
                }
            }
        }

        super.onUpdate();
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        Color color = new Color(255, 0, 0, (int) MathUtils.map(timer.getTimePassed(), 0, 10000, 0, 155));
        Color eyes = new Color(255, 0, 0, (int) MathUtils.map(timer.getTimePassed(), 0, 10000, 0, 255));


        BufferedImage img = RenderUtils.dye(RenderUtils.toBufferedImage(this.img), color);
        BufferedImage img_eyes = RenderUtils.dye(RenderUtils.toBufferedImage(occhi), eyes);

        g.drawImage(img, (int) (this.x - this.width / 2), (int) (this.y - this.height / 2), (int) width, (int) height, null);
        g.drawImage(img_eyes, (int) (this.x - this.width / 2), (int) (this.y - this.height / 2), (int) width, (int) height, null);

        String hp = "\247a" + (int) this.lifePoints;
        RenderUtils.drawStringWithShadow(g, hp, (int) (this.x + this.width / 2 - RenderUtils.getStringWidth(g, hp) - 2), (int) (this.y - this.height / 2 - 6), 0xffffffff);

        float pos[] = posFunc();
        g.fillOval((int) pos[0], (int) pos[1], 10, 10);

//        for (int i = 0; i < 500; i++) {
//            int x = i * 4;
//            g.setColor(new Color(0, (int) MathUtils.map(x, 0, 2000, 0, 255), 0, 255));
//            int y = (int) ((x > 1000 ? 1 : -1) * Math.abs(250 * Math.sin(0.01 / Math.PI * (x > 1000 ? 1000 - (x - 1000) : x))));
//            g.fillOval(x > 1000 ? 1000 - (x - 1000) : x, y + Main.HEIGHT / 2, 10, 10);
//        }
    }

    @Override
    public void onDeath() {
        super.onDeath();
    }

    private float[] posFunc() {
        xSpeed = 2;
        int xC = 200;
        int yC = (int) ((1 - Main.DISPLAY.currentLevel.player.bulletsRange) * Main.HEIGHT);

        int length = Main.WIDTH - 400;
        int height = 500;

        float n = (float) (2 * Math.PI / 500);
        int m = height / 2;

        int x = (int) (this.ticksExisted * xSpeed) % (length * 2);

        System.out.println(x);

        if (x < length) {
            return new float[]{x + xC, m * (float) Math.abs(Math.sin(n * x)) + yC};
        } else {
            return new float[]{length - (x - length) + xC, -m * (float) Math.abs(Math.sin(n * x)) + yC};
        }
    }
}
