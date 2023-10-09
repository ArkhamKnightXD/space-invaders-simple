package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import knight.arkham.scenes.Hud;

public class Alien extends GameObject {
    private boolean isDestroyed;
    private boolean setToDestroy;
    private final int alienPoints;
    private float stateTimer;
    private float changeVelocityTimer;
    private float velocityX;
    private boolean shouldGoDown;

    public Alien(int positionX, int positionY, String spritePath, int points) {
        super(
            new Rectangle(720 + positionX, 850 - positionY, 32, 32),
            spritePath, "okay.wav"
        );
        alienPoints = points;
        velocityX = 20;
    }

    public void update(float deltaTime) {

        if (setToDestroy && !isDestroyed)
            destroyAlien();

        stateTimer += deltaTime;
        changeVelocityTimer += deltaTime;

        if (stateTimer > 0.5f){

            actualBounds.x += velocityX;

            stateTimer = 0;

        } else if (changeVelocityTimer > 4.5f){

            velocityX *= -1;
            actualBounds.x += velocityX;
            changeVelocityTimer = -4.5f;

            stateTimer = 0;

            shouldGoDown = true;
        } else if (shouldGoDown) {

            actualBounds.y -= 20;

            shouldGoDown = false;
        }
    }

    private void destroyAlien() {

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

            Hud.addScore(alienPoints);

            actionSound.play(0.6f);

            bullet.collision();

            return true;
        }

        return false;
    }

    public Vector2 getPosition(){
        return new Vector2(actualBounds.x, actualBounds.y);
    }
}
