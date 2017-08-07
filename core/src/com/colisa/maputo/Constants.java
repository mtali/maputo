package com.colisa.maputo;

import com.badlogic.gdx.Application;

@SuppressWarnings("WeakerAccess")
public class Constants {

    public static final boolean DEBUG_FLAG_MENU_SCREEN = false;
    public static final boolean DEBUG_FLAG_GAME_SCREEN = true;
    public static final boolean OVERRIDE_GAME_OVER = true;
    public static final int LOG_LEVEL = Application.LOG_DEBUG;

    public static final String SKIN_MAPUTO_UI = "data/maputo-ui.json";
    public static final String TEXTURE_MAPUTO_UI = "data/packed/maputo-ui.pack.atlas";
    public static final String SKIN_LIBGDX_UI = "data/uiskin.json";
    public static final String TEXTURE_LIBGDX_UI = "data/uiskin.atlas";

    // fonts
    public static final String FONTS = "data/fonts/default.fnt";

    public static final float VIEWPORT_GUI_WIDTH = 480.0f;
    public static final float VIEWPORT_GUI_HEIGHT = 800.0f;

    public static final float VIEWPORT_WIDTH = 10;
    public static final float VIEWPORT_HEIGHT = VIEWPORT_WIDTH * 2;

    public static final float BALLOON_SPAWN_TIME = 1.0f;

    // Assets
    public static final String BALLOON = "balloon";

    public static final int INITIAL_LIVES = 3;
    public static final int BALLOON_HIT_SCORE = 1;
    public static final float TIME_DISPLAY_GAME_OVER = 3;

    public static final float BALLOON_Y_TERMINAL = 50;

}
