package com.mygdx.game.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Created by peter on 1/22/17.
 */

public class Assets extends AbstractAssets {

    public TiledMap tiledMap;
    public Texture acornTexture;

    public void load(){
        loadTmxMap("tiledMap.tmx");
        loadTexture("pete.png");
        loadTexture("acorn.png");

    }

    public void get(){
        tiledMap=assetManager.get("tiledMap.tmx");
        acornTexture=assetManager.get("acorn.png");
    }
}
