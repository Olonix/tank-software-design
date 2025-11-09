package ru.mipt.bit.platformer;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.config.GameConfiguration;
import ru.mipt.bit.platformer.level.RandomLevelLoader;


public class GameWithRandomLevel {
    public static void main(String[] args) {
        GameConfiguration gameConfig = new GameConfiguration();
        Lwjgl3ApplicationConfiguration appConfig = new Lwjgl3ApplicationConfiguration();

        appConfig.setWindowedMode(gameConfig.getWindowWidth(), gameConfig.getWindowHeight());
        
        RandomLevelLoader randomLoader = new RandomLevelLoader(10, 8, 15, 3);
        
        new Lwjgl3Application(new GameDesktopLauncher(gameConfig, randomLoader), appConfig);
    }
}
