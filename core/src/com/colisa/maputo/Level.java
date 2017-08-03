package com.colisa.maputo;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

@SuppressWarnings("WeakerAccess")
public class Level {
    private static final String TAG = Level.class.getName();

    BalloonController balloonController;
    int score;
    int lives;
    boolean gameOver;

    public Level() {
        init();
    }

    private void init() {
        balloonController = new BalloonController(50, new Vector2(0, 5), 2f);
        score = 0;
        lives = Constants.INITIAL_LIVES;
        gameOver = false;
    }

    public void render(SpriteBatch batch) {
        balloonController.render(batch);
    }

    public void update(float deltaTime, Camera camera) {
        balloonController.update(deltaTime, camera);
    }
}
