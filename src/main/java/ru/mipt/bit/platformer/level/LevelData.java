package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LevelData {
    private final GridPoint2 playerStartPosition;
    private final List<GridPoint2> obstaclePositions;
    private final List<GridPoint2> aiTankPositions;

    public LevelData(GridPoint2 playerStartPosition, List<GridPoint2> obstaclePositions) {
        this(playerStartPosition, obstaclePositions, new ArrayList<>());
    }

    public LevelData(GridPoint2 playerStartPosition, List<GridPoint2> obstaclePositions, List<GridPoint2> aiTankPositions) {
        this.playerStartPosition = new GridPoint2(playerStartPosition);
        this.obstaclePositions = Collections.unmodifiableList(new ArrayList<>(obstaclePositions));
        this.aiTankPositions = Collections.unmodifiableList(new ArrayList<>(aiTankPositions));
    }
    
    public GridPoint2 getPlayerStartPosition() {
        return new GridPoint2(playerStartPosition);
    }
    
    public List<GridPoint2> getObstaclePositions() {
        return obstaclePositions;
    }

    public List<GridPoint2> getAITankPositions() {
        return aiTankPositions;
    }
}
