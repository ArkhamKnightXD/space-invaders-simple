package knight.arkham.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import knight.arkham.helpers.AssetsHelper;

public abstract class GameObject {
    protected final Rectangle actualBounds;
    protected final Texture sprite;
    protected final Sound actionSound;

    protected GameObject(Rectangle bounds, String spritePath, String soundPath) {
        actualBounds = bounds;
        sprite = new Texture(spritePath);
        actionSound = AssetsHelper.loadSound(soundPath);
    }

    public void draw(Batch batch) {

        batch.draw(sprite, actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }

    public void dispose() {
        sprite.dispose();
        actionSound.dispose();
    }
}
