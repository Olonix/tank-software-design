package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;


// Obstacle model without graphics
public class ObstacleModel implements GameEntity {
    private final GridPoint2 coordinates;
    private final float rotation = 0f;

    public ObstacleModel(GridPoint2 coordinates) {
        this.coordinates = new GridPoint2(coordinates);
    }

    @Override
    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    @Override
    public float getRotation() {
        return rotation;
    }
}