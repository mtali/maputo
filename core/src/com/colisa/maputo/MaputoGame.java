package com.colisa.maputo;

import com.badlogic.gdx.math.Interpolation;
import com.colisa.maputo.mainmenu.MenuScreen;
import com.colisa.maputo.transition.ScreenTransition;
import com.colisa.maputo.transition.ScreenTransitionSlide;

public class MaputoGame extends DirectedGame {

	@Override
	public void create() {
		ScreenTransition transition = ScreenTransitionSlide.init(2, ScreenTransitionSlide.LEFT, false, Interpolation.swing);
		setScreen(new MenuScreen(this), transition);
	}
}
