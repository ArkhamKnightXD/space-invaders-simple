package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import knight.arkham.scenes.Hud;

public class SpaceShip extends GameObject {
    private boolean isDestroyed;
    private boolean setToDestroy;
    private float stateTimer;
    private final float velocityX;
    private boolean shouldGoRight;

    public SpaceShip() {
        super(
            new Rectangle(1600, 900, 32, 32),
            "images/alien-ship.png", "okay.wav"
        );
        velocityX = 150;
    }

    public void update(float deltaTime) {

        if (setToDestroy && !isDestroyed)
            destroySpaceShip();

        if (!shouldGoRight) {

            if (actualBounds.x < 450){
                stateTimer += deltaTime;

                if (stateTimer > 10){
                    shouldGoRight = true;

                    stateTimer = 0;
                }
            }

            else
                actualBounds.x -= velocityX * deltaTime;
        }

        if (shouldGoRight){
            actualBounds.x += velocityX * deltaTime;

            if (actualBounds.x > 1000){
                stateTimer += deltaTime;

                if (stateTimer > 10){
                    shouldGoRight = false;

                    stateTimer = 0;
                }
            }
        }
    }

    private void destroySpaceShip() {
        isDestroyed = true;
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

            actionSound.play();

            bullet.collision();

            return true;
        }

        return false;
    }
}
