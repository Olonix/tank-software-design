package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.graphics.ObstacleGraphics;
import ru.mipt.bit.platformer.model.ObstacleModel;


public class Obstacle {
    private final ObstacleModel model;
    private final ObstacleGraphics graphics;
    
    public Obstacle(String texturePath, GridPoint2 coordinates, TiledMapTileLayer tileLayer) {
        this.model = new ObstacleModel(coordinates);
        this.graphics = new ObstacleGraphics(texturePath, model, tileLayer);
    }

    public void render(Batch batch) {
        graphics.render(batch);
    }

    public void dispose() {
        graphics.dispose();
    }

    public GridPoint2 getCoordinates() {
        return model.getCoordinates();
    }

    public ObstacleModel getModel() {
        return model;
    }
}