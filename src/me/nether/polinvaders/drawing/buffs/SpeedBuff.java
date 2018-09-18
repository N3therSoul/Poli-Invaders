package me.nether.polinvaders.drawing.buffs;

import com.darkmagician6.eventapi.EventTarget;
import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.AbstractBuff;
import me.nether.polinvaders.events.EventTick;
import me.nether.polinvaders.levels.casta.LuigiDiMaio;

public class SpeedBuff extends AbstractBuff {

    public int shots;

    public SpeedBuff(float x, float y, int width, int height, int shots, int duration) {
        super("Speed_Buff", x, y, width, height, "speedbuff.png", duration);
        this.shots = shots;
        this.maxDuration = 60000;
        this.stackable = true;
        this.maxStacks = 10;
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
        ((LuigiDiMaio) Main.DISPLAY.currentLevel.player).attackSpeed.value += this.shots;

        super.onEnable();
    }

    @Override
    public void onDisable() {
        ((LuigiDiMaio) Main.DISPLAY.currentLevel.player).attackSpeed.value -= this.shots;

        super.onDisable();
    }
}

