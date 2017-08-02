package com.colisa.maputo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import com.colisa.maputo.transition.ScreenTransition;
import com.colisa.maputo.transition.ScreenTransitionSlide;

public class WorldController extends InputAdapter implements Disposable {
    private static final String TAG = WorldController.class.getName();
    private DirectedGame game;
    public Level level;
    private Camera camera;
    private float balloonLastSpawn;

    public WorldController(DirectedGame game) {
        this.game = game;
        init();
    }

    private void init() {
        initLevel();
    }

    private void initLevel() {
        level = new Level();
        balloonLastSpawn = level.balloonSpawnTime;
    }


    public void update(float delta) {
        level.update(delta, camera);
    }

    @Override
    public void dispose() {

    }


    @Override
    public boolean keyUp(int keycode) {
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
}
