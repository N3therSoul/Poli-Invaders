package me.nether.polinvaders.menu;

import me.nether.polinvaders.Main;

import java.awt.*;
import java.awt.event.MouseListener;

public abstract class AbstractButton implements MouseListener {

    public int x, y, width, height;

    public AbstractButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isHovered(int mx, int my) {
        return mx > x - width / 2 && mx < x + width / 2 && my > y - height / 2 && my < y + height / 2;
    }

    public boolean isOnButton() {
        Point mouse = Main.DISPLAY.getMousePosition();
        if(mouse == null) return false;
        int mx = mouse.x;
        int my = mouse.y;
        return mx > x - width / 2 && mx < x + width / 2 && my > y - height / 2 && my < y + height / 2;
    }


    public abstract void draw(Graphics2D g);
}
