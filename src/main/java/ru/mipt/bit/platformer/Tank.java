package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.TileMovement;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank extends GameObject {
    private static final float MOVEMENT_SPEED = 0.4f;
    
    private final TileMovement tileMovement;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;

    public Tank(String texturePath, GridPoint2 initialCoordinates, TileMovement tileMovement) {
        super(texturePath, initialCoordinates);
        this.tileMovement = tileMovement;
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
    }

    public void update(float deltaTime) {
        // update movement progress
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);

        // update position
        tileMovement.moveRectangleBetweenTileCenters(rectangle, coordinates, destinationCoordinates, movementProgress);

        // check if movement is complete
        if (isEqual(movementProgress, 1f)) {
            coordinates.set(destinationCoordinates);
        }
    }

    public boolean tryMove(Direction direction, Obstacle obstacle) {
        if (!isEqual(movementProgress, 1f)) {
            return false;
        }

        GridPoint2 newDestination = direction.apply(coordinates);
        
        // сheck collision with obstacle
        if (obstacle.getCoordinates().equals(newDestination)) {
            return false;
        }

        // start movement
        destinationCoordinates.set(newDestination);
        movementProgress = 0f;
        rotation = direction.getRotation();
        return true;
    }

    public boolean isMoving() {
        return !isEqual(movementProgress, 1f);
    }
}