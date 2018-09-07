package me.nether.polinvaders.drawing.buffs;

import com.darkmagician6.eventapi.EventTarget;
import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.AbstractBuff;
import me.nether.polinvaders.events.EventTick;

public class SpeedBuff extends AbstractBuff {

    public int shots;

    public SpeedBuff(float x, float y, int width, int height, int shots) {
        super("Speed_Buff", x, y, width, height, "speedbuff.png", 15000);
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
        Main.DISPLAY.currentLevel.player.bulletsPerSecond += this.shots;

        super.onEnable();
    }

    @Override
    public void onDisable() {
        Main.DISPLAY.currentLevel.player.bulletsPerSecond -= this.shots * currentStacks;

        super.onDisable();
    }
}

