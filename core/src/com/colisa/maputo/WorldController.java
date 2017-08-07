package com.colisa.maputo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.colisa.maputo.objects.Balloon;
import com.colisa.maputo.screens.*;
import com.colisa.maputo.screens.MenuScreen;
import com.colisa.maputo.transition.ScreenTransition;
import com.colisa.maputo.transition.ScreenTransitionSlide;

@SuppressWarnings("WeakerAccess")
public class WorldController extends InputAdapter implements Disposable {
    private static final String TAG = WorldController.class.getName();
    private DirectedGame game;
    private Camera camera;
    private Vector3 touchPosition;
    private Rectangle detectionRectangle = new Rectangle();
    public Level level;
    private BalloonController bController;
    private int lives;
    private int score;
    private boolean gameOver;
    private float timeLeftGameOver;

    public WorldController(DirectedGame game) {
        this.game = game;
        init();
    }

    private void init() {
        initLevel();
    }

    private void initLevel() {
        level = new Level();
        bController = level.balloonController;
        touchPosition = new Vector3();
        lives = level.lives;
        score = level.score;
        gameOver = level.gameOver;
        timeLeftGameOver = Constants.TIME_DISPLAY_GAME_OVER;
    }


    public void update(float delta) {
        if (isGameOver()) {
            timeLeftGameOver -= delta;
            if (timeLeftGameOver < 0) {
                backToMainMenu();
            }
        } else {
            testFingerBalloonCollision();
            checkBalloonsHitTopOfScreen();
            level.update(delta, camera);
        }

    }

    private void testFingerBalloonCollision() {
        if (Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            for (Balloon balloon : bController.getBalloons()) {
                if (!balloon.isRunning()) continue;
                // check if finger touch a the screen
                detectionRectangle.set(
                        balloon.position.x, balloon.position.y, balloon.bounds.width, balloon.bounds.height);
                if (detectionRectangle.contains(touchPosition.x, touchPosition.y)) {
                    // add score
                    score += Constants.BALLOON_HIT_SCORE;
                    balloon.setBalloonState(Balloon.STATES.STOPPED);
                    break;
                }
            }
        }
    }

    private void checkBalloonsHitTopOfScreen() {
        for (Balloon balloon : bController.getBalloons()) {
            if (!balloon.isRunning()) continue;
            if (hasBalloonHitTopOfScreen(balloon)) {
                lives -= 1;
                if (lives < 0) gameOver = true;
                balloon.setBalloonState(Balloon.STATES.ZOMBIE);
            }
            if (outOfScreen(balloon)) {
                balloon.setBalloonState(Balloon.STATES.STOPPED);
            }
        }
    }

    private boolean outOfScreen(Balloon balloon) {
        return balloon.position.y >= camera.viewportHeight / 2;
    }

    private boolean hasBalloonHitTopOfScreen(Balloon balloon) {
        return balloon.position.y + balloon.dimension.y >= camera.viewportHeight / 2;
    }


    @Override
    public void dispose() {

    }


    @Override
    public boolean keyUp(int keycode) {
        Gdx.app.debug(TAG, "Fires");
        if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
            backToMainMenu();
        }
        return false;
    }

    private void backToMainMenu() {
        ScreenTransition transition
                = ScreenTransitionSlide.init(0.5f, ScreenTransitionSlide.DOWN, false, Interpolation.fade);
        game.setScreen(new MenuScreen(game), transition);
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public boolean isGameOver() {
        if (Constants.OVERRIDE_GAME_OVER) return false;
        else return gameOver;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }
}
