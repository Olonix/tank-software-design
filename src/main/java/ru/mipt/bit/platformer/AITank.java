package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.command.MoveCommand;
import ru.mipt.bit.platformer.graphics.TankGraphics;
import ru.mipt.bit.platformer.graphics.TankGraphicsRenderer;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.model.TankMovable;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;
import java.util.Random;

public class AITank {
    private final TankMovable model;
    private final TankGraphicsRenderer graphics;
    private final Random random = new Random();
    private Command currentCommand = null;

    public AITank(TankMovable model, TankGraphicsRenderer graphics) {
        this.model = model;
        this.graphics = graphics;
    }

    public AITank(String texturePath, GridPoint2 initialCoordinates, TileMovement tileMovement) {
        this.model = new TankModel(initialCoordinates);
        this.graphics = new TankGraphics(texturePath, tileMovement);
    }

    public void update(float deltaTime, List<Obstacle> obstacles, List<AITank> otherTanks, TankMovable playerTank, int levelWidth, int levelHeight) {
        model.update(deltaTime);

        if (currentCommand == null || currentCommand.isFinished()) {
            generateRandomMove(obstacles, otherTanks, playerTank, levelWidth, levelHeight);
        }

        if (currentCommand != null && !currentCommand.isFinished()) {
            currentCommand.execute();
        }
    }

    private void generateRandomMove(List<Obstacle> obstacles, List<AITank> otherTanks, TankMovable playerTank, int levelWidth, int levelHeight) {
        Direction[] directions = Direction.values();
        Direction randomDirection = directions[random.nextInt(directions.length)];

        List<TankMovable> allOtherTanks = new java.util.ArrayList<>();

        for (AITank tank : otherTanks) {
            if (tank != this) {
                allOtherTanks.add(tank.getModel());
            }
        }
        allOtherTanks.add(playerTank);

        currentCommand = new MoveCommand(model, randomDirection, obstacles, allOtherTanks, levelWidth, levelHeight);
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

    public GridPoint2 getDestinationCoordinates() {
        return model.getDestinationCoordinates();
    }

    public TankMovable getModel() {
        return model;
    }

    public boolean isMoving() {
        return model.isMoving();
    }
}
