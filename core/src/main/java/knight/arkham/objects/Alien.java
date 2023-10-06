package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import knight.arkham.scenes.Hud;

public class Alien extends GameObject {
    private boolean isDestroyed;
    private boolean setToDestroy;
    private final int alienPoints;
    private float stateTimer;
    private float velocityX;
    private float changeVelocityTimer;
    private boolean shouldGoDown;

    public Alien(int positionX, int positionY, String spritePath, int points) {
        super(
            new Rectangle(
                720 + positionX,
                850 - positionY,
                32, 32
            ), spritePath, "okay.wav"
        );
        alienPoints = points;

        velocityX = 20;
//        body.setLinearVelocity(velocityX,0);
    }

    public void update(float deltaTime) {

        if (setToDestroy && !isDestroyed)
            destroyAlien();

        stateTimer += deltaTime;
        changeVelocityTimer += deltaTime;

        if (stateTimer > 0.5f){
//            body.setLinearVelocity(velocityX,0);

            stateTimer = 0;

        } else if (changeVelocityTimer > 8){

            velocityX *= -1;
            changeVelocityTimer = -8;

            stateTimer = 0;

            shouldGoDown = true;
        } else if (shouldGoDown) {

//            body.setLinearVelocity(0,-40);

            shouldGoDown = false;
        } //else
//            body.setLinearVelocity(0,0);
    }

    private void destroyAlien() {

        isDestroyed = true;
    }

    @Override
    public void draw(Batch batch) {

        if (!isDestroyed)
            super.draw(batch);
    }


    public void hitByTheBullet(Rectangle bulletBounds) {

        if (actualBounds.overlaps(bulletBounds)){
            setToDestroy = true;

            Hud.addScore(alienPoints);

            actionSound.play(0.6f);
        }
    }
}
