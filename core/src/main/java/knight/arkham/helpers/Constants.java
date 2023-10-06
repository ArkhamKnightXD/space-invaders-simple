package knight.arkham.helpers;

import knight.arkham.Asteroid;

public class Constants {
    public static final float PIXELS_PER_METER = 32.0f;
    public static final int FULL_SCREEN_HEIGHT = Asteroid.INSTANCE.screenHeight;
    public static final int FULL_SCREEN_WIDTH = Asteroid.INSTANCE.screenWidth;
    public static final short PLAYER_BIT = 1;
    public static final short ALIEN_BIT = 2;
    public static final short BULLET_BIT = 4;
    public static final short STRUCTURE_BIT = 8;
    public static final short ALIEN_BULLET_BIT = 16;
}
