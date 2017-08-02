package com.colisa.maputo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.colisa.maputo.entities.Balloons;
import com.colisa.maputo.transition.ScreenTransition;
import com.colisa.maputo.transition.ScreenTransitionSlide;

public class WorldController extends InputAdapter implements Disposable {
    private static final String TAG = WorldController.class.getName();
    private DirectedGame game;
    public Level level;
    private Camera camera;
    private float balloonLastSpawn;
    private Vector3 touchPosition;
    private Rectangle detectionRectangle = new Rectangle();

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
        touchPosition = new Vector3();
    }


    public void update(float delta) {
        level.update(delta, camera);
        testFingerBalloonCollision();
    }

    private void testFingerBalloonCollision() {
        if (Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            for (Balloons.Balloon b: level.balloons.balloonArray){
                if (b.collected) continue;
                detectionRectangle.set(b.position.x, b.position.y, b.bounds.width, b.bounds.height);
                if (detectionRectangle.contains(touchPosition.x, touchPosition.y)) {
                    b.collected = true;
                    Gdx.app.debug(TAG, "Hit");
                } else {
                    Gdx.app.debug(TAG, "Miss");
                }
            }
        }
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
