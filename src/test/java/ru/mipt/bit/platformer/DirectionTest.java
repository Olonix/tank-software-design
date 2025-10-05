package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testDirectionRotations() {
        assertEquals(90f, Direction.UP.getRotation());
        assertEquals(-90f, Direction.DOWN.getRotation());
        assertEquals(-180f, Direction.LEFT.getRotation());
        assertEquals(0f, Direction.RIGHT.getRotation());
    }

    @Test
    void testDirectionVectors() {
        assertEquals(new Vector2(0, 1), Direction.UP.getDirectionVector());
        assertEquals(new Vector2(0, -1), Direction.DOWN.getDirectionVector());
        assertEquals(new Vector2(-1, 0), Direction.LEFT.getDirectionVector());
        assertEquals(new Vector2(1, 0), Direction.RIGHT.getDirectionVector());
    }

    @Test
    void testDirectionApply() {
        GridPoint2 point = new GridPoint2(5, 5);

        assertEquals(new GridPoint2(5, 6), Direction.UP.apply(point));
        assertEquals(new GridPoint2(5, 4), Direction.DOWN.apply(point));
        assertEquals(new GridPoint2(4, 5), Direction.LEFT.apply(point));
        assertEquals(new GridPoint2(6, 5), Direction.RIGHT.apply(point));
    }

    @Test
    void testOriginalPointNotModified() {
        GridPoint2 originalPoint = new GridPoint2(3, 3);
        GridPoint2 result = Direction.UP.apply(originalPoint);

        assertEquals(new GridPoint2(3, 3), originalPoint);
        assertEquals(new GridPoint2(3, 4), result);
        assertNotSame(originalPoint, result);
    }

    @Test
    void testDirectionVectorImmutability() {
        Vector2 vector1 = Direction.UP.getDirectionVector();
        Vector2 vector2 = Direction.UP.getDirectionVector();

        assertNotSame(vector1, vector2);
        assertEquals(vector1, vector2);

        vector1.x = 100;
        assertEquals(new Vector2(0, 1), Direction.UP.getDirectionVector());
    }
}