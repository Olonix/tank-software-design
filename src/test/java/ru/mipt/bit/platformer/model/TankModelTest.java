package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.Direction;

import static org.junit.jupiter.api.Assertions.*;

class TankModelTest {
    private TankModel tank;
    private ObstacleModel obstacle;

    @BeforeEach
    void setUp() {
        tank = new TankModel(new GridPoint2(1, 1));
        obstacle = new ObstacleModel(new GridPoint2(2, 1));
    }

    @Test
    void testInitialPosition() {
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
        assertEquals(0f, tank.getRotation());
        assertFalse(tank.isMoving());
        assertEquals(1f, tank.getMovementProgress());
    }

    @Test
    void testSuccessfulMove() {
        boolean result = tank.tryMove(Direction.UP, obstacle);
        
        assertTrue(result);
        assertTrue(tank.isMoving());
        assertEquals(0f, tank.getMovementProgress());
        assertEquals(Direction.UP.getRotation(), tank.getRotation());
        assertEquals(new GridPoint2(1, 2), tank.getDestinationCoordinates());
    }

    @Test
    void testMoveBlockedByObstacle() {
        boolean result = tank.tryMove(Direction.RIGHT, obstacle);
        
        assertFalse(result);
        assertFalse(tank.isMoving());
        assertEquals(1f, tank.getMovementProgress());
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
    }

    @Test
    void testCannotMoveWhileAlreadyMoving() {
        tank.tryMove(Direction.UP, obstacle);
        assertTrue(tank.isMoving());
        
        boolean result = tank.tryMove(Direction.DOWN, obstacle);
        
        assertFalse(result);
        assertEquals(Direction.UP.getRotation(), tank.getRotation());
        assertEquals(new GridPoint2(1, 2), tank.getDestinationCoordinates());
    }

    @Test
    void testMovementUpdate() {
        tank.tryMove(Direction.UP, obstacle);
        
        tank.update(3.0f); 
        
        assertFalse(tank.isMoving());
        assertEquals(1f, tank.getMovementProgress());
        assertEquals(new GridPoint2(1, 2), tank.getCoordinates());
    }

    @Test
    void testPartialMovementUpdate() {
        tank.tryMove(Direction.UP, obstacle);
        
        // With speed 2.0f, 0.1f gives 0.2f progress
        tank.update(0.1f);
        
        assertTrue(tank.isMoving());
        assertEquals(0.2f, tank.getMovementProgress(), 0.01f);
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
    }
}