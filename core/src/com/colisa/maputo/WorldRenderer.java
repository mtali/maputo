package com.colisa.maputo;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable {
    private static final String TAG = WorldRenderer.class.getName();
    private WorldController controller;
    public OrthographicCamera camera;
    private SpriteBatch batch;

    public WorldRenderer(com.colisa.maputo.WorldController controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
    }

    public void render() {
        worldRender(batch);
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
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
