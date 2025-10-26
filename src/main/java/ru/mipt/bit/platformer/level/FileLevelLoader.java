package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads level from a text file
 * 'T' - tree obstacle
 * 'X' - player start position
 * '_' - empty space
 */
public class FileLevelLoader implements LevelLoader {
    private final String filePath;
    
    public FileLevelLoader(String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public LevelData loadLevel() {
        FileHandle file = Gdx.files.internal(filePath);
        
        if (!file.exists()) {
            throw new RuntimeException("Level file not found: " + filePath);
        }
        
        String content = file.readString();
        String[] lines = content.split("\\r?\\n");
        
        List<GridPoint2> obstaclePositions = new ArrayList<>();
        GridPoint2 playerPosition = null;
        
        for (int y = 0; y < lines.length; y++) {
            String line = lines[y];
            // Flip Y coordinate (top line in file = highest Y in game)
            int gameY = lines.length - 1 - y;
            
            for (int x = 0; x < line.length(); x++) {
                char cell = line.charAt(x);
                
                switch (cell) {
                    case 'T':
                        obstaclePositions.add(new GridPoint2(x, gameY));
                        break;
                    case 'X':
                        if (playerPosition != null) {
                            throw new RuntimeException("Multiple player positions found in level file");
                        }
                        playerPosition = new GridPoint2(x, gameY);
                        break;
                    case '_':
                        break;
                    default:
                        throw new RuntimeException("Unknown character in level file: " + cell);
                }
            }
        }
        
        if (playerPosition == null) {
            throw new RuntimeException("No player start position (X) found in level file");
        }
        
        return new LevelData(playerPosition, obstaclePositions);
    }
}
