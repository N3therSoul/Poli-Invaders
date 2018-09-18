package me.nether.polinvaders.levels;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.ImageComponent;
import me.nether.polinvaders.drawing.Player;
import me.nether.polinvaders.utils.Timer;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractLevel implements KeyListener {

    protected Timer timer = new Timer();
    public int score = 0;
    public Player player;

    public AbstractLevel() {
        this.init();
        Main.MAIN_FRAME.addKeyListener(this);
    }

    protected abstract void init();

    public List<ImageComponent> getComponents() {
        return Main.DISPLAY.OBJECTS;
    }

    public void onUpdate() {
        this.getComponents().removeIf(i -> i.toDelete || i.isOutOfBorders());

        this.getComponents().forEach(ImageComponent::onUpdate);
    }

    public void addComponent(ImageComponent component) {
        this.getComponents().add(component);
    }

    protected int getWidth() {
        return Main.WIDTH;
    }

    protected int getHeight() {
        return Main.HEIGHT;
    }

    public void draw(Graphics2D g) {
    }
}
