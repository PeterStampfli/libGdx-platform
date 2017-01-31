package com.mygdx.game.utilities;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Created by peter on 1/22/17.
 */

public class Assets extends AbstractAssets {

    public TiledMap tiledMap;
    public Texture acornTexture;
    public Music peteMusic;

    public void load(){
        loadTmxMap("tiledMap.tmx");
        loadTexture("pete.png");
        loadTexture("acorn.png");
        soundManager.add("jump","acorn");
        loadMusic("peteTheme.mp3");

    }

    public void get(){
        tiledMap=assetManager.get("tiledMap.tmx");
        acornTexture=assetManager.get("acorn.png");

        soundManager.getAll();

        peteMusic=assetManager.get("peteTheme.mp3");
    }
}
