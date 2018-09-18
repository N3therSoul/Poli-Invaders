package me.nether.polinvaders.drawing.buffs;

import com.darkmagician6.eventapi.EventTarget;
import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.AbstractBuff;
import me.nether.polinvaders.events.EventTick;
import me.nether.polinvaders.levels.casta.LuigiDiMaio;

public class SizeBuff extends AbstractBuff {

    public float ratio;

    public SizeBuff(float x, float y, int width, int height, float ratio, int duration) {
        super("Size_Buff", x, y, width, height, "sizebuff.png", duration);
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
        ((LuigiDiMaio)Main.DISPLAY.currentLevel.player).bulletSize.value += this.ratio;

        super.onEnable();
    }

    @Override
    public void onDisable() {
        ((LuigiDiMaio)Main.DISPLAY.currentLevel.player).bulletSize.value -= ratio * currentStacks;

        super.onDisable();
    }
}

