package com.colisa.maputo.screens;

@SuppressWarnings("WeakerAccess")
public final class UI {
    /** This class should not be instantiated */
    public UI() {}

    /** Contain basic parameters for creating windows */
    public final class Window {

        /** This class should now be instantiated */
        public Window() {}

        /** Width in pixels for which all bitmaps are optimized */
        public static final float REFERENCE_WIDTH = 1080;

        /** Height in pixels for which all bitmaps are optimized*/
        public static final float REFERENCE_HEIGHT = 1920;
    }

    /** Contain basic parameters for transitions */
    public final class Transition {
        /** This class should not be instantiated */
        private Transition() {}

        public static final float FADE_OUT_TIME = 0.2f;

        public static final float FADE_IN_TIME = 0.3f;

    }

    public final class Buttons {

        /** This class should not be instantiated */
        private Buttons() {}

        public static final int TEXT_BUTTON_WIDTH = 500;

        public static final int TEXT_BUTTON_HEIGHT = 110;

        public static final float SPACE = 10;

    }

    public final class Balloon {

        /** This class should not be instantiated */
        private Balloon() {}

        public static final float WIDTH = 500;

        public static final float HEIGHT = WIDTH * 3/1.9f;

        public static final float PADDING_DOWN = -10;
    }
}
