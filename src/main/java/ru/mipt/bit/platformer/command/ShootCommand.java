package ru.mipt.bit.platformer.command;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Direction;
import ru.mipt.bit.platformer.model.BulletModel;
import ru.mipt.bit.platformer.model.TankMovable;

public class ShootCommand implements Command {
    private final TankMovable tank;
    private boolean executed = false;
    private boolean success = false;
    private BulletModel createdBullet = null;

    public ShootCommand(TankMovable tank) {
        this.tank = tank;
    }

    @Override
    public void execute() {
        if (!executed) {
            executed = true;
            // Create bullet
            GridPoint2 startPosition = getBulletStartPosition();
            Direction direction = getBulletDirection();
            createdBullet = new BulletModel(startPosition, direction);
            success = true;
        }
    }

    @Override
    public boolean isFinished() {
        return executed;
    }

    public boolean wasSuccessful() {
        return success;
    }

    public TankMovable getTank() {
        return tank;
    }

    public BulletModel getCreatedBullet() {
        return createdBullet;
    }

    private GridPoint2 getBulletStartPosition() {
        Direction tankDirection = getTankDirection();
        return tankDirection.apply(tank.getCoordinates());
    }

    private Direction getBulletDirection() {
        return getTankDirection();
    }

    private Direction getTankDirection() {
        float rotation = tank.getRotation();
        if (rotation == 0f) return Direction.RIGHT;
        if (rotation == 90f) return Direction.UP;
        if (rotation == -90f) return Direction.DOWN;
        if (rotation == -180f || rotation == 180f) return Direction.LEFT;
        return Direction.RIGHT;
    }
}
