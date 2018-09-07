package me.nether.polinvaders.levels.casta;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.Entity;
import me.nether.polinvaders.drawing.ImageComponent;
import me.nether.polinvaders.drawing.Player;
import me.nether.polinvaders.drawing.Statistic;
import me.nether.polinvaders.levels.AbstractLevel;
import me.nether.polinvaders.menu.types.Pause;
import me.nether.polinvaders.menu.types.Shop;
import me.nether.polinvaders.utils.ColorUtils;
import me.nether.polinvaders.utils.MathUtils;
import me.nether.polinvaders.utils.RenderUtils;
import me.nether.polinvaders.utils.Timer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.stream.Collectors;

public class LottaAllaCastaLevel extends AbstractLevel {

    Timer buffTimer = new Timer();
    Timer gentletimer = new Timer();

    @Override
    public void init() {
        this.addComponent(new ImageComponent(Main.WIDTH / 2, 0, Main.WIDTH, Main.HEIGHT * 2, "bg.png") {
            @Override
            public void draw(Graphics2D g) {
                this.y += 2f;
                if (y > Main.HEIGHT) y = 0;
                super.draw(g);
            }
        });

        this.addComponent(this.player = new LuigiDiMaio(Main.WIDTH / 2, Main.HEIGHT - 200, 100, 100));

        this.player.bulletsPerSecond = 6;
    }


    public ImageComponent presidenteDellaCamera = null;
    public int count = 0;

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (presidenteDellaCamera == null) {
            count++;
            if (count % 2 == 1) {
                presidenteDellaCamera = new RobertoFico((int) MathUtils.range(100, 900), Main.HEIGHT - Main.HEIGHT * Main.DISPLAY.currentLevel.player.bulletsRange + 100,
                        100, 100, 1);
            } else {
                presidenteDellaCamera = new LauraBoldrini((int) MathUtils.range(100, 900), Main.HEIGHT - Main.HEIGHT * Main.DISPLAY.currentLevel.player.bulletsRange + 100,
                        100, 100, 1);
            }
        }

        if (timer.hasReach(10000)) {
            Entity enemy = new MatteoRenzi((int) MathUtils.range(200, 400), 0, 80, 80, 1 + Main.DISPLAY.currentLevel.score / 1500);
            enemy.ySpeed = (float) MathUtils.range(0.4, 1);

            timer.reset();
        }

        if (gentletimer.hasReach(40000)) {
            Entity gentile = new PaoloGentiloni((int) MathUtils.range(300, 700), 0, 150, 150, 1 + Main.DISPLAY.currentLevel.score / 1500);
            gentile.ySpeed = 0.05f;

            gentletimer.reset();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            new Shop();
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            new Pause();
        }
    }

    Font font = new Font("Century Gothic", Font.PLAIN, 15);
    Font font1 = new Font("Century Gothic", Font.BOLD, 17);

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        for (int j = 0; j < ((LuigiDiMaio) this.player).categories.length; j++) {
            String cat = ((LuigiDiMaio) this.player).categories[j];

            List<Statistic> list = this.player.stats.stream().filter(statistic -> statistic.category.equalsIgnoreCase(cat)).collect(Collectors.toList());

            String string = "\247c" + cat.substring(0, 1).toUpperCase() + cat.substring(1);
            g.setFont(font1);
            RenderUtils.drawStringWithShadow(g, string, Main.WIDTH - RenderUtils.getStringWidth(g, string) - 30 - 200 * j, Main.HEIGHT - 130, 0xffffffff);

            for (int i = 0; i < list.size(); i++) {
                Statistic s = list.get(i);
                g.setFont(font);
                String toDraw = s.name + ": lvl \247a" + s.level;
                RenderUtils.drawString(g, toDraw, Main.WIDTH - RenderUtils.getStringWidth(g, toDraw) - 30 - 200 * j, Main.HEIGHT - 110 + i * 20, 0xffffffff);
            }
        }

        int level = ((LuigiDiMaio)this.player).level;
        float progress = ((LuigiDiMaio)this.player).getProgress();
        g.setColor(ColorUtils.getColor(0xcccccccc));
        g.fillRect(Main.WIDTH - 200, Main.HEIGHT - 170, 190, 18);
        g.setColor(ColorUtils.getColor(0xff16ff7b));
        g.fillRect(Main.WIDTH - 198, Main.HEIGHT - 168, (int) (186 * (progress)), 14);
        String string = String.format("\247flvl: \247b%d", level);
        g.setFont(font1);
        RenderUtils.drawStringWithShadow(g, string, Main.WIDTH - RenderUtils.getStringWidth(g, string) - 205, Main.HEIGHT - 155, 0xffffffff);

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
