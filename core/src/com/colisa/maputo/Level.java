package com.colisa.maputo;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

@SuppressWarnings("WeakerAccess")
public class Level {
    private static final String TAG = Level.class.getName();

    BalloonController balloonController;
    int score;
    int lives;
    boolean gameOver;
    private Vector2 speed = new Vector2(0, 5);

    public Level() {
        init();
    }

    private void init() {

        balloonController = new BalloonController(500, speed, MathUtils.random(0.3f, 0.5f));
        score = 0;
        lives = Constants.INITIAL_LIVES;
        gameOver = false;
    }

    public void render(SpriteBatch batch) {
        balloonController.render(batch);
    }

    public void update(float deltaTime, Camera camera) {
        speed.y += deltaTime / 2;
        balloonController.update(deltaTime, camera);
    }
}
