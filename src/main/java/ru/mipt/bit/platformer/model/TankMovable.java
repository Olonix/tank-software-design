package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

// Interface for tank-specific movable behavior
public interface TankMovable extends Movable {
    GridPoint2 getDestinationCoordinates();
    float getMovementProgress();
}