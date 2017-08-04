package com.colisa.maputo;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
@SuppressWarnings("WeakerAccess")
public class Assets implements AssetErrorListener, Disposable {
    private static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private boolean initialized = false;
    private AssetManager assetManager;

    public AssetBalloon assetBalloon;
    public AssetFonts assetFonts;

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
            assetFonts = new AssetFonts();
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
        assetFonts.dispose();
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

    public class AssetFonts {
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;

        public AssetFonts() {
            defaultSmall = new BitmapFont(Gdx.files.internal(Constants.FONTS), true);
            defaultNormal = new BitmapFont(Gdx.files.internal(Constants.FONTS), true);
            defaultBig = new BitmapFont(Gdx.files.internal(Constants.FONTS), true);

            setScaleAndFilter(defaultSmall, 0.75f, Texture.TextureFilter.Linear);
            setScaleAndFilter(defaultNormal, 1.0f, Texture.TextureFilter.Linear);
            setScaleAndFilter(defaultBig, 2.0f, Texture.TextureFilter.Linear);
        }

        private void setScaleAndFilter(BitmapFont font, float scaleXY, Texture.TextureFilter filter) {
            font.getData().setScale(scaleXY);
            font.getRegion().getTexture().setFilter(filter, filter);
        }

        protected void dispose() {
            defaultSmall.dispose();
            defaultNormal.dispose();
            defaultBig.dispose();
        }
    }
}
