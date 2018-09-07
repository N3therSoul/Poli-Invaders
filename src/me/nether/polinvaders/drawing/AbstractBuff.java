package me.nether.polinvaders.drawing;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import me.nether.polinvaders.Main;
import me.nether.polinvaders.events.EventTick;
import me.nether.polinvaders.utils.MathUtils;
import me.nether.polinvaders.utils.Timer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class AbstractBuff extends ImageComponent {

    public final Timer timer = new Timer();
    public final String name;
    public int duration, maxDuration;
    public boolean stackable;
    public int currentStacks = 0, debuffStacks = 0, maxStacks = 3, oldStacks;
    public boolean isDebuff;

    public AbstractBuff(String name, float x, float y, int width, int height, String directory, int duration) {
        super(x, y, width, height, directory);
        this.name = name;
        this.duration = duration;

        Main.DISPLAY.currentLevel.addComponent(this);
    }

    @EventTarget
    public void onTick(EventTick event) {
        if (this.duration - timer.getTimePassed() > this.maxDuration) {
            this.duration = this.maxDuration + (int) timer.getTimePassed();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        this.draw(g,
                (int) (this.x - this.width / 2),
                (int) (this.y - this.height / 2),
                (int) this.width,
                (int) this.height);
    }

    @Override
    public void draw(Graphics2D g, float x, float y, float width, float height) {
        if (this.isDebuff) {
            AffineTransform t = new AffineTransform();
            t.rotate(Math.toRadians(180), x + width / 2, y + height / 2);
            g.transform(t);
            super.draw(g, x, y, width, height);
            g.transform(t);
        } else {
            super.draw(g, x, y, width, height);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.toDelete) return;

        Player p = Main.DISPLAY.currentLevel.player;

        if (MathUtils.distance(this.x, this.y, p.x, p.y) < p.width) {
            boolean present = false;
            AbstractBuff sameBuff = null;
            for (AbstractBuff buff : Main.DISPLAY.currentLevel.player.buffs) {
                if (buff.name.equalsIgnoreCase(this.name) && ((this.isDebuff && buff.isDebuff) || (!this.isDebuff && !buff.isDebuff))) {
                    present = true;
                    sameBuff = buff;
                    break;
                }
            }

            if (present) {
                if (sameBuff.stackable) {
                    if(!sameBuff.isDebuff) {
                        if (sameBuff.currentStacks < sameBuff.maxStacks) {
                            sameBuff.currentStacks++;
                            sameBuff.onEnable();
                        }
                    } else {
                        if (sameBuff.currentStacks < sameBuff.maxStacks) {
                            sameBuff.currentStacks++;
                            sameBuff.onEnable();
                        }
                    }
                }
                if (sameBuff instanceof ActivatableBuff) {
                    ((ActivatableBuff) sameBuff).uses = ((ActivatableBuff) sameBuff).maxUses;
                }
                sameBuff.duration += this.duration;
            } else {
                this.currentStacks = 1;
                Main.DISPLAY.currentLevel.player.buffs.add(this);
                this.onEnable();
                this.timer.reset();
                EventManager.register(this);
            }

            this.toDelete = true;
            return;
        }
    }

    public void onEnable() {
    }

    public void onDisable() {
        EventManager.unregister(this);
        Main.DISPLAY.currentLevel.player.buffs.remove(this);
    }

    public static AbstractBuff getBuff(String buffName) {
        for (AbstractBuff buff : Main.DISPLAY.currentLevel.player.buffs) {
            if (buff.name.equalsIgnoreCase(buffName)) {
                return buff;
            }
        }
        return null;
    }

}
