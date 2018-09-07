package me.nether.polinvaders.drawing.buffs;

import com.darkmagician6.eventapi.EventTarget;
import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.AbstractBuff;
import me.nether.polinvaders.events.EventTick;

public class SizeBuff extends AbstractBuff {

    public float ratio;

    public SizeBuff(float x, float y, int width, int height, float ratio) {
        super("Size_Buff", x, y, width, height, "sizebuff.png", 50000);
        this.ratio = ratio;
        this.maxDuration = 60000;
        this.stackable = true;
        this.maxStacks = 4;
    }

    @EventTarget
    public void onTick(EventTick event) {
        super.onTick(event);
        if (!this.toDelete) timer.reset();

        if (timer.hasReach(this.duration)) {
            onDisable();
        }

        oldStacks = currentStacks;
    }

    @Override
    public void onEnable() {
        Main.DISPLAY.currentLevel.player.bulletSize += ratio;

        super.onEnable();
    }

    @Override
    public void onDisable() {
        Main.DISPLAY.currentLevel.player.bulletSize -= ratio * currentStacks;

        super.onDisable();
    }
}

