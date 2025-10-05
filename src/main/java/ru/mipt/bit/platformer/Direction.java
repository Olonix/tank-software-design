package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;


public enum Direction {
    UP(90f, new Vector2(0, 1), (point) -> incrementedY(point)),
    DOWN(-90f, new Vector2(0, -1), (point) -> decrementedY(point)),
    LEFT(-180f, new Vector2(-1, 0), (point) -> decrementedX(point)),
    RIGHT(0f, new Vector2(1, 0), (point) -> incrementedX(point));

    private final float rotation;
    private final Vector2 directionVector;
    private final GridPointTransform transform;

    Direction(float rotation, Vector2 directionVector, GridPointTransform transform) {
        this.rotation = rotation;
        this.directionVector = new Vector2(directionVector);
        this.transform = transform;
    }

    public float getRotation() {
        return rotation;
    }

    public Vector2 getDirectionVector() {
        return new Vector2(directionVector);
    }

    public GridPoint2 apply(GridPoint2 point) {
        return transform.apply(point);
    }

    @FunctionalInterface
    private interface GridPointTransform {
        GridPoint2 apply(GridPoint2 point);
    }
}