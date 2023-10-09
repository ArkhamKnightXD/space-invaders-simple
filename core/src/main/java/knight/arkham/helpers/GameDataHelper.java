package knight.arkham.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import knight.arkham.objects.Player;

public class GameDataHelper {

    private static final String dataFilename = "space-invaders-data";

    public static void saveHighScore(){

        Preferences preferences = Gdx.app.getPreferences(dataFilename);

        if (Player.score < loadHighScore())
            preferences.putInteger("playerScore", Player.score);

        else {
            preferences.putInteger("playerScore", Player.score);
            preferences.putInteger("highScore", Player.score);
        }

        preferences.flush();
    }

    public static int loadHighScore(){

        Preferences preferences = Gdx.app.getPreferences(dataFilename);

        return preferences.getInteger("highScore");
    }

    public static int loadPlayerScore(){

        Preferences preferences = Gdx.app.getPreferences(dataFilename);

        return preferences.getInteger("playerScore");
    }
}
