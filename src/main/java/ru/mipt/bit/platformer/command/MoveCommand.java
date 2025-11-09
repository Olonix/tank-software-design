package ru.mipt.bit.platformer.command;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Direction;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.model.TankMovable;

import java.util.List;

public class MoveCommand implements Command {
    private final TankMovable tank;
    private final Direction direction;
    private final List<Obstacle> obstacles;
    private final List<? extends TankMovable> otherTanks;
    private final int levelWidth;
    private final int levelHeight;
    private boolean executed = false;
    private boolean success = false;

    public MoveCommand(TankMovable tank, Direction direction, List<Obstacle> obstacles,
                      List<? extends TankMovable> otherTanks, int levelWidth, int levelHeight) {
        this.tank = tank;
        this.direction = direction;
        this.obstacles = obstacles;
        this.otherTanks = otherTanks;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
    }

    @Override
    public void execute() {
        if (!executed) {
            executed = true;
            if (canMove()) {
                if (tank instanceof ru.mipt.bit.platformer.model.TankModel) {
                    success = ((ru.mipt.bit.platformer.model.TankModel) tank).move(direction);
                }
            } else {
                success = false;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return executed && !tank.isMoving();
    }

    public boolean wasSuccessful() {
        return success;
    }

    private boolean canMove() {
        if (tank.isMoving()) {
            return false;
        }

        GridPoint2 newPosition = direction.apply(tank.getCoordinates());

        if (newPosition.x < 0 || newPosition.x >= levelWidth || newPosition.y < 0 || newPosition.y >= levelHeight) {
            return false;
        }

        for (Obstacle obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(newPosition)) {
                return false;
            }
        }

        for (TankMovable otherTank : otherTanks) {
            if (otherTank != tank) {
                if (otherTank.getCoordinates().equals(newPosition) || otherTank.getDestinationCoordinates().equals(newPosition)) {
                    return false;
                }
                if (otherTank.getDestinationCoordinates().equals(tank.getCoordinates())) {
                    return false;
                }
            }
        }

        return true;
    }
}
