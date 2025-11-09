package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.config.GameConfiguration;
import ru.mipt.bit.platformer.factory.GameObjectFactory;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.level.LevelData;
import ru.mipt.bit.platformer.level.LevelLoader;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    
    private Tank player;
    private List<Obstacle> obstacles;
    private List<AITank> aiTanks;
    private InputHandler inputHandler;
    private int levelWidth;
    private int levelHeight;
    private boolean showHealthBars = false;
    
    private final GameConfiguration config;
    private final GameObjectFactory factory;
    private final LevelLoader levelLoader;
    
    public GameDesktopLauncher(GameConfiguration config, LevelLoader levelLoader) {
        this.config = config;
        this.factory = new GameObjectFactory(config);
        this.levelLoader = levelLoader;
    }
    
    public GameDesktopLauncher(GameConfiguration config) {
        this(config, null);
    }
    
    public GameDesktopLauncher() {
        this(new GameConfiguration(), null);
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles using configuration
        level = new TmxMapLoader().load(config.getLevelPath());
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // create game objects using factory
        obstacles = new ArrayList<>();
        aiTanks = new ArrayList<>();
        levelWidth = groundLayer.getWidth();
        levelHeight = groundLayer.getHeight();

        // Load level if loader is provided
        if (levelLoader != null) {
            LevelData levelData = levelLoader.loadLevel();
            
            // Create player at loaded position
            player = factory.createCustomTank(
                config.getPlayerTexturePath(),
                levelData.getPlayerStartPosition(),
                config.getTankMovementSpeed(),
                tileMovement
            );
            
            // Create obstacles at loaded positions
            for (GridPoint2 obstaclePos : levelData.getObstaclePositions()) {
                Obstacle obstacle = factory.createCustomObstacle(
                    config.getObstacleTexturePath(),
                    obstaclePos,
                    groundLayer
                );
                obstacles.add(obstacle);
            }

            // Create AI tanks at loaded positions
            for (GridPoint2 aiTankPos : levelData.getAITankPositions()) {
                AITank aiTank = new AITank(
                    config.getPlayerTexturePath(), // Use same texture for now
                    aiTankPos,
                    tileMovement
                );
                aiTanks.add(aiTank);
            }
        } else {
            // Use default configuration
            player = factory.createTank(tileMovement);
            Obstacle treeObstacle = factory.createObstacle(groundLayer);
            obstacles.add(treeObstacle);
        }
        
        // create input handler
        inputHandler = new InputHandler();
    }

    @Override
    public void render() {
        // clear the screen using configuration
        Gdx.gl.glClearColor(config.getBackgroundRed(), config.getBackgroundGreen(), 
                           config.getBackgroundBlue(), config.getBackgroundAlpha());
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        // handle input
        Direction inputDirection = inputHandler.getInputDirection();
        if (inputDirection != null) {
            // Check collision with all obstacles and AI tanks
            if (canPlayerMove(inputDirection)) {
                // No collision detected, perform movement
                player.move(inputDirection);
            }
        }

        // handle health bar toggle
        if (inputHandler.isToggleHealthPressed()) {
            showHealthBars = !showHealthBars;
            player.getHealthBarDecorator().setShowHealthBar(showHealthBars);
            for (AITank aiTank : aiTanks) {
                aiTank.getHealthBarDecorator().setShowHealthBar(showHealthBars);
            }
        }

        // update game objects
        player.update(deltaTime);

        // update AI tanks
        for (AITank aiTank : aiTanks) {
            aiTank.update(deltaTime, obstacles, aiTanks, player.getModel(), levelWidth, levelHeight);
        }

        // render everything
        levelRenderer.render();

        batch.begin();
        player.render(batch);
        for (Obstacle obstacle : obstacles) {
            obstacle.render(batch);
        }
        for (AITank aiTank : aiTanks) {
            aiTank.render(batch);
        }
        batch.end();
    }

    private boolean canPlayerMove(Direction direction) {
        if (player.isMoving()) {
            return false;
        }

        GridPoint2 newPosition = direction.apply(player.getCoordinates());

        // Check bounds
        if (newPosition.x < 0 || newPosition.x >= levelWidth || newPosition.y < 0 || newPosition.y >= levelHeight) {
            return false;
        }

        // Check obstacles
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(newPosition)) {
                return false;
            }
        }

        // Check AI tanks
        for (AITank aiTank : aiTanks) {
            if (aiTank.getCoordinates().equals(newPosition) || aiTank.getDestinationCoordinates().equals(newPosition)) {
                return false;
            }
            // Check if AI tank is moving to player's position
            if (aiTank.getDestinationCoordinates().equals(player.getCoordinates())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        for (Obstacle obstacle : obstacles) {
            obstacle.dispose();
        }
        for (AITank aiTank : aiTanks) {
            aiTank.dispose();
        }
        player.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        GameConfiguration gameConfig = new GameConfiguration();
        Lwjgl3ApplicationConfiguration appConfig = new Lwjgl3ApplicationConfiguration();
        
        // Set window size from game configuration
        appConfig.setWindowedMode(gameConfig.getWindowWidth(), gameConfig.getWindowHeight());
        
        new Lwjgl3Application(new GameDesktopLauncher(gameConfig), appConfig);
    }
}
