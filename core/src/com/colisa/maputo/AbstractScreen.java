package com.colisa.maputo;


import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

/** This class is a base of all classes, its purpose is to force all screen resume to reload assets
 *  and screen dispose to dispose game assets */

public abstract class AbstractScreen implements Screen {
    private static final String TAG = AbstractScreen.class.getName();
    protected DirectedGame game;

    public AbstractScreen(DirectedGame game) {
        this.game = game;
    }

    public abstract void render(float deltaTime);
    public abstract void resize(int width, int height);
    public abstract void show();
    public abstract void hide();
    public abstract void pause();

    @Override
    public void dispose() {
        // TODO: Dispose assets here
    }

    @Override
    public void resume() {
        // TODO: Reload assets here
    }

    public abstract InputProcessor getInputProcessor();
}
