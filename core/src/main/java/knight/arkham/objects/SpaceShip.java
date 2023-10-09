package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import knight.arkham.scenes.Hud;

public class SpaceShip extends GameObject {
    private boolean isDestroyed;
    private boolean setToDestroy;
    private float stateTimer;
    private final float velocityX;

    public SpaceShip(int positionX, int positionY) {
        super(
            new Rectangle(positionX, positionY, 32, 32),
            "images/alien-ship.png", "okay.wav"
        );
        velocityX = 100;
    }

    public void update(float deltaTime) {

        if (setToDestroy && !isDestroyed)
            destroySpaceShip();

        stateTimer += deltaTime;

        if (stateTimer > 5) {
            actualBounds.x -= velocityX * deltaTime;
        }
    }

    private void destroySpaceShip() {

        isDestroyed = true;

        super.dispose();
    }

    @Override
    public void draw(Batch batch) {

        if (!isDestroyed)
            super.draw(batch);
    }

    public boolean hitByTheBullet(Bullet bullet) {

        if (!isDestroyed && actualBounds.overlaps(bullet.getBounds())){
            setToDestroy = true;

            Hud.addScore(50);

//            Todo the sounds doesn't work.
            actionSound.play();

            bullet.collision();

            return true;
        }

        return false;
    }
}
