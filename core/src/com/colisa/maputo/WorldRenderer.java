package com.colisa.maputo;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

@SuppressWarnings("WeakerAccess")
public class WorldRenderer implements Disposable {
    private static final String TAG = WorldRenderer.class.getName();
    private WorldController controller;
    private OrthographicCamera camera;
    //    private OrthographicCamera cameraGUI;
    private Stage stage;
    private SpriteBatch batch;
    private HUD displayHUD;


    public WorldRenderer(WorldController controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
        init();
    }

    private void init() {
        batch = new SpriteBatch();

        // set world camera
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
        controller.setCamera(camera);

        if (displayHUD == null) displayHUD = new HUD();

    }

    public void render() {
        worldRender(batch);
        displayHUD.render(batch, controller.getScore(), controller.getLives());
    }


    private void worldRender(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        controller.balloonController.render(batch);
        batch.end();
    }

    public void resize(int width, int height) {
        float ratio = (float) width / (float) height;

        camera.viewportWidth = Constants.VIEWPORT_HEIGHT * ratio;
        camera.update();

        displayHUD.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }


    /**
     * This class is responsible to render score lives and fps overlays
     * specifically for Game Screen
     */
    private final class HUD {
        private OrthographicCamera camera;
        private int width = 300;
        private BitmapFont font;
        private BitmapFont fontBig;
        private TextureRegion balloonRegion;


        public HUD() {
            float w = Gdx.graphics.getWidth();
            float h = Gdx.graphics.getHeight();
            camera = new OrthographicCamera(width, width * h / w);
            camera.update();

            font = Assets.instance.assetFonts.defaultSmall;
            fontBig = Assets.instance.assetFonts.defaultSmall;
            balloonRegion = Assets.instance.assetBalloon.balloon;
        }

        public void render(SpriteBatch batch, int score, int lives) {
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            renderFPS(batch);
            renderScore(batch, score);
            renderExtraLives(batch, lives);
            batch.end();
        }

        private void renderExtraLives(SpriteBatch batch, int lives) {

            float x = camera.viewportWidth - Constants.INITIAL_LIVES * 51f;
            float y = camera.viewportHeight - 95 ;
            for (int i = 0; i < Constants.INITIAL_LIVES; i++) {
                if (lives <= i) batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
                batch.draw(
                        Assets.instance.assetBalloon.balloon,
                        x + i * 40,
                        y,
                        50,
                        50,
                        100,
                        150,
                        0.3f,
                        0.3f,
                        0
                );
                batch.setColor(1, 1, 1, 1);
            }


        }

        private void renderScore(SpriteBatch batch, int score) {
            font.getData().setScale(0.3f);
            float x = -25;
            float y = camera.viewportHeight - 95;
            batch.setColor(Color.GOLD);
            batch.draw(
                    Assets.instance.assetBalloon.balloon,
                    x,
                    y,
                    50,
                    50,
                    100,
                    150,
                    0.3f,
                    0.3f,
                    0
            );
            batch.setColor(1, 1, 1, 1);
            font.draw(batch, String.valueOf(score), x + 70f, y + 70f);
        }

        private void renderFPS(SpriteBatch batch) {
            float fps = Gdx.graphics.getFramesPerSecond();
            font.getData().setScale(0.15f);
            float x = camera.viewportWidth - 40;
            float y = 13;
            if (fps >= 45) {
                font.setColor(Color.GREEN);
            } else if (fps >= 30) {
                font.setColor(Color.YELLOW);
            } else {
                font.setColor(Color.RED);
            }
            font.draw(batch, "FPS: " + fps, x, y);
            font.setColor(1, 1, 1, 1);
        }

        public void update(int width, int height) {
            camera.viewportWidth = this.width;
            camera.viewportHeight = this.width * height / width;
            camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
            camera.update();
        }
    }
}
