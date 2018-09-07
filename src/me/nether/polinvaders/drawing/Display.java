package me.nether.polinvaders.drawing;

import com.darkmagician6.eventapi.EventManager;
import me.nether.polinvaders.Main;
import me.nether.polinvaders.events.EventTick;
import me.nether.polinvaders.levels.casta.LottaAllaCastaLevel;
import me.nether.polinvaders.levels.AbstractLevel;
import me.nether.polinvaders.menu.AbstractMenu;
import me.nether.polinvaders.menu.types.Pause;
import me.nether.polinvaders.utils.HasLifeBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Display extends JPanel implements KeyListener, Runnable {

    public final List<ImageComponent> OBJECTS = new ArrayList<>();

    private final Font FONT_SCORE = new Font("Helvetica Neue", Font.BOLD, 40);
    private final Font FONT_LEVEL = new Font("Helvetica Neue", Font.BOLD, 20);

    public AbstractLevel currentLevel;

    private final int TICKS_PER_SECOND = 60;
    private final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    private final int MAX_FRAMESKIP = 5;

    public void init() {
        currentLevel = new LottaAllaCastaLevel();
    }

    public AbstractMenu currentMenu;

    @Override
    public void keyPressed(KeyEvent e) {
        //TODO: rendere anche player un keylistener
        this.currentLevel.player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

        this.currentLevel.player.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    List<Entity> lifeBars = new ArrayList<>();

    public static float data[];
    public static int size;

    static {
        int radius = 3;
        size = radius * 2 + 1;
        float weight = 1.0f / (size * size);
        data = new float[size * size];

        for (int i = 0; i < data.length; i++) {
            data[i] = weight;
        }
    }

    @Override
    protected void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (this.currentMenu != null && this.currentMenu.useBlur) {
            BufferedImage bufferedImage = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics gg = bufferedImage.getGraphics();
            Main.DISPLAY.paintShit(gg);

            Kernel kernel = new Kernel(size, size, data);
            ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
            bufferedImage = op.filter(bufferedImage, null);

            g.drawImage(bufferedImage, 0, 0, Main.WIDTH, Main.HEIGHT, null);
        } else {
            paintShit(g);
        }

        this.drawHud(g);
    }

    public void paintShit(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        if (this.OBJECTS.size() <= 0) return;

        this.OBJECTS.get(0).draw(g2);

        g.setColor(new Color(255, 0, 0, 200));
        g.fillRect(0, (int) (Main.HEIGHT - Main.HEIGHT * this.currentLevel.player.bulletsRange) - 10, Main.WIDTH, 3);

        for (int i = this.OBJECTS.size() - 1; i >= 1; i--) {
            if (this.OBJECTS.get(i) == null || i > this.OBJECTS.size() - 1) continue;

            if (this.OBJECTS.get(i) instanceof HasLifeBar) {
                lifeBars.add((Entity) this.OBJECTS.get(i));
            }

            if (this.OBJECTS.get(i).toDelete || this.OBJECTS.get(i).isOutOfBorders()) {
                this.OBJECTS.remove(i);
            } else
                this.OBJECTS.get(i).draw(g2);
        }

        this.OBJECTS.addAll(this.currentLevel.player.projectiles);
        this.currentLevel.player.projectiles.clear();

        for (int i = this.lifeBars.size() - 1; i >= 0; i--) {
            if (this.lifeBars.get(i) == null || i > this.lifeBars.size() - 1) continue;
            Entity e = this.lifeBars.get(i);

            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect((int) (e.x - e.width / 2) + 4, (int) (e.y - e.height / 2), (int) (e.width) - 8, 8);

            float healthPercentage = e.lifePoints < 0 ? 0 : (float) e.lifePoints / e.maxLifePoints;

            g.setColor(new Color(1 - 1 * healthPercentage, 1 * healthPercentage, 0, 0.8f));
            g.fillRect((int) (e.x - e.width / 2) + 6, (int) (e.y - e.height / 2), (int) ((e.width - 12) * healthPercentage), 6);
        }

        this.lifeBars.clear();
    }

    public void drawHud(Graphics2D g) {
        Main.MAIN_FRAME.setBounds(Main.MAIN_FRAME.getX(), Main.MAIN_FRAME.getY(), Main.WIDTH, Main.HEIGHT);

        g.setFont(FONT_SCORE);
        g.setColor(Color.white);
        g.drawString("" + this.currentLevel.score, 14, 40);
        g.setFont(FONT_LEVEL);

        List<AbstractBuff> buffs = this.currentLevel.player.buffs.stream().filter(b -> !b.isDebuff).collect(Collectors.toList());
        List<AbstractBuff> debuffs = this.currentLevel.player.buffs.stream().filter(b -> b.isDebuff).collect(Collectors.toList());

        g.setColor(new Color(99, 99, 99, 155));
        g.fillRoundRect(2, Main.HEIGHT - 150, Main.WIDTH - 10, 118, 20, 20);

        g.setColor(new Color(0, 0, 0, 155));
        g.fillRect(0, Main.HEIGHT - 104, buffs.size() * 40 + 2, Main.HEIGHT);
        g.fillRect(0, Main.HEIGHT - 184, debuffs.size() * 40 + 2, Main.HEIGHT - 104);

        for (int i = 0; i < buffs.size(); i++) {
            AbstractBuff buff = buffs.get(i);
            buff.draw(g, 4 + i * 40, Main.HEIGHT - 76, 36, 36);
            g.setColor(new Color(0, 0, 0, 155));
            g.setColor(Color.black);
            g.drawString(buff.currentStacks + "", 17 + i * 40, Main.HEIGHT - 47);

            if (buff instanceof ActivatableBuff) {
                g.setColor(Color.green);
                g.drawString(((ActivatableBuff) buff).uses + "", 16 + i * 40, Main.HEIGHT - 48);
            } else {
                g.setColor(Color.yellow);
                g.drawString(buff.currentStacks + "", 16 + i * 40, Main.HEIGHT - 48);
            }

            g.setColor(Color.white);
            g.drawString(String.format("%.0fs", (buff.duration - buff.timer.getTimePassed()) / 1000.f), 8 + i * 40, Main.HEIGHT - 80);
        }

        for (int i = 0; i < debuffs.size(); i++) {
            AbstractBuff buff = debuffs.get(i);
            buff.draw(g, 4 + i * 40, Main.HEIGHT - 156, 36, 36);
            g.setColor(new Color(0, 0, 0, 155));
            g.setColor(Color.black);
            g.drawString(buff.currentStacks + "", 17 + i * 40, Main.HEIGHT - 127);

            if (buff instanceof ActivatableBuff) {
                g.setColor(Color.green);
                g.drawString(((ActivatableBuff) buff).uses + "", 16 + i * 40, Main.HEIGHT - 128);
            } else {
                g.setColor(Color.yellow);
                g.drawString(buff.currentStacks + "", 16 + i * 40, Main.HEIGHT - 128);
            }

            g.setColor(Color.white);
            g.drawString(String.format("%.0fs", (buff.duration - buff.timer.getTimePassed()) / 1000.f), 8 + i * 40, Main.HEIGHT - 160);
        }

        this.currentLevel.draw(g);

        if (this.currentMenu != null) {
            this.currentMenu.draw(g);
        }
    }


    @Override
    public void run() {
        double next_game_tick = System.currentTimeMillis();
        int loops;

        while (true) {
            loops = 0;

            while (System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {

                this.repaint();
                if (!Main.paused)
                    this.currentLevel.onUpdate();

                EventManager.call(new EventTick());

                next_game_tick += SKIP_TICKS;
                loops++;
            }
        }
    }
}
