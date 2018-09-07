package me.nether.polinvaders.drawing.buffs;

import com.darkmagician6.eventapi.EventTarget;
import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.AbstractBuff;
import me.nether.polinvaders.events.EventTick;

public class RangeBuff extends AbstractBuff {

    public float ratio;

    public RangeBuff(float x, float y, int width, int height, float ratio) {
        super("Range_Buff", x, y, width, height, "rangebuff.png", 5000);
        this.ratio = ratio;
        this.maxDuration = 60000;
        this.stackable = true;
        this.maxStacks = 5;
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
        Main.DISPLAY.currentLevel.player.bulletsRange *= ratio;

        super.onEnable();
    }

    @Override
    public void onDisable() {
        Main.DISPLAY.currentLevel.player.bulletsRange /= Math.pow(ratio, currentStacks);

        super.onDisable();
    }
}

