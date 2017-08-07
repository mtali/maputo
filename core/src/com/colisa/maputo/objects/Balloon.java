package com.colisa.maputo.objects;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.colisa.maputo.Assets;
import com.colisa.maputo.ColorHelper;
import com.colisa.maputo.Constants;

public class Balloon extends BaseObject implements Poolable {

    private static final String TAG = "Balloon";
    private Color color;
    private boolean alive;
    private boolean canCollide;
    private TextureRegion region;

    public Balloon() {
        super();
        region = Assets.instance.assetBalloon.balloon;
    }

    /**
     * Initialize balloon after getting it from the pool
     */
    public void init(Camera camera, Vector2 speed) {
        dimension.set(2, 3.1f);
        bounds.set(0, 0, dimension.x, dimension.y);
        alive = true;
        canCollide = true;
        // + position
        float x = camera.viewportWidth/2;
        float y = camera.viewportHeight/2;
        position.x = MathUtils.random(-x, x - dimension.x);
        position.y = -y - dimension.y;
        // + velocity
        velocity.set(speed);
        // + terminal velocity
        terminalVelocity.set(0, Constants.BALLOON_Y_TERMINAL);
        // + color
        color = ColorHelper.getRandomColor();
        // + region
        setRegion(region);
    }

    @Override
    public void reset() {
        alive = false;
        canCollide = false;
        velocity.set(0, 0);
    }


    @Override
    public void render(SpriteBatch batch) {
        if (!alive) return;
        if (null != color) batch.setColor(color);
        batch.draw(
                region.getTexture(),
                position.x,
                position.y,
                origin.x,
                origin.y,
                dimension.x,
                dimension.y,
                scale.x,
                scale.y,
                rotation,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false
        );
        batch.setColor(1, 1, 1, 1);
    }


    public void setColor(Color color) {
        this.color = color;
    }

    private void setRegion(TextureRegion region) {
        this.region = region;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }

    public boolean canCollide() {
        return canCollide;
    }
}
