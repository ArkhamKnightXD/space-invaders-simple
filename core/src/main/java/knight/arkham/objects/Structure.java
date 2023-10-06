package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Structure extends GameObject {
    private int hitCounter;
    private boolean setToDestroy;
    private boolean isDestroyed;

    public Structure(Rectangle bounds) {
        super(bounds, "images/structure.png", "break.ogg");
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

    public void hitByTheBullet() {

        hitCounter++;

        actionSound.play();

        if (hitCounter == 5)
            setToDestroy = true;
    }
}
