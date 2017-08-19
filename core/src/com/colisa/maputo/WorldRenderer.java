package com.colisa.maputo;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

@SuppressWarnings("WeakerAccess")
public class WorldRenderer implements Disposable {
    private static final String TAG = WorldRenderer.class.getName();
    private WorldController controller;
    private OrthographicCamera camera;

    private Stage stage;
    private SpriteBatch batch;

    private UpdatesHUD updatesHUD;


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

        if (updatesHUD == null) updatesHUD = new UpdatesHUD();

    }

    public void render() {
        worldRender(batch);
        updatesHUD.render(batch, controller.getScore(), controller.getLives());
    }


    private void worldRender(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        controller.balloonController.render(batch);
        batch.end();
    }

    public void resize(int width, int height) {
        float ratio = (float) width / (float) height;
        updatesHUD.resize(width, height);
        camera.viewportWidth = Constants.VIEWPORT_HEIGHT * ratio;
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }


    private final class UpdatesHUD {
        private OrthographicCamera camera;
        private Viewport viewport;
        private static final float SCENE_WIDTH = 400f;
        private static final float SCENE_HEIGHT = 600f;
        private TextureRegion balloon;
        private final Vector2 dimension = new Vector2(45f, 45f * 1.53f);
        private final float margin = 15f;
        private BitmapFont font;

        public UpdatesHUD() {
            camera = new OrthographicCamera();
            viewport = new ExtendViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
            balloon = Assets.instance.assetBalloon.balloon;

            font = Assets.instance.assetFonts.defaultSmall;
        }

        public void render(SpriteBatch batch, int score, int lives) {
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            renderExtraLives(batch, lives);
            renderScore(batch, score);
            renderFPS(batch);
            batch.end();
        }

        public void resize(int width, int height) {
            viewport.update(width, height, true);
        }

        private void renderExtraLives(SpriteBatch batch, int lives) {
            float x = camera.viewportWidth - Constants.INITIAL_LIVES * (dimension.x + margin);
            float y = camera.viewportHeight - (dimension.y + margin);

            for (int i = 0; i < Constants.INITIAL_LIVES; i++) {

                if (lives <= i) batch.setColor(.5f, .5f, .5f, .9f);
                batch.draw(
                        balloon.getTexture(),
                        x + i * (dimension.x + margin),
                        y,
                        0,
                        0,
                        dimension.x,
                        dimension.y,
                        1,
                        1,
                        0,
                        balloon.getRegionX(),
                        balloon.getRegionY(),
                        balloon.getRegionWidth(),
                        balloon.getRegionHeight(),
                        false,
                        false
                );
                batch.setColor(1, 1, 1, 1);
            }
        }

        private void renderScore(SpriteBatch batch, int score) {
            font.getData().setScale(0.4f);
            float x = 0;
            float y = camera.viewportHeight - (dimension.y + margin);
            batch.setColor(Color.GOLD);
            batch.draw(
                    balloon.getTexture(),
                    margin,
                    y,
                    0,
                    0,
                    dimension.x,
                    dimension.y,
                    1,
                    1,
                    0,
                    balloon.getRegionX(),
                    balloon.getRegionY(),
                    balloon.getRegionWidth(),
                    balloon.getRegionHeight(),
                    false,
                    false
            );
            batch.setColor(1, 1, 1, 1);
            font.draw(
                    batch,
                    String.valueOf(score),
                    x + 80,
                    y + 54
            );
        }

        private void renderFPS(SpriteBatch batch) {
            font.getData().setScale(0.2f);
            float fps = Gdx.graphics.getFramesPerSecond();
            float x = camera.viewportWidth - 56;
            float y = 15;
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

    }

}
