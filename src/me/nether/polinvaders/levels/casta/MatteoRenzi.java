package me.nether.polinvaders.levels.casta;

import me.nether.polinvaders.drawing.Entity;
import me.nether.polinvaders.utils.HasLifeBar;
import me.nether.polinvaders.utils.MathUtils;
import me.nether.polinvaders.utils.Timer;

public class MatteoRenzi extends Entity implements HasLifeBar {

    public final Timer timer = new Timer();

    public MatteoRenzi(float x, float y, int width, int height, int level) {
        super(x, y, width, height, "renzi1.png");
        timer.reset();
        this.setupLifePoints(20 * level);
        this.level = level;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.toDelete) return;

        if (timer.hasReach(2000)) {

            int jobsAct = this.level * 4;

            int phi = (int) MathUtils.range(0, 90);

            for (int i = (level > 2 ? -90 : phi); i < (level > 2 ? 90 : 360 + phi); i += 360 / jobsAct) {

                int x = (int) (Math.sin(Math.toRadians(i)) * this.width + this.x);
                int y = (int) (Math.cos(Math.toRadians(i)) * this.width + this.y);

                JobsAct jAct = new JobsAct(x, y, (int) this.width / 2, (int) this.height / 2, 1);
                jAct.lifePoints = 2 * level;

                jAct.ySpeed = this.ySpeed;
                if (this.level > 1) {
                    int x1 = (int) (Math.sin(Math.toRadians(i)) * this.width * 2 + this.x);
                    int y1 = (int) (Math.cos(Math.toRadians(i)) * this.width * 2 + this.y);

                    OttantaEuro ottanta = new OttantaEuro(x1, y1, (int) this.width / 2, (int) this.height / 2, 1);
                    ottanta.lifePoints = 1;
                    ottanta.yAcc = 0.05f;
                }
            }

            timer.reset();
        }
    }

    public class JobsAct extends Entity {
        public final Timer timer = new Timer();

        public JobsAct(float x, float y, int width, int height, int level) {
            super(x, y, width, height, "jobsact.png");
            timer.reset();
        }
    }

    public class OttantaEuro extends Entity {
        public final Timer timer = new Timer();

        public OttantaEuro(float x, float y, int width, int height, int level) {
            super(x, y, width, height, "ottantaeuro.png");
            timer.reset();
        }
    }

}
