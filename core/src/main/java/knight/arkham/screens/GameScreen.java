package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import knight.arkham.Asteroid;
import knight.arkham.helpers.AssetsHelper;
import knight.arkham.objects.*;
import knight.arkham.scenes.Hud;
import knight.arkham.scenes.PauseMenu;

public class GameScreen extends ScreenAdapter {
    private final Asteroid game;
    private final OrthographicCamera camera;
    public SpriteBatch batch;
    private final Hud hud;
    private final PauseMenu pauseMenu;
    private final Player player;
    private final Array<Structure> structures;
    private final Array<Alien> aliens;
    private final Sound winSound;
    private final Array<Bullet> bullets;
    private final Array<AlienBullet> alienBullets;
    private long lastAlienBulletTime;
    private float bulletSpawnTime;
    public static boolean isGamePaused;


    public GameScreen() {

        game = Asteroid.INSTANCE;

        camera = game.camera;

        batch = new SpriteBatch();

        player = new Player(new Rectangle(1000, 350, 32, 32));

        Structure structure = new Structure(new Rectangle(650, 450, 48, 32));
        Structure structure2 = new Structure(new Rectangle(900, 450, 48, 32));
        Structure structure3 = new Structure(new Rectangle(1150, 450, 48, 32));
        Structure structure4 = new Structure(new Rectangle(1400, 450, 48, 32));

        structures = new Array<>();

        structures.add(structure, structure2, structure3, structure4);

        aliens = createAliens();

        winSound = AssetsHelper.loadSound("win.wav");

        hud = new Hud();
        pauseMenu = new PauseMenu();

        bullets = new Array<>();
        alienBullets = new Array<>();
        spawnAlienBullet();

        isGamePaused = false;
    }

    private void spawnAlienBullet() {

        Rectangle bulletBounds = new Rectangle();

        float firstAlienXPosition = aliens.get(0).getBounds().x;
        float lastAlienXPosition = aliens.get(aliens.size - 1).getBounds().x;

        bulletBounds.x = MathUtils.random(firstAlienXPosition, lastAlienXPosition - 32);
        bulletBounds.y = 680;

        alienBullets.add(new AlienBullet(new Vector2(bulletBounds.x, bulletBounds.y)));

        lastAlienBulletTime = TimeUtils.nanoTime();
    }


    private Array<Alien> createAliens() {
        int positionX;
        int positionY = 0;
        int alienPoints = 8;
        String spritePath;

        Array<Alien> temporalAliens = new Array<>();

        for (int i = 0; i < 5; i++) {

            positionX = 0;

            if (i == 0)
                spritePath = "images/blue-alien.png";
            else if (i >= 3)
                spritePath = "images/green-alien.png";
            else
                spritePath = "images/red-alien.png";


            for (int j = 0; j < 11; j++) {

                temporalAliens.add(new Alien(positionX, positionY, spritePath, alienPoints));
                positionX += 60;
            }

            alienPoints--;
            positionY += 40;
        }

        return temporalAliens;
    }


    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
    }

    private void update(float deltaTime) {

        player.update(deltaTime);

        for (Structure structure : structures)
            structure.update();

        for (Alien alien : aliens){
            alien.update(deltaTime);

            for (Bullet bullet : bullets)
                alien.hitByTheBullet(bullet);
        }

        for (AlienBullet alienBullet : alienBullets)
            alienBullet.update(deltaTime);

        if(TimeUtils.nanoTime() - lastAlienBulletTime > 2000000000)
            spawnAlienBullet();

        for (Bullet bullet : bullets)
            bullet.update(deltaTime);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            shootBullet(deltaTime);
    }

    private void shootBullet(float deltaTime) {

        bulletSpawnTime += deltaTime;

        if (bulletSpawnTime > 1) {

            bullets.add(new Bullet(new Vector2(player.getBounds().x, player.getBounds().y)));

            bulletSpawnTime = 0;
        }
    }



    @Override
    public void render(float deltaTime) {

        ScreenUtils.clear(0, 0, 0, 0);

        if (!isGamePaused) {
            update(deltaTime);
            draw();
        } else {

            pauseMenu.stage.act();
            pauseMenu.stage.draw();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F1))
            isGamePaused = !isGamePaused;
    }

    private void draw() {

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        player.draw(batch);

        for (Alien alien : aliens)
            alien.draw(batch);

        for (Bullet bullet : bullets)
            bullet.draw(batch);

        for (AlienBullet alienBullet : alienBullets)
            alienBullet.draw(batch);

        for (Structure structure : structures)
            structure.draw(batch);

        batch.end();

        hud.stage.draw();
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        player.dispose();
        hud.dispose();
        pauseMenu.dispose();
        winSound.dispose();
        batch.dispose();

        for (Structure structure : structures)
            structure.dispose();

        for (Alien alien : aliens)
            alien.dispose();
    }
}
