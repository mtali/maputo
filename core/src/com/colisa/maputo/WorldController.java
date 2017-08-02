package com.colisa.maputo;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.colisa.maputo.DirectedGame;
import com.colisa.maputo.MenuScreen;
import com.colisa.maputo.entities.Balloon;
import com.colisa.maputo.transition.ScreenTransition;
import com.colisa.maputo.transition.ScreenTransitionSlide;

public class WorldController extends InputAdapter implements Disposable {
    private DirectedGame game;
    public Level level;
    private int score;
    private int lives;
    private float balloonSpawnTime;
    private int balloonLeft;
    public Camera camera;

    public WorldController(DirectedGame game) {
        this.game = game;
        init();
    }

    private void init() {
        initLevel();
    }

    private void initLevel() {
        level = new Level();
        balloonSpawnTime = Constants.BALLOON_SPAWN_TIME;
        level.balloons.clear();
        score = 0;
        lives = 0;
        balloonLeft = level.numberOfBalloons;

    }

    private Balloon spawnBalloon() {
        Balloon b = new Balloon();
        float halfCamW = camera.viewportWidth / 2;
        float halfCamH = camera.viewportHeight / 2;
        b.position.x = MathUtils.random(-halfCamW, halfCamW - b.dimension.x);
        b.position.y = -halfCamH - b.dimension.y;
        return b;
    }

    public void update(float delta){
        balloonSpawnTime -= delta;
        if (balloonLeft > 0 && balloonSpawnTime < 0 ) {
            level.balloons.add(spawnBalloon());
            balloonSpawnTime = MathUtils.random(0.1f, 1f);
        }

        level.update(delta);
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
}
