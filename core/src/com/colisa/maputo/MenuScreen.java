package com.colisa.maputo;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.colisa.maputo.transition.ScreenTransition;
import com.colisa.maputo.transition.ScreenTransitionSlide;

public class MenuScreen extends AbstractScreen {
    private static final String TAG = MenuScreen.class.getName();
    private Skin maputoSkin;
    private Skin skinLibgdx;
    private Stage stage;
    private Button playButton;
    private Button settingButton;
    private Image imageBackground;
    private ButtonClickListener listener;

    public MenuScreen(DirectedGame game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        if (listener == null) listener = new ButtonClickListener();
        // rebuilding the stage including individual layers
        maputoSkin = new Skin(Gdx.files.internal(Constants.SKIN_MAPUTO_UI), new TextureAtlas(Constants.TEXTURE_MAPUTO_UI));
        skinLibgdx = new Skin();
        Stack stack = new Stack();
        stage.clear();
        stage.addActor(stack);
        stack.setFillParent(true);

        // building individual layers
        Table backgroundLayer = buildBackgroundLayer();
        Table controlsLayer = buildControlsLayer();

        // assemble stage
        stack.add(backgroundLayer);
        stack.add(controlsLayer);
        stack.pack();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void hide() {
        skinLibgdx.dispose();
        maputoSkin.dispose();
        stage.dispose();
        Gdx.app.debug(TAG, "disposing menu screen");
    }

    @Override
    public void pause() {

    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }


    private Table buildControlsLayer() {
        Table layer = new Table();
        layer.setFillParent(true);
        layer.center();
        // + play button
        playButton = new Button(maputoSkin, "play");
        layer.add(playButton);
        playButton.addListener(listener);
        // + setting button
        settingButton = new Button(maputoSkin, "setting");
        layer.add(settingButton);
        settingButton.addListener(listener);
        if (Constants.DEBUG_FLAG_MENU_SCREEN) layer.debug();
        return layer;
    }

    private Table buildBackgroundLayer() {
        Table layer = new Table();
        imageBackground = new Image(maputoSkin, "background");
        layer.add(imageBackground);
        return layer;
    }

    private class ButtonClickListener extends ChangeListener {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            if (actor.equals(playButton)) {
                // play button clicked
                ScreenTransition transition = ScreenTransitionSlide
                                .init(0.5f, ScreenTransitionSlide.DOWN, false, Interpolation.fade);
                game.setScreen(new GameScreen(game), transition);
            } else if (actor.equals(settingButton)) {
                Gdx.app.debug(TAG, "Settings button clicked");
            }
        }
    }
}
