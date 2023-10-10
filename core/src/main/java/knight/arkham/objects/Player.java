package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import knight.arkham.scenes.Hud;

public class Player extends GameObject {
    public static int score;
    public static int livesQuantity;
    private final int speed;

    public Player(Rectangle bounds) {
        super(bounds, "images/player-ship.png", "laser.wav");
        score = 0;
        livesQuantity = 2;
        speed = 400;
    }

    public void update(float deltaTime) {

        if (actualBounds.x < 1500 && Gdx.input.isKeyPressed(Input.Keys.D))
            actualBounds.x += speed * deltaTime;

        else if (actualBounds.x > 520 && Gdx.input.isKeyPressed(Input.Keys.A))
            actualBounds.x -= speed * deltaTime;
    }

    public boolean hitByTheBullet(AlienBullet alienBullet) {

        if (actualBounds.overlaps(alienBullet.getBounds())){

            actionSound.play(0.6f);

            Hud.takeAvailableHealth();

            alienBullet.collision();

            return true;
        }

        return false;
    }
}
