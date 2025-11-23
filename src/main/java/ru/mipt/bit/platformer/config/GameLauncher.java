package ru.mipt.bit.platformer.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.mipt.bit.platformer.GameDesktopLauncher;
import ru.mipt.bit.platformer.level.LevelLoader;

public class GameLauncher {

    private final ApplicationContext context;

    public GameLauncher() {
        this.context = new AnnotationConfigApplicationContext(GameSpringConfiguration.class);
    }

    public GameDesktopLauncher createLauncherWithFileLevel() {
        LevelLoader fileLevelLoader = context.getBean("fileLevelLoader", LevelLoader.class);
        return createLauncher(fileLevelLoader);
    }

    public GameDesktopLauncher createLauncherWithRandomLevel() {
        LevelLoader randomLevelLoader = context.getBean("randomLevelLoader", LevelLoader.class);
        return createLauncher(randomLevelLoader);
    }

    public GameDesktopLauncher createDefaultLauncher() {
        return createLauncher(null);
    }

    private GameDesktopLauncher createLauncher(LevelLoader levelLoader) {
        GameConfiguration config = context.getBean(GameConfiguration.class);
        return new GameDesktopLauncher(config, levelLoader, context);
    }

    public ApplicationContext getContext() {
        return context;
    }
}
