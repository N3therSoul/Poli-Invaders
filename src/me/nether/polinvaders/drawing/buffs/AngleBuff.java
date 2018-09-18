package me.nether.polinvaders.drawing.buffs;

import com.darkmagician6.eventapi.EventTarget;
import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.AbstractBuff;
import me.nether.polinvaders.events.EventTick;
import me.nether.polinvaders.levels.casta.LuigiDiMaio;

public class AngleBuff extends AbstractBuff {

    public float multiplier;

    public AngleBuff(float x, float y, int width, int height, float multiplier, int duration) {
        super("Angle_Buff", x, y, width, height, "anglebuff.png", duration);
        this.maxDuration = 10000;
        this.multiplier = multiplier;
        this.stackable = false;
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
        ((LuigiDiMaio)Main.DISPLAY.currentLevel.player).angle.value += this.multiplier;

        super.onEnable();
    }

    @Override
    public void onDisable() {
        ((LuigiDiMaio)Main.DISPLAY.currentLevel.player).angle.value -= this.multiplier * this.currentStacks;

        super.onDisable();
    }
}

