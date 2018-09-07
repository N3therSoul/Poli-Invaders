package me.nether.polinvaders.menu.types.components;

import me.nether.polinvaders.menu.AbstractButton;
import me.nether.polinvaders.utils.ColorUtils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BasicButton extends AbstractButton implements MouseListener {

    public String button;

    private Font font = new Font("Helvetica Neue", Font.PLAIN, 25);

    public boolean enabled, visible;

    public BasicButton(String button, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.button = button;
        this.enabled = true;
        this.visible = true;
    }

    @Override
    public void draw(Graphics2D g) {
        if (!this.visible) return;

        g.setColor(ColorUtils.getColor(!this.enabled ? 0xcc609dff : this.isOnButton() ? 0xcc34f95b : 0xaa34f95b));
        int angle = width > height ? width / 10 : height / 10;

        g.fillRoundRect(x - width / 2, y - height / 2, width, height, angle, angle);
        g.setColor(g.getColor().darker().darker().darker().darker());
        g.drawRoundRect(x - width / 2, y - height / 2, width, height, angle, angle);

        g.setColor(Color.white);
        g.setFont(font);
        double sWidth = g.getFontMetrics().stringWidth(button);
        double sHeight = g.getFontMetrics().getHeight();

        g.drawString(button, (int) (x - sWidth / 2) + 1, (int) (y + sHeight / 4) + 1);
    }

    public void init() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.isHovered(e.getX(), e.getY())) {
            this.init();
            System.out.println("meme");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}
