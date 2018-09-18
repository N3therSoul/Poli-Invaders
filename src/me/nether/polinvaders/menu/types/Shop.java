package me.nether.polinvaders.menu.types;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.Statistic;
import me.nether.polinvaders.levels.casta.LuigiDiMaio;
import me.nether.polinvaders.menu.AbstractMenu;
import me.nether.polinvaders.menu.types.components.BasicButton;
import me.nether.polinvaders.menu.types.components.StatButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;

public class Shop extends AbstractMenu {

    public Shop() {
        super();
        this.useBlur = true;
    }

    @Override
    public void init() {
        Main.paused = true;
        Main.DISPLAY.currentLevel.player.timer2.reset();
        BasicButton resume = new BasicButton("RIESUMA", Main.WIDTH / 2, Main.HEIGHT / 2 - 400, 200, 50) {
            @Override
            public void init() {
                super.init();
                onExit();
            }
        };

        BasicButton random = new BasicButton("Points: \247a0", 420, Main.HEIGHT - 100, 200, 50) {
            @Override
            public void draw(Graphics2D g) {
                super.draw(g);
                this.button = "Points: \247c" + ((LuigiDiMaio) Main.DISPLAY.currentLevel.player).spendablePoints;

            }
        };

        for (int j = 0; j < ((LuigiDiMaio) Main.DISPLAY.currentLevel.player).categories.length; j++) {
            String cat = ((LuigiDiMaio) Main.DISPLAY.currentLevel.player).categories[j];

            List<Statistic> list = Main.DISPLAY.currentLevel.player.stats.stream().filter(statistic -> statistic.category.equalsIgnoreCase(cat)).collect(Collectors.toList());

            for (int i = 0; i < list.size(); i++) {
                Statistic s = list.get(i);

                this.buttonList.add(new StatButton(s, Main.WIDTH - 20 - 200 * j, Main.HEIGHT - 115 + i * 20, 16, 16) {
                    @Override
                    public void init() {
                        super.init();
                        s.levelUp();
                    }
                });
            }
        }

        this.buttonList.add(resume);
        this.buttonList.add(random);
    }

    @Override
    public void onExit() {
        super.onExit();
        Main.paused = false;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.onExit();
        }
    }
}
