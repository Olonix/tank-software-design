package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelDataTest {

    @Test
    void testLevelDataWithAITanks() {
        GridPoint2 playerPos = new GridPoint2(0, 0);
        List<GridPoint2> obstacles = Arrays.asList(new GridPoint2(1, 0), new GridPoint2(2, 0));
        List<GridPoint2> aiTanks = Arrays.asList(new GridPoint2(3, 0), new GridPoint2(4, 0));

        LevelData levelData = new LevelData(playerPos, obstacles, aiTanks);

        assertEquals(playerPos, levelData.getPlayerStartPosition());
        assertEquals(obstacles, levelData.getObstaclePositions());
        assertEquals(aiTanks, levelData.getAITankPositions());
    }

    @Test
    void testLevelDataWithoutAITanks() {
        GridPoint2 playerPos = new GridPoint2(0, 0);
        List<GridPoint2> obstacles = Arrays.asList(new GridPoint2(1, 0));

        LevelData levelData = new LevelData(playerPos, obstacles);

        assertEquals(playerPos, levelData.getPlayerStartPosition());
        assertEquals(obstacles, levelData.getObstaclePositions());
        assertTrue(levelData.getAITankPositions().isEmpty());
    }
}
