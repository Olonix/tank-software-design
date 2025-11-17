package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.Direction;

public class BulletModel implements GameEntity {
    private Vector2 coordinates;
    private final Direction direction;
    private final float speed;
    private boolean destroyed = false;
    private final int damage;

    public BulletModel(GridPoint2 startPosition, Direction direction, float speed, int damage) {
        this.coordinates = new Vector2(startPosition.x, startPosition.y);
        this.direction = direction;
        this.speed = speed;
        this.damage = damage;
    }

    public BulletModel(GridPoint2 startPosition, Direction direction) {
        this(startPosition, direction, 4f, 25); // default speed and damage
    }

    public void update(float deltaTime) {
        if (!destroyed) {
            coordinates.x += direction.getDirectionVector().x * speed * deltaTime;
            coordinates.y += direction.getDirectionVector().y * speed * deltaTime;
        }
    }

    @Override
    public GridPoint2 getCoordinates() {
        return new GridPoint2((int)coordinates.x, (int)coordinates.y);
    }

    public Vector2 getPreciseCoordinates() {
        return new Vector2(coordinates);
    }

    @Override
    public float getRotation() {
        return direction.getRotation();
    }

    public Direction getDirection() {
        return direction;
    }

    public int getDamage() {
        return damage;
    }

    public void destroy() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
