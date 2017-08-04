package com.colisa.maputo.screens;


import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.colisa.maputo.DirectedGame;

public class MenuScreen extends BasicScreen {

    private Button playButton;
    private Button settingsButton;
    private Button highScoreButton;
    private Image balloonImage;
    public MenuScreen(DirectedGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    protected void generateContent() {
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
        innerTable.add(balloonImage).width(UI.Balloon.MAX_WIDTH).height(UI.Balloon.MAX_WIDTH * 3/1.9f).pad(0, 0, -60, 0 );
        innerTable.row();
        innerTable.add(playButton).width(UI.Buttons.TEXT_BUTTON_WIDTH).height(UI.Buttons.TEXT_BUTTON_HEIGHT).pad(UI.Buttons.SPACE);
        innerTable.row();
        innerTable.add(settingsButton).width(UI.Buttons.TEXT_BUTTON_WIDTH).height(UI.Buttons.TEXT_BUTTON_HEIGHT).pad(UI.Buttons.SPACE);
        innerTable.row();
        innerTable.add(highScoreButton).width(UI.Buttons.TEXT_BUTTON_WIDTH).height(UI.Buttons.TEXT_BUTTON_HEIGHT).pad(UI.Buttons.SPACE);
        innerTable.row();

        outerTable.add(innerTable).colspan(2).expand();

    }

    @Override
    public InputProcessor getInputProcessor() {
        return null;
    }
}
