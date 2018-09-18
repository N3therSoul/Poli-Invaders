package me.nether.polinvaders.drawing;

import me.nether.polinvaders.Main;

public class EntityBoss extends Entity {

    public EntityBoss(int x, int y, int width, int height, String directory) {
        super(x, y, width, height, directory);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        Main.DISPLAY.currentLevel.getComponents().stream().filter(e -> e instanceof Entity && e != this).map(Entity.class::cast).forEach(e -> {
            if (e.lifePoints > Math.max(0.3, e.maxLifePoints / 300.f)) {
                e.lifePoints -= Math.max(0.3, e.maxLifePoints / 300.f);
            } else{
                e.toDelete = true;
            }
        });
    }

}