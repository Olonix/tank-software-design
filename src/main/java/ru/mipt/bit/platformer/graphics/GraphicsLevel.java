package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Tank;
import ru.mipt.bit.platformer.AITank;
import ru.mipt.bit.platformer.level.LogicalLevel;
import ru.mipt.bit.platformer.model.BulletModel;
import ru.mipt.bit.platformer.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class GraphicsLevel implements Observer {
    private final LogicalLevel logicalLevel;
    private final TiledMapTileLayer tileLayer;
    private final Batch batch;

    private final List<Tank> tanks = new ArrayList<>();
    private final List<AITank> aiTanks = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();
    private final List<BulletGraphics> bulletGraphics = new ArrayList<>();
    private final List<BulletModel> bullets = new ArrayList<>();

    public GraphicsLevel(LogicalLevel logicalLevel, TiledMapTileLayer tileLayer, Batch batch) {
        this.logicalLevel = logicalLevel;
        this.tileLayer = tileLayer;
        this.batch = batch;
        logicalLevel.addObserver(this);

        update();
    }

    @Override
    public void update() {
        tanks.clear();
        tanks.addAll(logicalLevel.getTanks());

        aiTanks.clear();
        aiTanks.addAll(logicalLevel.getAITanks());

        obstacles.clear();
        obstacles.addAll(logicalLevel.getObstacles());

        List<BulletModel> currentBullets = logicalLevel.getBullets();
        bulletGraphics.clear();
        bullets.clear();

        for (BulletModel bullet : currentBullets) {
            bullets.add(bullet);
            BulletGraphics graphics = new BulletGraphics(tileLayer);
            bulletGraphics.add(graphics);
        }
    }

    public void render() {
        for (Obstacle obstacle : obstacles) {
            obstacle.render(batch);
        }

        for (Tank tank : tanks) {
            tank.render(batch);
        }

        for (AITank aiTank : aiTanks) {
            aiTank.render(batch);
        }

        for (int i = 0; i < bullets.size(); i++) {
            BulletModel bullet = bullets.get(i);
            BulletGraphics graphics = bulletGraphics.get(i);
            graphics.render(batch, bullet);
        }
    }

    public void dispose() {
        for (BulletGraphics graphics : bulletGraphics) {
            graphics.dispose();
        }
    }
}
