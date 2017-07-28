package com.colisa.maputo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.colisa.maputo.screens.DirectedGame;
import com.colisa.maputo.screens.MenuScreen;
import com.colisa.maputo.screens.transition.ScreenTransition;
import com.colisa.maputo.screens.transition.ScreenTransitionSlide;

public class MaputoGame extends DirectedGame {

	@Override
	public void create() {
		ScreenTransition transition = ScreenTransitionSlide.init(2, ScreenTransitionSlide.LEFT, false, Interpolation.swing);
		setScreen(new MenuScreen(this), transition);
	}
}
