package com.colisa.maputo.entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.colisa.maputo.Assets;

public class Balloon extends AbstractEntity {

    public boolean collected;
    private TextureRegion region;

    public Balloon() {
        super();
        region = Assets.instance.assetBalloon.balloon;
        dimension.set(2, 3);
        terminalVelocity.set(0, 10);
        velocity.set(0, 7);
        bounds.set(0, 0, dimension.x, dimension.y);
        collected = false;
    }

    @Override
    public void render(SpriteBatch batch) {
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
}
