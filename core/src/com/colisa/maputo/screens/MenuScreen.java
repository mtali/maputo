package com.colisa.maputo.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.colisa.maputo.Assets;
import com.colisa.maputo.DirectedGame;
import com.colisa.maputo.transition.ScreenTransition;
import com.colisa.maputo.transition.ScreenTransitionSlide;

public class MenuScreen extends BasicScreen {
    private static final String TAG = "MenuScreen";
    private Button playButton;
    private Button settingsButton;
    private Button highScoreButton;
    private Image balloonImage;
    private MenuButtonsListener menuListener;
    private NinePatchDrawable controlsBackground;

    // Screens

    public MenuScreen(DirectedGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    protected void generateContent() {
        if (null == menuListener) menuListener = new MenuButtonsListener();
        if (null == controlsBackground) controlsBackground = new NinePatchDrawable(new NinePatch(
                SkinManager.getInstance().getSkin().getRegion("menu-background"), 10, 10, 10, 10
        ));
        super.generateContent();
        createButtonsAndPositionContent();
    }

    private void createButtonsAndPositionContent() {
        Skin skin = SkinManager.getInstance().getSkin();
        final Table outerTable = new Table();
        outerTable.setFillParent(true);
        stage.addActor(outerTable);


        Table innerTable = new Table();

        // Layer to hold the buttons and dark background
        Table buttonsTable = new Table();

        TextureAtlas.AtlasRegion region = Assets.instance.assetBalloon.balloon;

        balloonImage = new Image(region);
        playButton = new TextButton("Play", skin, "play");
        settingsButton = new TextButton("Setting", skin, "setting");
        highScoreButton = new TextButton("Leaders", skin, "leaders");

        buttonsTable.add(playButton).width(UI.Buttons.TEXT_BUTTON_WIDTH).height(UI.Buttons.TEXT_BUTTON_HEIGHT).pad(UI.Buttons.SPACE);
        buttonsTable.row();
        buttonsTable.add(settingsButton).width(UI.Buttons.TEXT_BUTTON_WIDTH).height(UI.Buttons.TEXT_BUTTON_HEIGHT).pad(0);
        buttonsTable.row();
        buttonsTable.add(highScoreButton).width(UI.Buttons.TEXT_BUTTON_WIDTH).height(UI.Buttons.TEXT_BUTTON_HEIGHT).pad(UI.Buttons.SPACE);
        buttonsTable.row();
        buttonsTable.setBackground(controlsBackground);
        innerTable.add(balloonImage).width(UI.Balloon.WIDTH).height(UI.Balloon.HEIGHT).pad(0, 0, 0, 0);
        innerTable.row();
        innerTable.add(buttonsTable).pad(0, 70, 70, 70);
        outerTable.add(innerTable).colspan(2).expand();


        // Add listeners
        playButton.addListener(menuListener);
        settingsButton.addListener(menuListener);
        highScoreButton.addListener(menuListener);
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }

    /** Listening for menu buttons change event*/
    private class MenuButtonsListener extends ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {

            if (actor.equals(playButton)) {

                ScreenTransition transition = ScreenTransitionSlide.init(0.5f, ScreenTransitionSlide.DOWN, false, Interpolation.fade);
                game.setScreen(new GameScreen(game), transition);

            } else if (actor.equals(settingsButton))  {

                Gdx.app.debug(TAG, "Setting button clicked");

            } else if (actor.equals(highScoreButton)) {

                Gdx.app.debug(TAG, "Leaders button clicked");

            } else if (actor.equals(balloonImage)) {
                Gdx.app.debug(TAG, "Balloon clicked");
            }

        }
    }
}
