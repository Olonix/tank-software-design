package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.GameEntity;
import ru.mipt.bit.platformer.util.TileMovement;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;


public class TankGraphics implements Renderable {
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;
    private final TileMovement tileMovement;

    public TankGraphics(String texturePath, TileMovement tileMovement) {
        this.texture = new Texture(texturePath);
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.tileMovement = tileMovement;
    }

    @Override
    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, textureRegion, rectangle, 0f);
    }

    public void render(Batch batch, GameEntity entity, GridPoint2 destination, float progress) {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, entity.getCoordinates(), destination, progress);
        
        drawTextureRegionUnscaled(batch, textureRegion, rectangle, entity.getRotation());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}