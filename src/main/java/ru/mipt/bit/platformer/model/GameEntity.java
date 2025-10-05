package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;


// Base interface for all game entities (model without graphics)
public interface GameEntity {
    GridPoint2 getCoordinates();
    float getRotation();
}