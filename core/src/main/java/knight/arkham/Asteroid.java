package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.screens.MainMenuScreen;

public class Asteroid extends Game {
    public static Asteroid INSTANCE;
    public OrthographicCamera camera;
    public Viewport viewport;
    public int screenWidth;
    public int screenHeight;
    public AssetDescriptor<Skin> uiSkin;

    public Asteroid() {

        INSTANCE = this;
    }

    @Override
    public void create() {

        camera = new OrthographicCamera();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        viewport = new FitViewport(screenWidth, screenHeight, camera);

        camera.position.set(screenWidth, screenHeight, 0);

        uiSkin = new AssetDescriptor<>("ui/uiskin.json", Skin.class, new SkinLoader.SkinParameter("ui/uiskin.atlas"));

        setScreen(new MainMenuScreen());
    }
}
