package knight.arkham.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.AssetsHelper;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public abstract class GameObject {
    protected final Rectangle actualBounds;
    protected final World actualWorld;
    protected final Texture sprite;
    protected final Sound actionSound;
    protected final Body body;
    private final boolean hasStaticBody;
    private Rectangle drawBounds;

    protected GameObject(Rectangle bounds, World world, String spritePath, String soundPath) {
        actualBounds = bounds;
        actualWorld = world;
        sprite = new Texture(spritePath);
        actionSound = AssetsHelper.loadSound(soundPath);

        body = createBody();
        hasStaticBody = body.getType() == BodyDef.BodyType.StaticBody;

        drawBounds = getDrawBounds();
    }

    protected abstract Body createBody();

    private Rectangle getDrawBounds() {

        return new Rectangle(
            body.getPosition().x - (actualBounds.width / 2 / PIXELS_PER_METER),
            body.getPosition().y - (actualBounds.height / 2 / PIXELS_PER_METER),
            actualBounds.width / PIXELS_PER_METER,
            actualBounds.height / PIXELS_PER_METER
        );
    }

    public void draw(Batch batch) {

        if(!hasStaticBody)
            drawBounds = getDrawBounds();

        batch.draw(sprite, drawBounds.x, drawBounds.y, drawBounds.width, drawBounds.height);
    }

    protected Vector2 getPixelPosition() {
        return new Vector2(
            body.getPosition().x * PIXELS_PER_METER,
            body.getPosition().y * PIXELS_PER_METER
        );
    }

    public void dispose() {
        sprite.dispose();
        actionSound.dispose();
    }
}
