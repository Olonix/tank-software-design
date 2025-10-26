package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LevelData {
    private final GridPoint2 playerStartPosition;
    private final List<GridPoint2> obstaclePositions;
    
    public LevelData(GridPoint2 playerStartPosition, List<GridPoint2> obstaclePositions) {
        this.playerStartPosition = new GridPoint2(playerStartPosition);
        this.obstaclePositions = Collections.unmodifiableList(new ArrayList<>(obstaclePositions));
    }
    
    public GridPoint2 getPlayerStartPosition() {
        return new GridPoint2(playerStartPosition);
    }
    
    public List<GridPoint2> getObstaclePositions() {
        return obstaclePositions;
    }
}
