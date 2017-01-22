package com.mygdx.game.utilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by peter on 1/22/17.
 */

public abstract class AbstractAssets {
    private AssetManager assetManager;
    private boolean hasTmxMapLoader=false;

    public AbstractAssets(){}

    public AbstractAssets(AssetManager assetManager){
        this.assetManager=assetManager;
    }

    abstract public void getAll();

    abstract public void loadAll();

    public void loadTexture(String name){
        assetManager.load(name, Texture.class);

    }

    public void loadTmxMap(String name){
        if (!hasTmxMapLoader) {
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
            hasTmxMapLoader=true;
        }
        assetManager.load(name,TiledMap.class);
    }

}
