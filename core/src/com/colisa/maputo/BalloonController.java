package com.colisa.maputo;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.colisa.maputo.objects.Balloon;

@SuppressWarnings("WeakerAccess")
public class BalloonController {

    private int amount;
    private Vector2 speed;
    private float defaultSpawnTime;
    private Array<Balloon> balloonArray;
    private TextureRegion region;
    private float timeSinceLastSpawn;


    public BalloonController(int amount, Vector2 speed, float defaultSpawnTime) {
        this.amount = amount;
        this.speed = speed;
        this.defaultSpawnTime = defaultSpawnTime;
        init();
    }

    private void init() {
        balloonArray = new Array<Balloon>(amount);
        region = Assets.instance.assetBalloon.balloon;
        timeSinceLastSpawn = 0.0f;
    }

    private Balloon spawnBalloon(Camera camera) {
        Balloon balloon = new Balloon();
        balloon.dimension.set(2, 3);
        balloon.bounds.set(0, 0, balloon.dimension.x, balloon.dimension.y);
        balloon.setBalloonState(Balloon.STATES.RUNNING);
        // position
        float halfVPWidth = camera.viewportWidth / 2;
        float halfVPHeight = camera.viewportHeight / 2;
        balloon.position.x = MathUtils.random(-halfVPWidth, halfVPWidth - balloon.dimension.x);
        balloon.position.y = -halfVPHeight - balloon.dimension.y;
        // velocity
        balloon.terminalVelocity.set(0, 10);
        balloon.velocity.set(speed);
        // random color
        balloon.setColor(ColorHelper.getRandomColor());
        // region
        balloon.setRegion(region);
        return balloon;
    }

    private void checkSpawn(float delta, Camera camera) {
        timeSinceLastSpawn -= delta;
        if (timeSinceLastSpawn < 0 && amount > 0) {
            balloonArray.add(spawnBalloon(camera));
            amount -= 1;
            timeSinceLastSpawn = defaultSpawnTime;
        }
    }

    public void render(SpriteBatch batch) {
        for (Balloon b : balloonArray)
            b.render(batch);
    }

    public void update(float deltaTime, Camera camera) {
        checkSpawn(deltaTime, camera);
        for (Balloon b : balloonArray)
            b.update(deltaTime);
    }

    public Array<Balloon> getBalloons() {
        return balloonArray;
    }
}
