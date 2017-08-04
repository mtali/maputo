package com.colisa.maputo;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.colisa.maputo.screens.UI;
import com.colisa.maputo.transition.ScreenTransition;
import com.colisa.maputo.transition.ScreenTransitionSlide;

public class MenuScreen extends AbstractScreen {
    private static final String TAG = MenuScreen.class.getName();
    private Skin maputoSkin;
    private Skin skinLibgdx;
    private Stage stage;
    private Button playButton;
    private Button optionsButton;
    private Button leadersButton;
    private ButtonClickListener listener;
    private NinePatchDrawable menuBackground;

    private static boolean initialized = false;
    private static float xScalingFactor;
    private static float yScalingFactor;
    private static Sprite background;
    private static SpriteBatch batch;
    private static float deltaX;
    private static float deltaY;
    public MenuScreen(DirectedGame game) {
        super(game);
    }

    @Override
    public void show() {


        stage = new Stage(new FitViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        if (listener == null) listener = new ButtonClickListener();
        // rebuilding the stage including individual layers
        maputoSkin = new Skin(Gdx.files.internal(Constants.SKIN_MAPUTO_UI), new TextureAtlas(Constants.TEXTURE_MAPUTO_UI));
        skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI), new TextureAtlas(Constants.TEXTURE_LIBGDX_UI));

        if (!initialized) {
            batch = new SpriteBatch();
            background = new Sprite(maputoSkin.get("background", TextureRegion.class));
            updateBackground();
        }

        if (null == menuBackground)
            menuBackground = new NinePatchDrawable(
                    new NinePatch(maputoSkin.getRegion("menu-background"), 10, 10, 10, 10)
            );
        Stack stack = new Stack();
        stage.clear();
        stage.addActor(stack);
        stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);

        // building individual layers
        //Table backgroundLayer = buildBackgroundLayer();
        Table controlsLayer = buildControlLayers();
        // assemble stage
        //stack.add(backgroundLayer);
        stack.add(controlsLayer);
        stack.pack();
    }

    private void updateBackground() {
        xScalingFactor = Gdx.graphics.getWidth() / background.getWidth();
        yScalingFactor = Gdx.graphics.getHeight() / background.getHeight();

        deltaX = 0;
        deltaY = 0;
        background.setOrigin(0, 0);
        if (xScalingFactor >= yScalingFactor) {
            background.setScale(xScalingFactor);
            deltaY = (Gdx.graphics.getHeight() - background.getHeight() * xScalingFactor);
        } else {
            background.setScale(yScalingFactor);
            deltaX = (Gdx.graphics.getWidth() - background.getWidth() * yScalingFactor);
        }

        background.setPosition(deltaX, deltaY);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        batch.end();
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


    private Table buildControlLayers() {
        Table rootTable = new Table();
        // + image balloon
        Image imageBalloon = new Image(maputoSkin, "balloon");
        rootTable.add(imageBalloon).row();
        // + buttons table
        Table buttonsTable = new Table();
        playButton = new Button(maputoSkin, "play");
        optionsButton = new Button(maputoSkin, "options");
        leadersButton = new Button(maputoSkin, "leaders");
        buttonsTable.add(playButton).pad(10).row();
        playButton.addListener(listener);
        buttonsTable.add(optionsButton).pad(10).row();
        optionsButton.addListener(listener);
        buttonsTable.add(leadersButton).pad(10).row();
        leadersButton.addListener(listener);
        rootTable.add(buttonsTable).pad(-10, 0, 0, 0).row();
        buttonsTable.setBackground(menuBackground);
        if (Constants.DEBUG_FLAG_MENU_SCREEN) rootTable.debugAll();
        return rootTable;

    }


    private Table buildBackgroundLayer() {
        Table layer = new Table();
        Image imageBackground = new Image(maputoSkin, "background");
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
                GameScreen gameScreen = new GameScreen(game);
               // game.setScreen(gameScreen, transition);
            } else if (actor.equals(optionsButton)) {
                Gdx.app.debug(TAG, "Options button clicked");
            } else if (actor.equals(leadersButton)) {
                Gdx.app.debug(TAG, "Leaders button clicked");
            }
        }
    }
}
