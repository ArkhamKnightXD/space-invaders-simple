package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

public class AlienBullet extends GameObject {
    private boolean setToDestroy;
    private boolean isDestroyed;

    public AlienBullet(Vector2 position, World world) {
        super(
            new Rectangle(
                position.x, position.y, 16, 16
            ), world, "images/ball.png", "fall.wav"
        );

        body.setLinearVelocity(0, -15);
    }

    @Override
    protected Body createBody() {
        return Box2DHelper.createBulletBody(
            new Box2DBody(actualBounds, 0.1f, actualWorld, this)
        );
    }

    public void update(){

        if (setToDestroy && !isDestroyed)
            destroyBullet();

        else if (getPixelPosition().y < 200)
            setToDestroy = true;
    }

    private void destroyBullet() {

        actualWorld.destroyBody(body);
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
