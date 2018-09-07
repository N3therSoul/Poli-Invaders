package me.nether.polinvaders.drawing.buffs;

import com.darkmagician6.eventapi.EventTarget;
import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.AbstractBuff;
import me.nether.polinvaders.events.EventTick;

public class PerforationBuff extends AbstractBuff {

    public PerforationBuff(float x, float y, int width, int height) {
        super("Perforation_Buff", x, y, width, height, "perforationbuff.png", 65000);
        this.maxDuration = 100000;
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
//        Main.DISPLAY.currentLevel.player.perforation = true;

        super.onEnable();
    }

    @Override
    public void onDisable() {
//        Main.DISPLAY.currentLevel.player.perforation = false;

        super.onDisable();
    }
}

