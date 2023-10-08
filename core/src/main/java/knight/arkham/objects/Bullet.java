package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends GameObject {
    private boolean setToDestroy;
    public boolean isDestroyed;

    public Bullet(Vector2 position) {
        super(
            new Rectangle(
                position.x + 8, position.y + 15, 16, 16
            ), "images/ball.png", "fall.wav"
        );
    }


    public void update(float deltaTime){

        if (setToDestroy && !isDestroyed)
            destroyBullet();

        else if (actualBounds.y > 1000)
            setToDestroy = true;

        else
            actualBounds.y += 400 * deltaTime;
    }

    private void destroyBullet() {

        isDestroyed = true;
    }

    @Override
    public void draw(Batch batch) {

        if (!isDestroyed)
            super.draw(batch);
    }

    public void collision() {

        setToDestroy = true;

        super.dispose();
    }
}
