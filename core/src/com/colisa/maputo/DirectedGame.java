package com.colisa.maputo;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.colisa.maputo.transition.ScreenTransition;

public abstract class DirectedGame implements ApplicationListener {

    private static final String TAG = DirectedGame.class.getName();
    private ScreenTransition screenTransition;
    private AbstractScreen currentScreen;
    private AbstractScreen nextScreen;
    private FrameBuffer currentFBO;
    private FrameBuffer nextFBO;
    private SpriteBatch batch;
    private boolean init;
    private float time;

    /**
     * Set next screen without transition
     *
     * @param screen the new screen to replace the current
     */
    public void setScreen(AbstractScreen screen) {
        setScreen(screen, null);
    }

    public void setScreen(AbstractScreen screen, ScreenTransition transition) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        if (!init) {
            currentFBO = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
            nextFBO = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
            batch = new SpriteBatch();
            init = true;
        }

        // Start new transition
        nextScreen = screen;
        nextScreen.show();
        nextScreen.resize(width, height);
        nextScreen.render(0.0f);
        if (currentScreen != null) currentScreen.hide();
        nextScreen.pause();
        Gdx.input.setInputProcessor(null);
        this.screenTransition = transition;
        time = 0;
    }

    @Override
    public void render() {
        float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 0.016f);
        if (nextScreen == null) {
            // no ongoing transition
            if (currentScreen != null) currentScreen.render(deltaTime);
        } else {
            // ongoing transition
            float duration = 0;
            if (screenTransition != null)
                duration = screenTransition.getDuration();
            // update progress on the current transition
            time = Math.min(time + deltaTime, duration);

            if (screenTransition == null || time >= duration) {
                // no transition or transition just finished
                if (currentScreen != null) currentScreen.hide();
                nextScreen.resume();
                Gdx.input.setInputProcessor(nextScreen.getInputProcessor());
                currentScreen = nextScreen;
                nextScreen = null;
                screenTransition = null;

            } else {
                // render screen to FBO
                currentFBO.begin();
                if (currentScreen != null){
                    currentScreen.render(deltaTime);
                } else {
                    Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
                    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                }
                currentFBO.end();
                nextFBO.begin();
                nextScreen.render(deltaTime);
                nextFBO.end();

                // now render the transition to the screen
                float alpha = time / duration;
                screenTransition.render(
                        batch,
                        currentFBO.getColorBufferTexture(),
                        nextFBO.getColorBufferTexture(),
                        alpha
                );
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        if (currentScreen != null) currentScreen.resize(width, height);
        if (nextScreen != null) nextScreen.resize(width, height);
    }

    @Override
    public void pause() {
        if (currentScreen != null) currentScreen.pause();
    }

    @Override
    public void resume() {
        if (currentScreen != null) currentScreen.resume();
    }

    @Override
    public void dispose() {
        if (currentScreen != null) currentScreen.hide();
        if (nextScreen != null) nextScreen.hide();
        if (init) {
            currentFBO.dispose();
            nextFBO.dispose();
            batch.dispose();
            init = false;
        }
        // TODO: you might want to dispose all game assets here
    }
}
