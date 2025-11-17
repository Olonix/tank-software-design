package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Tank;
import ru.mipt.bit.platformer.AITank;
import ru.mipt.bit.platformer.model.BulletModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.observer.Observable;
import ru.mipt.bit.platformer.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class LogicalLevel implements Observable {
    private final List<Observer> observers = new ArrayList<>();
    private final List<Tank> tanks = new ArrayList<>();
    private final List<AITank> aiTanks = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();
    private final List<BulletModel> bullets = new ArrayList<>();


    public void addTank(Tank tank) {
        tanks.add(tank);
        notifyObservers();
    }

    public void addAITank(AITank aiTank) {
        aiTanks.add(aiTank);
        notifyObservers();
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
        notifyObservers();
    }

    public void addBullet(BulletModel bullet) {
        bullets.add(bullet);
        notifyObservers();
    }


    public void removeTank(Tank tank) {
        tanks.remove(tank);
        notifyObservers();
    }

    public void removeAITank(AITank aiTank) {
        aiTanks.remove(aiTank);
        notifyObservers();
    }

    public void removeBullet(BulletModel bullet) {
        bullets.remove(bullet);
        notifyObservers();
    }


    public List<Tank> getTanks() {
        return new ArrayList<>(tanks);
    }

    public List<AITank> getAITanks() {
        return new ArrayList<>(aiTanks);
    }

    public List<Obstacle> getObstacles() {
        return new ArrayList<>(obstacles);
    }

    public List<BulletModel> getBullets() {
        return new ArrayList<>(bullets);
    }


    public void update(float deltaTime, int levelWidth, int levelHeight) {
        for (Tank tank : tanks) {
            tank.update(deltaTime);
        }

        List<BulletModel> newBullets = new ArrayList<>();
        for (AITank aiTank : aiTanks) {
            BulletModel bullet = aiTank.update(deltaTime, obstacles, aiTanks, tanks.isEmpty() ? null : tanks.get(0).getModel(), levelWidth, levelHeight);
            if (bullet != null) {
                newBullets.add(bullet);
            }
        }

        for (BulletModel bullet : newBullets) {
            addBullet(bullet);
        }

        List<BulletModel> bulletsToRemove = new ArrayList<>();
        for (BulletModel bullet : bullets) {
            bullet.update(deltaTime);

            boolean shouldRemove = checkBulletCollisions(bullet);

            GridPoint2 pos = bullet.getCoordinates();
            if (pos.x < 0 || pos.x >= levelWidth || pos.y < 0 || pos.y >= levelHeight) {
                shouldRemove = true;
            }

            if (shouldRemove || bullet.isDestroyed()) {
                bulletsToRemove.add(bullet);
            }
        }

        for (BulletModel bullet : bulletsToRemove) {
            removeBullet(bullet);
        }

        List<Tank> tanksToRemove = new ArrayList<>();
        for (Tank tank : tanks) {
            if (((TankModel) tank.getModel()).isDestroyed()) {
                tanksToRemove.add(tank);
            }
        }
        for (Tank tank : tanksToRemove) {
            removeTank(tank);
        }

        List<AITank> aiTanksToRemove = new ArrayList<>();
        for (AITank aiTank : aiTanks) {
            if (((TankModel) aiTank.getModel()).isDestroyed()) {
                aiTanksToRemove.add(aiTank);
            }
        }
        for (AITank aiTank : aiTanksToRemove) {
            removeAITank(aiTank);
        }
    }

    private boolean checkBulletCollisions(BulletModel bullet) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(bullet.getCoordinates())) {
                return true;
            }
        }

        for (Tank tank : tanks) {
            if (tank.getCoordinates().equals(bullet.getCoordinates())) {
                ((TankModel) tank.getModel()).takeDamage(bullet.getDamage());
                return true;
            }
        }

        for (AITank aiTank : aiTanks) {
            if (aiTank.getCoordinates().equals(bullet.getCoordinates())) {
                ((TankModel) aiTank.getModel()).takeDamage(bullet.getDamage());
                return true;
            }
        }

        for (BulletModel otherBullet : bullets) {
            if (otherBullet != bullet && otherBullet.getCoordinates().equals(bullet.getCoordinates())) {
                otherBullet.destroy();
                return true;
            }
        }

        return false;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
