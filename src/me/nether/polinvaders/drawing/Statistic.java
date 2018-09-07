package me.nether.polinvaders.drawing;

public class Statistic {

    public String name, category;
    public float value, min, max;
    public int level, numLevels;

    public Statistic(String name, float value, float min, float max, int numLevels, String category) {
        this.name = name;
        this.level = 1;
        this.min = min;
        this.max = max;
        this.numLevels = numLevels;
        this.value = value;
        this.category = category;
    }

    public boolean canLevelUp() {
        return this.level < this.numLevels;
    }

    public void levelUp() {
        if(!canLevelUp()) return;

        this.level++;
        this.value = min + (max - min) / numLevels * level;
    }
}
