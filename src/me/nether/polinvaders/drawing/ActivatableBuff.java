package me.nether.polinvaders.drawing;

import com.darkmagician6.eventapi.EventTarget;
import me.nether.polinvaders.events.EventKey;

public abstract class ActivatableBuff extends AbstractBuff {

    public int uses, maxUses;

    public ActivatableBuff(String name, float x, float y, int width, int height, String directory, int duration) {
        super(name, x, y, width, height, directory, duration);
    }

    @EventTarget
    public abstract void onKey(EventKey event);
}

