package com.colisa.maputo.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.colisa.maputo.Constants;

public class SkinManager implements Disposable{
    private static final String TAG = "SkinManager";
    private static SkinManager instance;
    private Skin skin;

    public static SkinManager getInstance() {
        if (instance == null){
            instance = new SkinManager(Constants.SKIN_MAPUTO_UI);
            Gdx.app.debug(TAG, "skin loaded");
        }
        return instance;
    }

    private SkinManager(String skinFile){
        TextureAtlas atlas = new TextureAtlas(Constants.TEXTURE_MAPUTO_UI);
        skin = new Skin(atlas);
        skin.load(Gdx.files.internal(skinFile));

    }


    public Skin getSkin() {
        return skin;
    }

    @Override
    public void dispose() {
        if (skin != null) {
            skin.dispose();
            Gdx.app.debug(TAG, "skin disposed");
            skin = null;
            instance = null;
        }
    }
}
