package com.colisa.maputo;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.colisa.maputo.entities.Balloon;

public class Level {
    private static final String TAG = Level.class.getName();
    public Array<Balloon> balloons;
    public float spawnTime;
    public int numberOfBalloons;

    public Level(){
        init();
    }

    private void init() {
        numberOfBalloons = 20;
        balloons = new Array<Balloon>();
        spawnTime = Constants.BALLOON_SPAWN_TIME;

    }


    public void render(SpriteBatch batch) {
        for (Balloon balloon: balloons)
            balloon.render(batch);
    }

    public void update(float deltaTime) {
        for (Balloon balloon: balloons)
            balloon.update(deltaTime);
    }
}
