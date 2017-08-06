package com.colisa.maputo.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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

    // Screens
    private GameScreen gameScreen;

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
        super.generateContent();
        createButtonsAndPositionContent();
    }

    private void createButtonsAndPositionContent() {
        Skin skin = SkinManager.getInstance().getSkin();
        Table outerTable = new Table();
        outerTable.setFillParent(true);
        stage.addActor(outerTable);


        Table innerTable = new Table();
        balloonImage = new Image(skin, "balloon");
        playButton = new Button(skin, "play");
        settingsButton = new Button(skin, "options");
        highScoreButton = new Button(skin, "leaders");
        innerTable.add(balloonImage).width(UI.Balloon.WIDTH).height(UI.Balloon.HEIGHT).pad(0, 0, -60, 0);
        innerTable.row();
        innerTable.add(playButton).width(UI.Buttons.TEXT_BUTTON_WIDTH).height(UI.Buttons.TEXT_BUTTON_HEIGHT).pad(UI.Buttons.SPACE);
        innerTable.row();
        innerTable.add(settingsButton).width(UI.Buttons.TEXT_BUTTON_WIDTH).height(UI.Buttons.TEXT_BUTTON_HEIGHT).pad(UI.Buttons.SPACE);
        innerTable.row();
        innerTable.add(highScoreButton).width(UI.Buttons.TEXT_BUTTON_WIDTH).height(UI.Buttons.TEXT_BUTTON_HEIGHT).pad(UI.Buttons.SPACE);
        innerTable.row();

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
                Gdx.app.debug(TAG, "GameScreen: " + gameScreen);
                if (null == gameScreen) gameScreen = new GameScreen(game);
                ScreenTransition transition = ScreenTransitionSlide.init(0.5f, ScreenTransitionSlide.DOWN, false, Interpolation.fade);
                game.setScreen(gameScreen, transition);

            } else if (actor.equals(settingsButton))  {

                Gdx.app.debug(TAG, "Setting button clicked");

            } else if (actor.equals(highScoreButton)) {

                Gdx.app.debug(TAG, "Leaders button clicked");

            }
        }
    }
}
