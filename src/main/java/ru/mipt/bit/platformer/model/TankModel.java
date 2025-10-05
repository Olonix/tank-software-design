package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Direction;
import static com.badlogic.gdx.math.MathUtils.isEqual;


// Tank model without graphics components
public class TankModel implements GameEntity {
    private static final float MOVEMENT_SPEED = 2f;
    
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation = 0f;

    public TankModel(GridPoint2 initialCoordinates) {
        this.coordinates = new GridPoint2(initialCoordinates);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
    }

    public void update(float deltaTime) {
        if (isMoving()) {
            movementProgress = Math.min(1f, movementProgress + deltaTime * MOVEMENT_SPEED);
            
            if (!isMoving()) {
                coordinates.set(destinationCoordinates);
            }
        }
    }

    public boolean tryMove(Direction direction, ObstacleModel obstacle) {
        if (isMoving()) {
            return false;
        }

        GridPoint2 newDestination = direction.apply(coordinates);
        
        if (obstacle.getCoordinates().equals(newDestination)) {
            return false;
        }

        destinationCoordinates.set(newDestination);
        movementProgress = 0f;
        rotation = direction.getRotation();
        return true;
    }

    public boolean isMoving() {
        return !isEqual(movementProgress, 1f);
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public GridPoint2 getDestinationCoordinates() {
        return new GridPoint2(destinationCoordinates);
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