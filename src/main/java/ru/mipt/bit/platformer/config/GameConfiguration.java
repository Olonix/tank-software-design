package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;

// Configuration class for game settings
public class GameConfiguration {
    
    // Window configuration
    private final int windowWidth;
    private final int windowHeight;
    
    // Level configuration
    private final String levelPath;
    
    // Player configuration
    private final String playerTexturePath;
    private final GridPoint2 playerInitialPosition;
    
    // Obstacle configuration  
    private final String obstacleTexturePath;
    private final GridPoint2 obstaclePosition;
    
    // Game mechanics
    private final float tankMovementSpeed;
    private final float backgroundRed;
    private final float backgroundGreen;
    private final float backgroundBlue;
    private final float backgroundAlpha;
    
    public GameConfiguration() {
        this.windowWidth = 1280;
        this.windowHeight = 1024;
        this.levelPath = "level.tmx";
        this.playerTexturePath = "images/tank_blue.png";
        this.playerInitialPosition = new GridPoint2(1, 1);
        this.obstacleTexturePath = "images/greenTree.png";
        this.obstaclePosition = new GridPoint2(1, 3);
        this.tankMovementSpeed = 2f;
        this.backgroundRed = 0f;
        this.backgroundGreen = 0f;
        this.backgroundBlue = 0.2f;
        this.backgroundAlpha = 1f;
    }
    
    public GameConfiguration(int windowWidth, int windowHeight, String levelPath,
                           String playerTexturePath, GridPoint2 playerInitialPosition,
                           String obstacleTexturePath, GridPoint2 obstaclePosition,
                           float tankMovementSpeed, float backgroundRed, 
                           float backgroundGreen, float backgroundBlue, float backgroundAlpha) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.levelPath = levelPath;
        this.playerTexturePath = playerTexturePath;
        this.playerInitialPosition = new GridPoint2(playerInitialPosition);
        this.obstacleTexturePath = obstacleTexturePath;
        this.obstaclePosition = new GridPoint2(obstaclePosition);
        this.tankMovementSpeed = tankMovementSpeed;
        this.backgroundRed = backgroundRed;
        this.backgroundGreen = backgroundGreen;
        this.backgroundBlue = backgroundBlue;
        this.backgroundAlpha = backgroundAlpha;
    }
    
    public int getWindowWidth() { return windowWidth; }
    public int getWindowHeight() { return windowHeight; }
    public String getLevelPath() { return levelPath; }
    public String getPlayerTexturePath() { return playerTexturePath; }
    public GridPoint2 getPlayerInitialPosition() { return new GridPoint2(playerInitialPosition); }
    public String getObstacleTexturePath() { return obstacleTexturePath; }
    public GridPoint2 getObstaclePosition() { return new GridPoint2(obstaclePosition); }
    public float getTankMovementSpeed() { return tankMovementSpeed; }
    public float getBackgroundRed() { return backgroundRed; }
    public float getBackgroundGreen() { return backgroundGreen; }
    public float getBackgroundBlue() { return backgroundBlue; }
    public float getBackgroundAlpha() { return backgroundAlpha; }
}