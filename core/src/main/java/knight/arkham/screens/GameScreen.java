package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Asteroid;
import knight.arkham.helpers.AssetsHelper;
import knight.arkham.helpers.GameContactListener;
import knight.arkham.objects.*;
import knight.arkham.scenes.Hud;
import knight.arkham.scenes.PauseMenu;

public class GameScreen extends ScreenAdapter {
    private final Asteroid game;
    private final OrthographicCamera camera;
    public SpriteBatch batch;
    private final Hud hud;
    private final PauseMenu pauseMenu;
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    private final Player player;
    private final Array<Structure> structures;
    private final Array<Alien> aliens;
    private final Sound winSound;
    private float bulletSpawnTime;
    private final Array<AlienBullet> alienBullets;
    public static boolean isGamePaused;


    public GameScreen() {

        game = Asteroid.INSTANCE;

        camera = game.camera;

        batch = new SpriteBatch();

        world = new World(new Vector2(0, 0), true);

        world.setContactListener(new GameContactListener());

        debugRenderer = new Box2DDebugRenderer();

        player = new Player(new Rectangle(1000, 350, 32, 32), world);

        Structure structure = new Structure(new Rectangle(650, 450, 48, 32), world);
        Structure structure2 = new Structure(new Rectangle(900, 450, 48, 32), world);
        Structure structure3 = new Structure(new Rectangle(1150, 450, 48, 32), world);
        Structure structure4 = new Structure(new Rectangle(1400, 450, 48, 32), world);

        structures = new Array<>();

        structures.add(structure, structure2, structure3, structure4);

        aliens = createAliens();

        winSound = AssetsHelper.loadSound("win.wav");

        hud = new Hud();
        pauseMenu = new PauseMenu();

        alienBullets = new Array<>();

        isGamePaused = false;
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

                temporalAliens.add(new Alien(positionX, positionY, world, spritePath, alienPoints));
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

        world.step(1 / 60f, 6, 2);

        player.update(deltaTime);

        for (Structure structure : structures)
            structure.update();

        bulletSpawnTime += deltaTime;

        for (Alien alien : aliens) {
            alien.update(deltaTime);

            if (bulletSpawnTime > 5){
                alienBullets.add(alien.shootBullet());
                bulletSpawnTime = 0;
            }
        }

        for (AlienBullet bullet : alienBullets)
            bullet.update();
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

        for (AlienBullet bullet : alienBullets)
            bullet.draw(batch);

        for (Structure structure : structures)
            structure.draw(batch);

        batch.end();

        hud.stage.draw();

        debugRenderer.render(world, camera.combined);
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
        world.dispose();
        batch.dispose();
        debugRenderer.dispose();

        for (Structure structure : structures)
            structure.dispose();

        for (Alien alien : aliens)
            alien.dispose();
    }
}
