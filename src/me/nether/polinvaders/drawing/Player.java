package me.nether.polinvaders.drawing;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import me.nether.polinvaders.Main;
import me.nether.polinvaders.events.EventKey;
import me.nether.polinvaders.events.EventTick;
import me.nether.polinvaders.utils.Timer;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Player extends ImageComponent {

    protected boolean keys[] = new boolean[256];

    public int bulletsPerSecond = 1;
    public float bulletsAngle = 0;
    public float bulletSize = 1;
    public float bulletsRange = 0.7f;

    protected Timer timer = new Timer();
    public Timer timer2 = new Timer();

    public List<Projectile> projectiles = new ArrayList<>();
    public List<AbstractBuff> buffs = new ArrayList<>();

    public List<Statistic> stats = new ArrayList<>();

    public Player(int x, int y, int width, int height, String directory) {
        super(x, y, width, height, directory);
        EventManager.register(this);
    }

    public void keyPressed(KeyEvent e) {
        this.keys[e.getKeyCode()] = true;

        EventManager.call(new EventKey(e.getKeyCode()));
    }

    public void keyReleased(KeyEvent e) {
        this.keys[e.getKeyCode()] = false;
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (Main.paused) {

            //TODO: Rifare i timer o qualcosa di simile, per ora funziona
            if (timer2.hasReach(1000)) {
                this.buffs.forEach(b -> {
                    b.timer.last += 1000000000;
                });
                timer2.reset();
            }

        }
    }

    protected void fixBounds() {
        if (this.x - this.width / 2 < 0) {
            this.x = this.width / 2;
        }
        if (this.x + this.width / 2 > Main.MAIN_FRAME.getWidth()) {
            this.x = Main.MAIN_FRAME.getWidth() - this.width / 2;
        }
    }
}