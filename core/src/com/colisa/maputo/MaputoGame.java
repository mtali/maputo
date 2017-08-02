package com.colisa.maputo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Interpolation;
import com.colisa.maputo.transition.ScreenTransition;
import com.colisa.maputo.transition.ScreenTransitionSlide;

public class MaputoGame extends DirectedGame {

	@Override
	public void create() {
		Gdx.app.setLogLevel(Constants.LOG_LEVEL);
		Assets.instance.init(new AssetManager());
		ScreenTransition transition = ScreenTransitionSlide.init(0.5f, ScreenTransitionSlide.DOWN, false, Interpolation.fade);
		setScreen(new MenuScreen(this), transition);
	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.instance.dispose();
	}
}
