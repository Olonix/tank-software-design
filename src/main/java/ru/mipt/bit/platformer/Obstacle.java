package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class Obstacle extends GameObject {
    
    public Obstacle(String texturePath, GridPoint2 coordinates, TiledMapTileLayer tileLayer) {
        super(texturePath, coordinates);
        moveRectangleAtTileCenter(tileLayer, rectangle, coordinates);
    }
}