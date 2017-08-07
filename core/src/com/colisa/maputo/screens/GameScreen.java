package com.colisa.maputo.screens;


import com.badlogic.gdx.InputProcessor;
import com.colisa.maputo.DirectedGame;
import com.colisa.maputo.WorldController;
import com.colisa.maputo.WorldRenderer;

public class GameScreen extends BasicScreen {

    private static final String TAG = "GameScreen";

    private WorldController controller;
    private WorldRenderer renderer;
    private boolean paused;

    public GameScreen(DirectedGame game) {
        super(game);
    }

    @Override
    protected void generateContent() {
        super.generateContent();
        if (controller == null) controller = new WorldController(game);
        if (renderer == null) renderer = new WorldRenderer(controller, stage);
    }

    @Override
    public void moreRender(float delta) {
        super.moreRender(delta);
        renderer.render();
        if (!paused) {
            controller.update(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        renderer.resize(width, height);
    }

    @Override
    public void hide() {
        paused = true;
        renderer.dispose();
        controller.dispose();
        super.hide();
    }

    @Override
    public void pause() {
        paused = true;
        super.pause();
    }

    @Override
    public void resume() {
        paused = false;
        super.resume();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return controller;
    }
}
