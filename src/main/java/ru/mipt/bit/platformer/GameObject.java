package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public abstract class GameObject {
    protected Texture texture;
    protected TextureRegion textureRegion;
    protected Rectangle rectangle;
    protected GridPoint2 coordinates;
    protected float rotation;

    protected GameObject(String texturePath, GridPoint2 initialCoordinates) {
        this.texture = new Texture(texturePath);
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.coordinates = new GridPoint2(initialCoordinates);
        this.rotation = 0f;
    }

    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, textureRegion, rectangle, rotation);
    }

    public void dispose() {
        texture.dispose();
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}