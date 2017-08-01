package com.colisa.maputo;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen extends AbstractScreen {
    private static final String TAG = GameScreen.class.getName();
    private boolean paused;
    private WorldController worldController;
    private WorldRenderer worldRenderer;

    public GameScreen(DirectedGame game){
        super(game);
    }

    @Override
    public void show() {
        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer(worldController);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldRenderer.render();
        if (!paused) {
            worldController.update(deltaTime);
        }
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void hide() {
        worldRenderer.dispose();
        worldController.dispose();
        Gdx.input.setCatchBackKey(false);
        Gdx.app.debug(TAG, "dispose world controller and renderer");
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        super.resume();
        paused = false;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return worldController;
    }
}
