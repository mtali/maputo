package com.colisa.maputo;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.StringBuilder;
@SuppressWarnings("WeakerAccess")
public class WorldRenderer implements Disposable {
    private static final String TAG = WorldRenderer.class.getName();
    private WorldController controller;
    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private SpriteBatch batch;

    public WorldRenderer(com.colisa.maputo.WorldController controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        batch = new SpriteBatch();

        // set world camera
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
        controller.setCamera(camera);

        // set gui camera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT * w/h);
        cameraGUI.setToOrtho(true);
        cameraGUI.update();

    }

    public void render() {
        worldRender(batch);
        renderGUI(batch);
    }

    private void renderGUI(SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        if (Constants.DEBUG_FLAG_GAME_SCREEN)
            renderExtraLivesGUI(batch);
            renderFPS(batch);
        batch.end();
    }

    private void renderFPS(Batch batch) {
        float x = cameraGUI.viewportWidth - 70;
        float y = cameraGUI.viewportHeight - 20;
        float fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.assetFonts.defaultSmall;
        if (fps >= 45) {
            fpsFont.setColor(Color.GREEN);
        } else if (fps >= 30) {
            fpsFont.setColor(Color.YELLOW);
        } else {
            fpsFont.setColor(Color.RED);
        }
        fpsFont.draw(batch, "FPS: " + fps, x, y);
        fpsFont.setColor(1, 1, 1, 1);
    }

    private void renderExtraLivesGUI(Batch batch) {

        float x = cameraGUI.viewportWidth - Constants.INITIAL_LIVES * 60;
        float y = -5;
        for (int i = 0; i < Constants.INITIAL_LIVES; i++) {
            if (controller.getLives() <= i) batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
            batch.draw(
                    Assets.instance.assetBalloon.balloon,
                    x + i * 50,
                    y,
                    50,
                    50,
                    100,
                    150,
                    0.35f,
                    -0.35f,
                    0
            );
            batch.setColor(1, 1, 1, 1);
        }
    }

    private void worldRender(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        controller.level.render(batch);
        batch.end();
    }

    public void resize(int width, int height) {
        float ratio = (float) width / (float) height;

        camera.viewportWidth = Constants.VIEWPORT_HEIGHT * ratio;
        camera.update();

        cameraGUI.viewportWidth = Constants.VIEWPORT_GUI_WIDTH;
        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_WIDTH * height/width;
        cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
        cameraGUI.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
