package com.colisa.maputo.objects;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Balloon extends BaseObject {

    private static final String TAG = "Balloon";
    private Color color;
    private TextureRegion region;
    private STATES state;

    public enum STATES {
        RUNNING, STOPPED, ZOMBIE
    }

    @Override
    public void render(SpriteBatch batch) {
        if (state == STATES.STOPPED) return;
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
    }

    public void setBalloonState(STATES state) {
        this.state = state;
    }

    public STATES getState() {
        return state;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    public boolean isRunning() {
        return state == STATES.RUNNING;
    }

    public boolean isStopped() {
        return state == STATES.STOPPED;
    }

    public boolean isZombie() {
        return state == STATES.ZOMBIE;
    }

}
