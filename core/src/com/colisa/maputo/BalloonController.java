package com.colisa.maputo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.colisa.maputo.objects.Balloon;

@SuppressWarnings("WeakerAccess")
public class BalloonController {
    private static final String TAG = "BalloonController";

    private float spawnTime;
    private float timeSinceLastSpawn;
    private Vector2 speed;

    private Rectangle balloonCollisionBox;
    private Vector3 touchPosition;

    public int score;
    public int lives;
    public boolean gameOver;

    private float levelUpTime;


    private final Array<Balloon> activeBalloons = new Array<Balloon>();
    private final Pool<Balloon> balloonPool = new Pool<Balloon>() {
        @Override
        protected Balloon newObject() {
            return new Balloon();
        }

    };

    public BalloonController(float spawnTime, Vector2 initialSpeed) {
        this.spawnTime = spawnTime;
        this.speed = initialSpeed;
        init();
    }

    public void init() {
        gameOver = false;
        timeSinceLastSpawn = 0;
        balloonCollisionBox = new Rectangle();
        touchPosition = new Vector3();
        balloonPool.freeAll(activeBalloons);
        activeBalloons.clear();
        lives = Constants.INITIAL_LIVES;
        levelUpTime = 2;
    }



    public void update(float delta, Camera camera) {
        levelUpTime -= delta;
        if (!gameOver) {
            checkSpawn(delta, camera);
            testFingerBalloonCollision(camera);
            checkBalloonHitScreenTop(camera);
            checkOutOfScreen(camera);
        }

        if (levelUpTime <= 0) {
            speed.set(0, speed.y + 0.5f);
            spawnTime -= 0.015;
            levelUpTime = 2;
            Gdx.app.debug(TAG, "Level up: speed = " + speed.y + " spawn time = " + spawnTime);
        }

        for (Balloon balloon : activeBalloons)
            balloon.update(delta);

    }

    public void render(SpriteBatch batch) {
        for (Balloon balloon : activeBalloons) {
            balloon.render(batch);
        }
    }

    private void checkSpawn(float delta, Camera camera) {
        timeSinceLastSpawn -= delta;
        if (timeSinceLastSpawn < 0) {
            Balloon balloon = balloonPool.obtain();
            balloon.init(camera, speed);
            activeBalloons.add(balloon);
            timeSinceLastSpawn = spawnTime;
        }
    }

    private void testFingerBalloonCollision(Camera camera) {
        if (Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            int len = activeBalloons.size;
            Balloon balloon;
            for (int i = len; --i >= 0;) {
                balloon = activeBalloons.get(i);
                if (!balloon.isAlive()) continue;
                balloonCollisionBox.set(balloon.position.x, balloon.position.y, balloon.bounds.width, balloon.bounds.height);
                if (balloonCollisionBox.contains(touchPosition.x, touchPosition.y)) {
                    score += Constants.BALLOON_HIT_SCORE;
                    balloon.setAlive(false);
                    activeBalloons.removeIndex(i);
                    balloonPool.free(balloon);
                    break;
                }
            }
        }
    }

    private void checkBalloonHitScreenTop(Camera camera) {
        for (Balloon balloon : activeBalloons) {
            if (!balloon.canCollide() || !balloon.isAlive()) continue;
            if (hitTopScreen(balloon, camera)) {
                Gdx.app.debug("BalloonController", "collide");
                lives -= 1;
                if (lives < 0) gameOver = true;
                balloon.setCanCollide(false);
            }
        }
    }

    private void checkOutOfScreen(Camera camera) {
        int len = activeBalloons.size;
        Balloon balloon;
        for (int i = len; --i >= 0;) {
            balloon = activeBalloons.get(i);
            if (balloon.position.y >= camera.viewportHeight / 2) {
                balloon.setAlive(false);
                activeBalloons.removeIndex(i);
                balloonPool.free(balloon);
            }
        }
    }

    private boolean hitTopScreen(Balloon balloon, Camera camera) {
        return balloon.position.y + balloon.dimension.y >= camera.viewportHeight / 2;
    }
}