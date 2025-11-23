package ru.mipt.bit.platformer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import ru.mipt.bit.platformer.InputHandler;
import ru.mipt.bit.platformer.factory.GameObjectFactory;
import ru.mipt.bit.platformer.level.FileLevelLoader;
import ru.mipt.bit.platformer.level.LevelLoader;
import ru.mipt.bit.platformer.level.LogicalLevel;
import ru.mipt.bit.platformer.level.RandomLevelLoader;

@Configuration
public class GameSpringConfiguration {

    @Bean
    public GameConfiguration gameConfiguration() {
        return new GameConfiguration();
    }

    @Bean
    public GameObjectFactory gameObjectFactory(GameConfiguration config) {
        return new GameObjectFactory(config);
    }

    @Bean
    @Primary
    public LevelLoader fileLevelLoader() {
        return new FileLevelLoader("level_sample.txt");
    }

    @Bean
    public LevelLoader randomLevelLoader() {
        return new RandomLevelLoader(10, 8, 15, 3);
    }

    @Bean
    @Scope("prototype")
    public LogicalLevel logicalLevel() {
        return new LogicalLevel();
    }

    @Bean
    public InputHandler inputHandler() {
        return new InputHandler();
    }
}
