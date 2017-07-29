package com.colisa.maputo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.colisa.maputo.mainmenu.MenuScreen;
import com.colisa.maputo.transition.ScreenTransition;
import com.colisa.maputo.transition.ScreenTransitionSlide;

public class MaputoGame extends DirectedGame {

	@Override
	public void create() {
		Gdx.app.setLogLevel(Constants.LOG_LEVEL);
		ScreenTransition transition = ScreenTransitionSlide.init(0.75f, ScreenTransitionSlide.DOWN, false, Interpolation.bounceOut);
		setScreen(new MenuScreen(this), transition);
	}
}
