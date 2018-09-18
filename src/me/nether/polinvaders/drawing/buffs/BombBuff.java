package me.nether.polinvaders.drawing.buffs;

import com.darkmagician6.eventapi.EventTarget;
import me.nether.polinvaders.Main;
import me.nether.polinvaders.drawing.*;
import me.nether.polinvaders.events.EventKey;
import me.nether.polinvaders.events.EventTick;
import me.nether.polinvaders.utils.MathUtils;

import java.awt.event.KeyEvent;

public class BombBuff extends ActivatableBuff {

    public int range;

    public BombBuff(float x, float y, int width, int height, int range, int duration) {
        super("Bomb_buff", x, y, width, height, "bombbuff.png", duration);
        this.maxDuration = 60000;
        this.uses = 3;
        this.maxUses = 3;
        this.range = range;
    }

    @EventTarget
    public void onTick(EventTick event) {
        super.onTick(event);

        if (timer.hasReach(this.duration)) {
            onDisable();
        }
    }

    @EventTarget
    public void onKey(EventKey event) {
        if (event.key == KeyEvent.VK_W) {
            this.uses--;

            Bomb bomb = new Bomb(Main.DISPLAY.currentLevel.player.x, Main.DISPLAY.currentLevel.player.y, range, 10);
            bomb.ySpeed = -5;

            if (this.uses == 0) {
                this.onDisable();
            }
        }
    }

    public class Bomb extends ImageComponent {

        public int damage;

        public Bomb(float x, float y, int range, int damage) {
            super(x, y, range, range, "bomb.png");
            Main.DISPLAY.OBJECTS.add(this);
            this.damage = damage;
        }

        @Override
        public void onUpdate() {
            super.onUpdate();

//            for (int i = Main.DISPLAY.OBJECTS.size() - 1; i >= 0; i--) {
//                AbstractComponent object = Main.DISPLAY.OBJECTS.get(i);
//                if (object instanceof Entity) {
//                    Entity e = (Entity) object;
//                    if (MathUtils.distance(e.x, e.y, this.x, this.y) <= this.width) {
//                        for (int i1 = Main.DISPLAY.OBJECTS.size() - 1; i1 >= 0; i1--) {
//                            ImageComponent imageComponent = Main.DISPLAY.OBJECTS.get(i1);
//                            if (imageComponent instanceof Entity) {
//                                Entity enemy = (Entity) imageComponent;
//
//                                if (MathUtils.distance(enemy.x, enemy.y, this.x, this.y) <= this.width * 5) {
//                                    enemy.lifePoints -= this.damage;
//                                }
//                            }
//                        }
//                        e.lifePoints -= damage * 4;
//                        this.toDelete = true;
//
//                        break;
//                    }
//                }
//            }

        }
    }

}
