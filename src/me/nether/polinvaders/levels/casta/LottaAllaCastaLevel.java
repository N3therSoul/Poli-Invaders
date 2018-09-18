package me.nether.polinvaders.levels.casta;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.*;
import me.nether.polinvaders.levels.AbstractLevel;
import me.nether.polinvaders.menu.types.Pause;
import me.nether.polinvaders.menu.types.Shop;
import me.nether.polinvaders.utils.ColorUtils;
import me.nether.polinvaders.utils.MathUtils;
import me.nether.polinvaders.utils.RenderUtils;
import me.nether.polinvaders.utils.Timer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LottaAllaCastaLevel extends AbstractLevel {

    private final Font FONT_SMALL = new Font("Century Gothic", Font.PLAIN, 15);
    private final Font FONT_MEDIUM = new Font("Century Gothic", Font.BOLD, 17);

    private final Font FONT_SCORE = new Font("Helvetica Neue", Font.BOLD, 40);
    private final Font FONT_LEVEL = new Font("Helvetica Neue", Font.BOLD, 20);

    private Timer buffTimer;
    private Timer boninoTimer;
    private Timer respawnCamera;
    private Timer dimartinoTimer;
    private Timer dalemaTimer;

    public static Timer gentletimer = new Timer();

    public int loss = 0;
    public float consents = 32.7f;

    public ImageComponent presidenteDellaCamera = null;
    public int count = 0;

    boolean salvini1;
    boolean salvini2;
    boolean salvini4;

    @Override
    public void init() {
        buffTimer = new Timer();
        respawnCamera = new Timer();
        boninoTimer = new Timer();
        dimartinoTimer = new Timer();
        dalemaTimer = new Timer();

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
        gentletimer.reset();
    }

    @Override
    public void onUpdate() {
//        Collections.sort(this.getComponents(), Comparator.comparingDouble(o -> o.width * o.height));

        super.onUpdate();
        
        handleLoss();

        boolean isBoss = this.getComponents().stream().anyMatch(e -> e instanceof EntityBoss);
        if (isBoss) return;

        if (getPunteggioneFinale() < 40 || (getPunteggioneFinale() > 45 && getPunteggioneFinale() < 55)) {
            spawnBonino();
        }

        spawnDAlema();

        if (getPunteggioneFinale() < 60) {
            spawnDiMartino();
        }

        if (getPunteggioneFinale() < 35) salvini1 = false;
        if (getPunteggioneFinale() < 45) salvini2 = false;
        if (getPunteggioneFinale() < 65) salvini4 = false;


        spawnCamera();

        if (getPunteggioneFinale() >= 50 && !salvini1) {
            spawnSalvini(1);
            salvini1 = true;
        }

        if (getPunteggioneFinale() >= 60 && !salvini2) {
            spawnSalvini(2);
            salvini2 = true;
        }

        if (getPunteggioneFinale() >= 80 && !salvini4) {
            spawnSalvini(4);
            salvini4 = true;
        }

        if (getPunteggioneFinale() > 40) {
            spawnRenzi();
        }

        if (getPunteggioneFinale() > 55) {
            spawnGentiloni();
        }
    }

    private int nextBonino = (int) MathUtils.range(2500, 4500);
    private int nextDiMartino = (int) MathUtils.range(5500, 15000);

    private void spawnDAlema() {
        boolean isDalema = this.getComponents().stream().anyMatch(e -> e instanceof MassimoDAlema);
        if (isDalema) return;
        if (dalemaTimer.hasReach(5000)) {
            MassimoDAlema dalema = new MassimoDAlema((int) MathUtils.range(100, Main.WIDTH - 100), 0, 80, 80, 1 + Main.DISPLAY.currentLevel.score / 2000);
//            dalema.xSpeed = (float) MathUtils.range(-2, 2);
            dalemaTimer.reset();
        }
    }

    private void spawnBonino() {
        if (boninoTimer.hasReach(nextBonino)) {
            EmmaBonino bonino = new EmmaBonino((int) MathUtils.range(100, Main.WIDTH - 100), 0, 80, 80, 1 + Main.DISPLAY.currentLevel.score / 2000);
            bonino.ySpeed = 0.5f;
            nextBonino = (int) MathUtils.range(2500, 4500);
            boninoTimer.reset();
        }
    }

    private void spawnDiMartino() {
        int countX = 0;
        int countXneg = 0;

        if (dimartinoTimer.hasReach(nextDiMartino)) {
            int firstX = (int) MathUtils.range(100, Main.WIDTH - 100);
            for (int i = 0, max = (int) MathUtils.range(3, 7, x -> x * MathUtils.map(score, 0, 10000, 0, 1)); i < max; i++) {
                new StefanoDiMartino(firstX + (i % 2 == 0 ? -countXneg : countX), 0, 80, 80, 1 + Main.DISPLAY.currentLevel.score / 4000);
                if (i % 2 == 0) {
                    countXneg += 100;
                } else {
                    countX += 100;
                }
            }

            nextDiMartino = (int) MathUtils.range(5500, 15000);
            dimartinoTimer.reset();
        }
    }

    private void spawnSalvini(int level) {
        new MatteoSalvini(Main.WIDTH / 2, Main.HEIGHT / 4, 200, 200, level);
    }

    private void spawnGentiloni() {
        if (gentletimer.hasReach(30000)) {
            Entity gentile = new PaoloGentiloni((int) MathUtils.range(300, 700), 0, 150, 150, 1 + Main.DISPLAY.currentLevel.score / 5000);
            gentile.ySpeed = 0.2f;
            gentletimer.reset();
        }
    }

    private void spawnCamera() {
        if (presidenteDellaCamera == null) {
            if (this.respawnCamera.hasReach(MathUtils.range(3000, 5000))) {
                count++;
                if (count % 2 == 1) {
                    presidenteDellaCamera = new RobertoFico((int) MathUtils.range(100, 900), Main.HEIGHT - Main.HEIGHT * Main.DISPLAY.currentLevel.player.bulletsRange + 100,
                            100, 100, ((LuigiDiMaio) this.player).level);
                } else {
                    presidenteDellaCamera = new LauraBoldrini((int) MathUtils.range(100, 900), Main.HEIGHT - Main.HEIGHT * Main.DISPLAY.currentLevel.player.bulletsRange + 100,
                            100, 100, ((LuigiDiMaio) this.player).level);
                }
            }
        } else {
            respawnCamera.reset();
        }
    }

    private void spawnRenzi() {
        if (timer.hasReach(15000)) {
            Entity enemy = new MatteoRenzi((int) MathUtils.range(100, Main.WIDTH - 100), 0, 80, 80, 1 + Main.DISPLAY.currentLevel.score / 5000);
            enemy.ySpeed = (float) MathUtils.range(0.2, 0.5);

            timer.reset();
        }
    }

    private void handleLoss() {
        this.getComponents().stream()
                .filter(i -> i instanceof Entity).map(i -> (Entity) i)
                .filter(Entity::isOutOfBorders)
                .forEach(e -> this.loss += e.lifePoints);
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

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        drawHud(g);

        for (int j = 0; j < ((LuigiDiMaio) this.player).categories.length; j++) {
            String cat = ((LuigiDiMaio) this.player).categories[j];

            List<Statistic> list = this.player.stats.stream().filter(statistic -> statistic.category.equalsIgnoreCase(cat)).collect(Collectors.toList());

            String string = "\247c" + cat.substring(0, 1).toUpperCase() + cat.substring(1);
            g.setFont(FONT_MEDIUM);
            RenderUtils.drawStringWithShadow(g, string, Main.WIDTH - RenderUtils.getStringWidth(g, string) - 30 - 200 * j, Main.HEIGHT - 130, 0xffffffff);

            for (int i = 0; i < list.size(); i++) {
                Statistic s = list.get(i);
                g.setFont(FONT_SMALL);
                String toDraw = s.name + ": lvl \247a" + s.level;

                RenderUtils.drawString(g, toDraw, Main.WIDTH - RenderUtils.getStringWidth(g, toDraw) - 30 - 200 * j, Main.HEIGHT - 110 + i * 20, 0xffffffff);
            }
        }

        int level = ((LuigiDiMaio) this.player).level;
        float progress = ((LuigiDiMaio) this.player).getProgress();
        g.setColor(ColorUtils.getColor(0xcccccccc));
        g.fillRect(Main.WIDTH - 200, Main.HEIGHT - 170, 190, 18);
        g.setColor(ColorUtils.getColor(0xff16ff7b));
        g.fillRect(Main.WIDTH - 198, Main.HEIGHT - 168, (int) (186 * (progress)), 14);
        String string = String.format("\247flvl: \247b%d", level);
        g.setFont(FONT_MEDIUM);
        RenderUtils.drawStringWithShadow(g, string, Main.WIDTH - RenderUtils.getStringWidth(g, string) - 205, Main.HEIGHT - 155, 0xffffffff);

        g.setFont(FONT_SCORE);
        g.setColor(Color.white);
        g.drawString(String.format("%.1f%c", getPunteggioneFinale(), '%'), 14, 40);

        g.setFont(FONT_SMALL);
        g.drawString("entities_on_screen: " + this.getComponents().size(), 14, Main.HEIGHT - 160);
        g.drawString("bullets_per_second: " + ((LuigiDiMaio) this.player).attackSpeed.value, 14, Main.HEIGHT - 173);
        g.drawString(String.format("damage_per_bullet: %.1f", +Math.pow((15 + 40) * ((LuigiDiMaio) this.player).bulletSize.value / 50, 1.3)), 14, Main.HEIGHT - 186);
        g.drawString("kill_counter: " + this.score, 14, Main.HEIGHT - 199);

    }

    public void drawHud(Graphics2D g) {
        g.setFont(FONT_MEDIUM);
        Main.MAIN_FRAME.setBounds(Main.MAIN_FRAME.getX(), Main.MAIN_FRAME.getY(), Main.WIDTH, Main.HEIGHT);

        List<AbstractBuff> buffs = player.buffs.stream().filter(b -> !b.isDebuff).collect(Collectors.toList());
        List<AbstractBuff> debuffs = player.buffs.stream().filter(b -> b.isDebuff).collect(Collectors.toList());

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
                g.drawString(((ActivatableBuff) buff).uses + "", 2 + i * 40, Main.HEIGHT - 64);
            } else {
                g.setColor(Color.yellow);
                g.drawString(buff.currentStacks + "", 2 + i * 40, Main.HEIGHT - 64);
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
                g.drawString(((ActivatableBuff) buff).uses + "", 2 + i * 40, Main.HEIGHT - 118);
            } else {
                g.setColor(Color.yellow);
                g.drawString(buff.currentStacks + "", 2 + i * 40, Main.HEIGHT - 118);
            }

            g.setColor(Color.white);
            g.drawString(String.format("%.0fs", (buff.duration - buff.timer.getTimePassed()) / 1000.f), 8 + i * 40, Main.HEIGHT - 160);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private float getPunteggioneFinale() {
        return consents + (score - loss) / 500.f;
    }
}
