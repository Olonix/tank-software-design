package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public enum Direction {
    UP(90f, (point) -> incrementedY(point)),
    DOWN(-90f, (point) -> decrementedY(point)),
    LEFT(-180f, (point) -> decrementedX(point)),
    RIGHT(0f, (point) -> incrementedX(point));

    private final float rotation;
    private final GridPointTransform transform;

    Direction(float rotation, GridPointTransform transform) {
        this.rotation = rotation;
        this.transform = transform;
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 apply(GridPoint2 point) {
        return transform.apply(point);
    }

    @FunctionalInterface
    private interface GridPointTransform {
        GridPoint2 apply(GridPoint2 point);
    }
}