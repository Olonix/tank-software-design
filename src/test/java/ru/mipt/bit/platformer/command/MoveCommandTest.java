package ru.mipt.bit.platformer.command;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.Direction;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveCommandTest {

    @Test
    void testSuccessfulMove() {
        TankModel tank = new TankModel(new GridPoint2(0, 0));
        List<Obstacle> obstacles = Collections.emptyList();
        List<TankModel> otherTanks = Collections.emptyList();

        MoveCommand command = new MoveCommand(tank, Direction.RIGHT, obstacles, otherTanks, 10, 10);

        command.execute();

        assertTrue(command.wasSuccessful());
        assertTrue(tank.isMoving());
        assertEquals(new GridPoint2(1, 0), tank.getDestinationCoordinates());
    }

    @Test
    void testMoveBlockedByBoundary() {
        TankModel tank = new TankModel(new GridPoint2(9, 0));
        List<Obstacle> obstacles = Collections.emptyList();
        List<TankModel> otherTanks = Collections.emptyList();

        MoveCommand command = new MoveCommand(tank, Direction.RIGHT, obstacles, otherTanks, 10, 10);

        command.execute();

        assertFalse(command.wasSuccessful());
        assertFalse(tank.isMoving());
        assertTrue(command.isFinished()); // Invalid moves finish immediately
    }

    @Test
    void testMoveBlockedByOtherTank() {
        TankModel tank = new TankModel(new GridPoint2(0, 0));
        TankModel otherTank = new TankModel(new GridPoint2(1, 0));
        List<Obstacle> obstacles = Collections.emptyList();
        List<TankModel> otherTanks = Arrays.asList(otherTank);

        MoveCommand command = new MoveCommand(tank, Direction.RIGHT, obstacles, otherTanks, 10, 10);

        command.execute();

        assertFalse(command.wasSuccessful());
        assertFalse(tank.isMoving());
        assertTrue(command.isFinished()); // Invalid moves finish immediately
    }

    @Test
    void testCommandNotFinishedWhileMoving() {
        TankModel tank = new TankModel(new GridPoint2(0, 0));
        List<Obstacle> obstacles = Collections.emptyList();
        List<TankModel> otherTanks = Collections.emptyList();

        MoveCommand command = new MoveCommand(tank, Direction.RIGHT, obstacles, otherTanks, 10, 10);

        command.execute();

        assertFalse(command.isFinished());
        assertTrue(tank.isMoving());
    }

    @Test
    void testCommandFinishedAfterMovement() {
        TankModel tank = new TankModel(new GridPoint2(0, 0));
        List<Obstacle> obstacles = Collections.emptyList();
        List<TankModel> otherTanks = Collections.emptyList();

        MoveCommand command = new MoveCommand(tank, Direction.RIGHT, obstacles, otherTanks, 10, 10);

        command.execute();
        // Simulate movement completion
        tank.update(1.0f); // With speed 2.0f, this should complete the movement

        assertTrue(command.isFinished());
        assertFalse(tank.isMoving());
    }
}
