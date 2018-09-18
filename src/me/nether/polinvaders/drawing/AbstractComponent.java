package me.nether.polinvaders.drawing;

public abstract class AbstractComponent {

    public float x, y;
    public float width, height;
    public float xSpeed = 0, ySpeed = 0;
    public float xAcc = 0, yAcc = 0;

    public boolean toDelete = false;

    public AbstractComponent(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void onUpdate() {
        this.xSpeed += this.xAcc;
        this.ySpeed += this.yAcc;
        this.x += this.xSpeed;
        this.y += this.ySpeed;
    }
}
