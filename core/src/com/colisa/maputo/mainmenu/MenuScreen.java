package com.colisa.maputo.mainmenu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.colisa.maputo.AbstractScreen;
import com.colisa.maputo.DirectedGame;

public class MenuScreen extends AbstractScreen {
    private static final String TAG = MenuScreen.class.getName();
    private Skin skinMaputo;
    private Skin skinLibgdx;
    private Stage stage;

    public MenuScreen(DirectedGame game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // rebuilding the stage including individual layers
        skinMaputo = new Skin();
        skinLibgdx = new Skin();
        Stack stack = new Stack();
        stage.clear();
        stage.addActor(stack);

        // + background
        Table backgroundLayer = new Table();
        // todo: add a background image
        stack.add(backgroundLayer);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void hide() {
        stage.dispose();
        skinLibgdx.dispose();
        skinMaputo.dispose();
        Gdx.app.debug(TAG, "disposing menu screen");
    }

    @Override
    public void pause() {

    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }
}
