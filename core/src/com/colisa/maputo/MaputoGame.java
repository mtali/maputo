package com.colisa.maputo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.colisa.maputo.screens.*;
import com.colisa.maputo.screens.GameScreen;
import com.colisa.maputo.screens.MenuScreen;
import com.colisa.maputo.transition.ScreenTransition;
import com.colisa.maputo.transition.ScreenTransitionSlide;

public class MaputoGame extends DirectedGame {


    @Override
    public void create() {
        Gdx.app.setLogLevel(Constants.LOG_LEVEL);

        // init assets has to be done on main thread because it need OpenGL context
        // which is only offered in main thread
        Assets.instance.init(new AssetManager());

        // init skin has to be done on main thread because it need OpenGL context
        // which is only offered in main thread
        SkinManager.getInstance();

        ScreenTransition transition = ScreenTransitionSlide.init(0.5f, ScreenTransitionSlide.DOWN, false, Interpolation.fade);
        setScreen(new MenuScreen(this), transition);
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.instance.dispose();
    }
}
