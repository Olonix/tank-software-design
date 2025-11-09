package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.GameModel;
import ru.mipt.bit.platformer.model.TankMovable;

// Decorator for adding health bar rendering to tank graphics
public class HealthBarDecorator implements TankGraphicsRenderer {
    private final TankGraphicsRenderer wrappedRenderer;
    private final ShapeRenderer shapeRenderer;
    private boolean showHealthBar;

    public HealthBarDecorator(TankGraphicsRenderer wrappedRenderer) {
        this.wrappedRenderer = wrappedRenderer;
        this.shapeRenderer = new ShapeRenderer();
        this.showHealthBar = false;
    }

    public void setShowHealthBar(boolean showHealthBar) {
        this.showHealthBar = showHealthBar;
    }

    @Override
    public void render(Batch batch) {
        wrappedRenderer.render(batch);
        if (showHealthBar) {
            // not used but keeping for interface compliance
        }
    }

    @Override
    public void render(Batch batch, GameModel entity, GridPoint2 destination, float progress) {
        wrappedRenderer.render(batch, entity, destination, progress);

        if (showHealthBar && entity instanceof TankMovable) {
            TankMovable tank = (TankMovable) entity;
            if (tank instanceof ru.mipt.bit.platformer.model.TankModel) {
                ru.mipt.bit.platformer.model.TankModel tankModel = (ru.mipt.bit.platformer.model.TankModel) tank;
                drawHealthBar(batch, tankModel);
            }
        }
    }

    private void drawHealthBar(Batch batch, ru.mipt.bit.platformer.model.TankModel tank) {
        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Rectangle tankRect = ((TankGraphics) wrappedRenderer).getRectangle();

        float barWidth = tankRect.width;
        float barHeight = 5f;
        float barX = tankRect.x;
        float barY = tankRect.y + tankRect.height + 2f;

        // Background (red)
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);

        // Health (green)
        float healthRatio = (float) tank.getHealth() / tank.getMaxHealth();
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(barX, barY, barWidth * healthRatio, barHeight);

        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public void dispose() {
        wrappedRenderer.dispose();
        shapeRenderer.dispose();
    }

    public Rectangle getRectangle() {
        return ((TankGraphics) wrappedRenderer).getRectangle();
    }
}
