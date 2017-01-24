package com.mygdx.game.utilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by peter on 1/22/17.
 */

public class AbstractAssets {
    AssetManager assetManager;
    private boolean hasTmxMapLoader=false;

    public void setAssetManager(AssetManager assetManager){
        this.assetManager=assetManager;
    }

    public void finishLoading(){
        assetManager.finishLoading();
    }

    public boolean update(){
        return assetManager.update();
    }

    public float getProgress(){
        return assetManager.getProgress();
    }

    public void loadTexture(String name){
        assetManager.load(name, Texture.class);
    }

    public void loadSound(String name){
        assetManager.load(name, Sound.class);
    }

    public  void loadMusic(String name){
        assetManager.load(name, Music.class);
    }

    public void loadTmxMap(String name){
        if (!hasTmxMapLoader) {
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
            hasTmxMapLoader=true;
        }
        assetManager.load(name,TiledMap.class);
    }



}
