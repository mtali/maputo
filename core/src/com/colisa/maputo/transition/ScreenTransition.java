package com.colisa.maputo.transition;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ScreenTransition {
    public float getDuration();

    /**
     * Each transition implement its own render and will be supplied with the following
     *
     * @param batch         the DirectedGame batch
     * @param currentScreen the current game screen texture
     * @param nextScreen    the next screen texture
     * @param alpha         0.0 will render from beginning and 0.25 will render from 25% etc
     */
    public void render(SpriteBatch batch, Texture currentScreen, Texture nextScreen, float alpha);
}
