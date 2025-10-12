package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.Direction;

// Interface for movable game entities
public interface Movable extends GameModel {
    void update(float deltaTime);
    boolean tryMove(Direction direction, GameModel obstacle);
    boolean isMoving();
}