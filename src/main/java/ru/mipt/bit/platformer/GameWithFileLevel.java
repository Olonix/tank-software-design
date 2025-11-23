package ru.mipt.bit.platformer;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.config.GameConfiguration;
import ru.mipt.bit.platformer.config.GameLauncher;


public class GameWithFileLevel {
    public static void main(String[] args) {
        GameLauncher gameLauncher = new GameLauncher();

        GameConfiguration gameConfig = gameLauncher.getContext().getBean(GameConfiguration.class);
        Lwjgl3ApplicationConfiguration appConfig = new Lwjgl3ApplicationConfiguration();

        appConfig.setWindowedMode(gameConfig.getWindowWidth(), gameConfig.getWindowHeight());

        new Lwjgl3Application(gameLauncher.createLauncherWithFileLevel(), appConfig);
    }
}
