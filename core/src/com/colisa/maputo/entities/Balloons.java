package com.colisa.maputo.entities;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.colisa.maputo.Assets;

public class Balloons {

    public int amount;
    private Vector2 speed;
    private float defaultSpawnTime;
    public Array<Balloon> balloons;
    private TextureRegion region;
    private float timeSinceLastSpawn;

    public Balloons(int amount, Vector2 speed, float defaultSpawnTime) {
        this.amount = amount;
        this.speed = speed;
        this.defaultSpawnTime = defaultSpawnTime;
        init();
    }

    private void init() {
        balloons = new Array<Balloon>(amount);
        region = Assets.instance.assetBalloon.balloon;
        timeSinceLastSpawn = 0.0f;
    }

    private Balloon spawnBalloon(Camera camera) {
        Balloon b = new Balloon();
        b.dimension.set(2, 3);
        b.bounds.set(0, 0, b.dimension.x, b.dimension.y);
        b.collected = false;

        // position {screen boundaries}
        float halfVPWidth = camera.viewportWidth / 2;
        float halfVPHeight = camera.viewportHeight / 2;
        b.position.x = MathUtils.random(-halfVPWidth, halfVPWidth - b.dimension.x);
        b.position.y = -halfVPHeight - b.dimension.y;

        // velocity
        b.terminalVelocity.set(0, 10);
        b.velocity.set(speed);

        return b;
    }

    private void checkSpawn(float delta, Camera camera) {
        timeSinceLastSpawn -= delta;
        if (timeSinceLastSpawn < 0 && amount > 0) {
            balloons.add(spawnBalloon(camera));
            amount -= 1;
            timeSinceLastSpawn = defaultSpawnTime;
        }
    }


    private class Balloon extends AbstractEntity {
        private static final String TAG = "Balloon";
        private boolean collected;

        @Override
        public void render(SpriteBatch batch) {
            if (collected) return;
            batch.draw(
                    region.getTexture(),
                    position.x,
                    position.y,
                    origin.x,
                    origin.y,
                    dimension.x,
                    dimension.y,
                    scale.x,
                    scale.y,
                    rotation,
                    region.getRegionX(),
                    region.getRegionY(),
                    region.getRegionWidth(),
                    region.getRegionHeight(),
                    false,
                    false
            );
        }


    }

    public void render(SpriteBatch batch) {
        for (Balloon b : balloons)
            b.render(batch);
    }

    public void update(float deltaTime, Camera camera) {
        checkSpawn(deltaTime, camera);
        for (Balloon b : balloons)
            b.update(deltaTime);
    }

}
