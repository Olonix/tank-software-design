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
import ru.mipt.bit.platformer.config.GameConfiguration;
import ru.mipt.bit.platformer.factory.GameObjectFactory;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    
    private Tank player;
    private Obstacle treeObstacle;
    private InputHandler inputHandler;
    
    private final GameConfiguration config;
    private final GameObjectFactory factory;
    
    public GameDesktopLauncher(GameConfiguration config) {
        this.config = config;
        this.factory = new GameObjectFactory(config);
    }
    
    public GameDesktopLauncher() {
        this(new GameConfiguration());
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
        player = factory.createTank(tileMovement);
        treeObstacle = factory.createObstacle(groundLayer);
        
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
            player.tryMove(inputDirection, treeObstacle);
        }

        // update game objects
        player.update(deltaTime);

        // render everything
        levelRenderer.render();

        batch.begin();
        player.render(batch);
        treeObstacle.render(batch);
        batch.end();
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
        treeObstacle.dispose();
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
