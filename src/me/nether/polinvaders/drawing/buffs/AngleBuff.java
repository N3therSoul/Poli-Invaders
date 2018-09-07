package me.nether.polinvaders.drawing.buffs;

import com.darkmagician6.eventapi.EventTarget;
import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.AbstractBuff;
import me.nether.polinvaders.events.EventTick;

public class AngleBuff extends AbstractBuff {

    public float multiplier;

    public AngleBuff(float x, float y, int width, int height, float multiplier) {
        super("Angle_Buff", x, y, width, height, "anglebuff.png", 5000);
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
        Main.DISPLAY.currentLevel.player.bulletsAngle = this.multiplier;

        super.onEnable();
    }

    @Override
    public void onDisable() {
        Main.DISPLAY.currentLevel.player.bulletsAngle = 0;

        super.onDisable();
    }
}

