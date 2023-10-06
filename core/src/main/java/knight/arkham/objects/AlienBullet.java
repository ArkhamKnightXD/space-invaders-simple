package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AlienBullet extends GameObject {
    private boolean setToDestroy;
    private boolean isDestroyed;

    public AlienBullet(Vector2 position) {
        super(
            new Rectangle(
                position.x, position.y, 16, 16
            ), "images/ball.png", "fall.wav"
        );

//        body.setLinearVelocity(0, -15);
    }


    public void update(){

        if (setToDestroy && !isDestroyed)
            destroyBullet();

        else if (actualBounds.y < 200)
            setToDestroy = true;
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
    }
}
