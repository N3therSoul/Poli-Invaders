package me.nether.polinvaders.menu;

import me.nether.polinvaders.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractMenu implements KeyListener {

    public AbstractMenu oldMenu;

    public boolean useBlur;

    public List<AbstractButton> buttonList = new ArrayList<>();


    public AbstractMenu() {
        if (Main.DISPLAY.currentMenu != null)
            Main.DISPLAY.currentMenu.onExit();

        Main.MAIN_FRAME.addKeyListener(this);
        Main.MAIN_FRAME.removeKeyListener(Main.DISPLAY.currentLevel);

        this.oldMenu = Main.DISPLAY.currentMenu;
        this.init();

        this.buttonList.forEach(Main.DISPLAY::addMouseListener);
        Main.DISPLAY.currentMenu = this;
    }

    public abstract void init();

    public void onExit() {
        Main.DISPLAY.currentMenu = this.oldMenu;
        Main.MAIN_FRAME.removeKeyListener(this);
        Main.MAIN_FRAME.addKeyListener(Main.DISPLAY.currentLevel);
        this.buttonList.forEach(Main.DISPLAY::removeMouseListener);

    }

    @Override
    public void keyPressed(KeyEvent event) {

    }

    @Override
    public void keyTyped(KeyEvent event) {

    }

    @Override
    public void keyReleased(KeyEvent event) {

    }

    public void draw(Graphics2D g) {
        this.drawDefaultBackground(g);
        this.buttonList.forEach(b -> b.draw(g));
    }


    public void drawDefaultBackground(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
    }
}
