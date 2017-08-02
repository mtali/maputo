package com.colisa.maputo;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.colisa.maputo.entities.Balloons;

public class Level {
    private static final String TAG = Level.class.getName();
    public Balloons balloons;
    public float balloonSpawnTime;

    public Level() {
        init();
    }

    private void init() {
        balloonSpawnTime = 0.4f;
        balloons = new Balloons(100, new Vector2(0, 7), balloonSpawnTime);
    }

    public void render(SpriteBatch batch) {
        balloons.render(batch);
    }

    public void update(float deltaTime, Camera camera) {
        balloons.update(deltaTime, camera);
    }
}
