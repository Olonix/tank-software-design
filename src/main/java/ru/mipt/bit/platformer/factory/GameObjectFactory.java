package ru.mipt.bit.platformer.factory;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Tank;
import ru.mipt.bit.platformer.config.GameConfiguration;
import ru.mipt.bit.platformer.graphics.ObstacleGraphics;
import ru.mipt.bit.platformer.model.ObstacleModel;
import ru.mipt.bit.platformer.util.TileMovement;

// Factory for creating game objects (OCP compliance)
public class GameObjectFactory {
    
    private final GameConfiguration config;
    
    public GameObjectFactory(GameConfiguration config) {
        this.config = config;
    }

    public Tank createTank(TileMovement tileMovement) {
        return new Tank(
            config.getPlayerTexturePath(),
            config.getPlayerInitialPosition(),
            config.getTankMovementSpeed(),
            tileMovement
        );
    }

    public Obstacle createObstacle(TiledMapTileLayer tileLayer) {
        ObstacleModel model = new ObstacleModel(config.getObstaclePosition());
        
        ObstacleGraphics graphics = new ObstacleGraphics(
            config.getObstacleTexturePath(),
            model,
            tileLayer
        );
        
        return new Obstacle(model, graphics);
    }

    public Tank createCustomTank(String texturePath, GridPoint2 position, 
                                float movementSpeed, TileMovement tileMovement) {
        return new Tank(texturePath, position, movementSpeed, tileMovement);
    }

    public Obstacle createCustomObstacle(String texturePath, GridPoint2 position,
                                       TiledMapTileLayer tileLayer) {
        ObstacleModel model = new ObstacleModel(position);
        ObstacleGraphics graphics = new ObstacleGraphics(texturePath, model, tileLayer);
        return new Obstacle(model, graphics);
    }
}