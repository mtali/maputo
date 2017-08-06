package com.colisa.maputo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.colisa.maputo.Constants;
import com.colisa.maputo.DirectedGame;

@SuppressWarnings("WeakerAccess")
public abstract class BasicScreen implements Screen {

    private static final float VIEWPORT_WIDTH = 20;
    private static final float VIEWPORT_HEIGHT = VIEWPORT_WIDTH * 2;

    private static final String TAG = "BasicScreen";

    /* Weather static members have been initialized or not */
    private static boolean initialized = false;

    private static float widthScalingFactor;
    private static float heightScalingFactor;
    private static float scalingFactor;
    private static float xScalingFactor;
    private static float yScalingFactor;
    private static float deltaX;
    private static float deltaY;

    private static Sprite background;

    protected static SpriteBatch batch;
    protected static Viewport viewport;

    protected final Stage stage;

    protected InputMultiplexer inputProcessor;

    protected DirectedGame game;

    public BasicScreen(DirectedGame game) {
        this.game = game;

        stage = new Stage();
        if (!initialized) {
            initialize();
            initialized = true;
        }
        stage.setViewport(viewport);
        inputProcessor = new InputMultiplexer(stage);
        generateContent();
    }

    private void initialize() {
        batch = new SpriteBatch();
        background = new Sprite(new Texture(Gdx.files.internal("background.png")));
        widthScalingFactor = UI.Window.REFERENCE_WIDTH / (float) Gdx.graphics.getWidth();
        heightScalingFactor = UI.Window.REFERENCE_HEIGHT / (float) Gdx.graphics.getHeight();
        scalingFactor = Math.max(widthScalingFactor, heightScalingFactor);
        updateBackground();

        viewport = new ExtendViewport(Gdx.graphics.getWidth() * scalingFactor, Gdx.graphics.getHeight() * scalingFactor, stage.getCamera());
    }

    private void updateBackground() {
        xScalingFactor = Gdx.graphics.getWidth() / background.getWidth();
        yScalingFactor = Gdx.graphics.getHeight() / background.getHeight();

        deltaX = 0;
        deltaY = 0;
        background.setOrigin(0, 0);
        if (xScalingFactor >= yScalingFactor) {
            background.setScale(xScalingFactor);
            deltaY = (Gdx.graphics.getHeight() - background.getHeight() * xScalingFactor);
        } else {
            background.setScale(yScalingFactor);
            deltaX = (Gdx.graphics.getWidth() - background.getWidth() * yScalingFactor);
        }

        background.setPosition(deltaX, deltaY);
    }

    /**
     * Override this if you want to create your own content
     */
    protected void generateContent() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        batch.end();

        moreRender(delta);

        stage.act(delta);
        stage.draw();
    }

    public void moreRender(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        stage.addAction(Actions.sequence(
                Actions.alpha(0), Actions.fadeIn(UI.Transition.FADE_IN_TIME)
        ));
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }


    @Override
    public void dispose() {
        Gdx.app.debug("BasicScreen", ".dispose() - dispose assets");
        background.getTexture().dispose();
        batch.dispose();
        stage.dispose();
        initialized = false;
    }

    public abstract InputProcessor getInputProcessor();

}
