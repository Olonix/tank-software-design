package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys.*;


public class InputHandler {
    
    public Direction getInputDirection() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return Direction.UP;
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return Direction.LEFT;
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return Direction.DOWN;
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return Direction.RIGHT;
        }
        return null;
    }

    public boolean isToggleHealthPressed() {
        return Gdx.input.isKeyJustPressed(L);
    }

    // public boolean isShootPressed() {
    //     return Gdx.input.isKeyPressed(SPACE);
    // }
}