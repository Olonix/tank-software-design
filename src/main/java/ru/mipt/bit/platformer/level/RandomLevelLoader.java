package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RandomLevelLoader implements LevelLoader {
    private final int levelWidth;
    private final int levelHeight;
    private final int obstacleCount;
    private final int aiTankCount;
    private final Random random;

    public RandomLevelLoader(int levelWidth, int levelHeight, int obstacleCount) {
        this(levelWidth, levelHeight, obstacleCount, 3);
    }

    public RandomLevelLoader(int levelWidth, int levelHeight, int obstacleCount, int aiTankCount) {
        this(levelWidth, levelHeight, obstacleCount, aiTankCount, new Random());
    }

    // public RandomLevelLoader(int levelWidth, int levelHeight, int obstacleCount, long seed) {
    //     this(levelWidth, levelHeight, obstacleCount, new Random(seed));
    // }

    private RandomLevelLoader(int levelWidth, int levelHeight, int obstacleCount, int aiTankCount, Random random) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.obstacleCount = obstacleCount;
        this.aiTankCount = aiTankCount;
        this.random = random;
    }
    
    @Override
    public LevelData loadLevel() {
        List<GridPoint2> obstaclePositions = new ArrayList<>();
        List<GridPoint2> occupiedPositions = new ArrayList<>();

        for (int i = 0; i < obstacleCount; i++) {
            GridPoint2 position;
            int attempts = 0;
            int maxAttempts = 100;
            
            // Try to find a unique position
            do {
                position = new GridPoint2(
                    random.nextInt(levelWidth),
                    random.nextInt(levelHeight)
                );
                attempts++;
            } while (containsPosition(occupiedPositions, position) && attempts < maxAttempts);

            if (!containsPosition(occupiedPositions, position)) {
                obstaclePositions.add(position);
                occupiedPositions.add(position);
            }
        }
        
        GridPoint2 playerPosition;
        int attempts = 0;
        int maxAttempts = 100;
        
        do {
            playerPosition = new GridPoint2(
                random.nextInt(levelWidth),
                random.nextInt(levelHeight)
            );
            attempts++;
        } while (containsPosition(occupiedPositions, playerPosition) && attempts < maxAttempts);

        occupiedPositions.add(playerPosition);

        List<GridPoint2> aiTankPositions = new ArrayList<>();
        for (int i = 0; i < aiTankCount; i++) {
            GridPoint2 position;
            attempts = 0;

            do {
                position = new GridPoint2(
                    random.nextInt(levelWidth),
                    random.nextInt(levelHeight)
                );
                attempts++;
            } while (containsPosition(occupiedPositions, position) && attempts < maxAttempts);

            if (!containsPosition(occupiedPositions, position)) {
                aiTankPositions.add(position);
                occupiedPositions.add(position);
            }
        }

        return new LevelData(playerPosition, obstaclePositions, aiTankPositions);
    }
    
    private boolean containsPosition(List<GridPoint2> positions, GridPoint2 position) {
        for (GridPoint2 p : positions) {
            if (p.x == position.x && p.y == position.y) {
                return true;
            }
        }
        return false;
    }
}
