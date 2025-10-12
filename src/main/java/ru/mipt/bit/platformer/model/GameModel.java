package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

// Interface for game models that can be rendered and positioned
public interface GameModel {
    GridPoint2 getCoordinates();
    float getRotation();
}