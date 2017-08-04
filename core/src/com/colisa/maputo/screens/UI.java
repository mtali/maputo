package com.colisa.maputo.screens;

public final class UI {
    // Class contain basic parameters for creating windows
    public static final class Window {
        // this class should not be initialized
        private Window() {}

        // width and height in pixels to which all bitmaps are optimized
        public static final float REFERENCE_WIDTH = 4000;
        public static final float REFERENCE_HEIGHT = 2200;
    }

    public static final class Transition {
        private Transition(){}

        public static final float FADE_IN_TIME = 3.0f;
    }

    public static final class Buttons {
        private Buttons(){}

        public static final int TEXT_BUTTON_WIDTH = 1600;
        public static final int TEXT_BUTTON_HEIGHT = 400;
        public static final int SPACE = 60;
    }

    public static final class Balloon {
        private Balloon(){}

        public static final int MAX_WIDTH = 1500;
        public static final int MAX_HEIGHT = 2000;

    }
}
