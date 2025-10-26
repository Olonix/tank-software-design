package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.graphics.TankGraphics;
import ru.mipt.bit.platformer.graphics.TankGraphicsRenderer;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.model.TankMovable;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;


public class Tank {
    private final TankMovable model;
    private final TankGraphicsRenderer graphics;

    public Tank(TankMovable model, TankGraphicsRenderer graphics) {
        this.model = model;
        this.graphics = graphics;
    }

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

    public boolean canMove(Direction direction, Obstacle obstacle) {
        if (model.isMoving()) {
            return false;
        }

        GridPoint2 newDestination = direction.apply(getCoordinates());
        return !obstacle.getCoordinates().equals(newDestination);
    }

    public boolean canMove(Direction direction, List<Obstacle> obstacles) {
        if (model.isMoving()) {
            return false;
        }

        GridPoint2 newDestination = direction.apply(getCoordinates());
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(newDestination)) {
                return false;
            }
        }
        return true;
    }

    public boolean move(Direction direction) {
        if (model instanceof TankModel) {
            return ((TankModel) model).move(direction);
        }
        return false;
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

    public TankMovable getModel() {
        return model;
    }
}
