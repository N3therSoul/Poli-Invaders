package me.nether.polinvaders.menu.types;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.levels.casta.MatteoSalvini;
import me.nether.polinvaders.menu.AbstractMenu;
import me.nether.polinvaders.menu.types.components.BasicButton;
import me.nether.polinvaders.menu.types.components.StatButton;

import java.awt.event.KeyEvent;

public class Pause extends AbstractMenu {

    public Pause() {
        super();
        this.useBlur = true;
    }

    @Override
    public void init() {
        MatteoSalvini.count = 0;
        Main.paused = true;
        Main.DISPLAY.currentLevel.player.timer2.reset();
        BasicButton resume = new BasicButton("Resume", Main.WIDTH / 2, Main.HEIGHT / 2, 200, 50) {
            @Override
            public void init() {
                super.init();
                onExit();
            }
        };

        this.buttonList.add(resume);
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
