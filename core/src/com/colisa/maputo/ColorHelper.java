package com.colisa.maputo;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class ColorHelper {
    public static Color getRandomColor() {
        switch (MathUtils.random(0, 7)) {
            case 0:
                return Color.BLUE;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.ORANGE;
            case 4:
                return Color.RED;
            case 5:
                return Color.VIOLET;
            case 6:
                return Color.PINK;
            case 7:
                return Color.GRAY;
            default:
                return Color.CLEAR;
        }
    }
}
