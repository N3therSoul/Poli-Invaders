package me.nether.polinvaders.menu.types.components;

import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.Statistic;
import me.nether.polinvaders.levels.casta.LuigiDiMaio;
import me.nether.polinvaders.menu.AbstractButton;
import me.nether.polinvaders.utils.ColorUtils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StatButton extends AbstractButton implements MouseListener {

    private Font font = new Font("Helvetica Neue", Font.PLAIN, 25);

    public boolean enabled, visible;
    public Statistic stat;

    public StatButton(Statistic stat, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.stat = stat;
        this.enabled = true;
        this.visible = true;
    }

    @Override
    public void draw(Graphics2D g) {
        this.visible = this.stat.canLevelUp();
        this.enabled = ((LuigiDiMaio) Main.DISPLAY.currentLevel.player).spendablePoints > 0;

        if (!this.visible) return;

        g.setColor(ColorUtils.getColor(!this.enabled ? 0x22609dff : this.isOnButton() ? 0xcc34f95b : 0xaa34f95b));
        int angle = width > height ? width / 10 : height / 10;

        g.fillRoundRect(x - width / 2, y - height / 2, width, height, angle, angle);
        g.setColor(g.getColor().darker().darker().darker().darker());
        g.drawRoundRect(x - width / 2, y - height / 2, width, height, angle, angle);

        g.setColor(ColorUtils.getColor(!this.enabled ? 0x447accff : this.isOnButton() ? 0xff008432 : 0xffffffff));
        g.setFont(font);
        double sWidth = g.getFontMetrics().stringWidth("+");
        double sHeight = g.getFontMetrics().getHeight();

        g.drawString("+", (int) (x - sWidth / 2) + 1, (int) (y + sHeight / 4) + 1);
    }

    public void init() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.visible = this.stat.canLevelUp();
        this.enabled = ((LuigiDiMaio) Main.DISPLAY.currentLevel.player).spendablePoints > 0;

        if(!this.visible || !this.enabled) return;

        if (this.isHovered(e.getX(), e.getY())) {
            ((LuigiDiMaio) Main.DISPLAY.currentLevel.player).spendablePoints--;
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
