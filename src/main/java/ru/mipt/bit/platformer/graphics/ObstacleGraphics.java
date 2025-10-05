package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.GameEntity;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;


public class ObstacleGraphics implements Renderable {
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;

    public ObstacleGraphics(String texturePath, GameEntity entity, TiledMapTileLayer tileLayer) {
        this.texture = new Texture(texturePath);
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        moveRectangleAtTileCenter(tileLayer, rectangle, entity.getCoordinates());
    }

    @Override
    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, textureRegion, rectangle, 0f);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}