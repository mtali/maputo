package com.colisa.maputo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
    private int lives;
    private int score;
    private boolean gameOver;
    private float timeLeftGameOver;

    public BalloonController balloonController;

    public WorldController(DirectedGame game) {
        this.game = game;
        init();
    }

    private void init() {
        balloonController = new BalloonController(0.85f, new Vector2(0, 9));
        initLevel();
    }

    private void initLevel() {
        level = new Level();
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
        }

        balloonController.update(delta, camera);
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
        else return balloonController.gameOver;
    }

    public int getLives() {
        return balloonController.lives;
    }

    public int getScore() {
        return balloonController.score;
    }
}
