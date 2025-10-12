package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.GameModel;

// Interface for tank-specific graphics rendering
public interface TankGraphicsRenderer extends Graphics {
    void render(Batch batch, GameModel entity, GridPoint2 destination, float progress);
}