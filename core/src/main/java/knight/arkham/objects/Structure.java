package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Structure extends GameObject {
    private int hitCounter;
    private boolean setToDestroy;
    private boolean isDestroyed;

    public Structure(Rectangle bounds) {
        super(bounds, "break.ogg", "images/structure.png");
    }

    public void update(){
        if (setToDestroy && !isDestroyed)
            destroyBody();
    }

    private void destroyBody() {
        isDestroyed = true;
    }

    @Override
    public void draw(Batch batch) {

        if (!isDestroyed)
            super.draw(batch);
    }

    public boolean hasCollisionWithTheBullet(GameObject bullet, boolean isBullet) {

        if (!isDestroyed && actualBounds.overlaps(bullet.actualBounds)) {

            hitCounter++;

            actionSound.play();

            if (isBullet)
                ((Bullet) bullet).collision();
            else
                ((AlienBullet) bullet).collision();

            if (hitCounter == 5)
                setToDestroy = true;

            return true;
        }

        return false;
    }
}
