package com.colisa.maputo.objects;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Balloon extends BaseObject {

    private static final String TAG = "Balloon";
    private Color color;
    private TextureRegion region;
    private boolean alive;
    private boolean canCollide;

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

    public void setRegion(TextureRegion region) {
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
