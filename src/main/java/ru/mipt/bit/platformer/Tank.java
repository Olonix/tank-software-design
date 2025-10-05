package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.graphics.TankGraphics;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.util.TileMovement;


public class Tank {
    private final TankModel model;
    private final TankGraphics graphics;

    public Tank(String texturePath, GridPoint2 initialCoordinates, TileMovement tileMovement) {
        this.model = new TankModel(initialCoordinates);
        this.graphics = new TankGraphics(texturePath, tileMovement);
    }

    public void update(float deltaTime) {
        model.update(deltaTime);
    }

    public boolean tryMove(Direction direction, Obstacle obstacle) {
        return model.tryMove(direction, obstacle.getModel());
    }

    public boolean isMoving() {
        return model.isMoving();
    }

    public void render(Batch batch) {
        graphics.render(batch, model, model.getDestinationCoordinates(), model.getMovementProgress());
    }

    public void dispose() {
        graphics.dispose();
    }

    public GridPoint2 getCoordinates() {
        return model.getCoordinates();
    }

    public TankModel getModel() {
        return model;
    }
}