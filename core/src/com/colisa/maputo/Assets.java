package com.colisa.maputo;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements AssetErrorListener, Disposable {
    private static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private boolean initialized = false;
    private AssetManager assetManager;

    public AssetBalloon assetBalloon;

    private Assets(){}


    public void init(AssetManager assetManager) {
        if (!initialized) {
            instance.assetManager = assetManager;
            assetManager.setErrorListener(this);
            assetManager.load(Constants.TEXTURE_MAPUTO_UI, TextureAtlas.class);
            assetManager.finishLoading();
            Gdx.app.debug(TAG, "Assets loaded");

            // apply filter for each atlas
            TextureAtlas atlas = assetManager.get(Constants.TEXTURE_MAPUTO_UI);
            for (Texture texture : atlas.getTextures()){
                texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            }

            // caching game resources
            assetBalloon = new AssetBalloon(atlas);
            initialized = true;
        }

    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.debug(TAG, "Couldn't load asset " + asset.fileName);
        initialized = false;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        initialized = false;
        Gdx.app.debug(TAG, "Dispose assets");
    }

    public class AssetBalloon{
        public final AtlasRegion balloon;
        AssetBalloon(TextureAtlas atlas) {
            balloon = atlas.findRegion(Constants.BALLOON);
        }
    }

}
