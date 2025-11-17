package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.model.BulletModel;

public class BulletGraphics implements Graphics {
    private final Texture bulletTexture;
    private final TextureRegion bulletRegion;
    private final TiledMapTileLayer tileLayer;
    private final float bulletSize = 8f;

    public BulletGraphics(TiledMapTileLayer tileLayer) {
        this.tileLayer = tileLayer;

        Pixmap pixmap = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0, 0, 8, 8);
        bulletTexture = new Texture(pixmap);
        bulletRegion = new TextureRegion(bulletTexture);
        pixmap.dispose();
    }

    @Override
    public void render(Batch batch) {
        // This method is not used for bullets as they need model-specific rendering
    }

    public void render(Batch batch, BulletModel bullet) {
        if (!bullet.isDestroyed()) {
            float x = bullet.getPreciseCoordinates().x * tileLayer.getTileWidth() + tileLayer.getTileWidth() / 2f;
            float y = bullet.getPreciseCoordinates().y * tileLayer.getTileHeight() + tileLayer.getTileHeight() / 2f;

            batch.draw(
                bulletRegion,
                x - bulletSize / 2f,
                y - bulletSize / 2f,
                bulletSize / 2f,
                bulletSize / 2f,
                bulletSize,
                bulletSize,
                1f,
                1f,
                bullet.getRotation()
            );
        }
    }

    @Override
    public void dispose() {
        bulletTexture.dispose();
    }
}
