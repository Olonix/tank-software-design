package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleModelTest {

    @Test
    void testObstacleCreation() {
        GridPoint2 position = new GridPoint2(3, 4);
        ObstacleModel obstacle = new ObstacleModel(position);

        assertEquals(new GridPoint2(3, 4), obstacle.getCoordinates());
        assertEquals(0f, obstacle.getRotation());
    }

    @Test
    void testObstaclePositionImmutability() {
        GridPoint2 originalPosition = new GridPoint2(5, 6);
        ObstacleModel obstacle = new ObstacleModel(originalPosition);

        originalPosition.x = 100;
        originalPosition.y = 200;

        assertEquals(new GridPoint2(5, 6), obstacle.getCoordinates());
    }

    @Test
    void testGetCoordinatesReturnsNewInstance() {
        ObstacleModel obstacle = new ObstacleModel(new GridPoint2(7, 8));
        
        GridPoint2 coords1 = obstacle.getCoordinates();
        GridPoint2 coords2 = obstacle.getCoordinates();

        assertNotSame(coords1, coords2);
        assertEquals(coords1, coords2);

        coords1.x = 999;
        assertEquals(new GridPoint2(7, 8), obstacle.getCoordinates());
    }
}