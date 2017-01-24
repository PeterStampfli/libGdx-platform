package com.mygdx.game.utilities;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Created by peter on 1/22/17.
 */

public class Assets extends AbstractAssets {

    public TiledMap tiledMap;
    public Texture acornTexture;
    public Sound jumpSound;
    public Sound acornSound;
    public Music peteMusic;

    public void load(){
        loadTmxMap("tiledMap.tmx");
        loadTexture("pete.png");
        loadTexture("acorn.png");
        loadSound("jump.wav");
        loadSound("acorn.wav");
        loadMusic("peteTheme.mp3");

    }

    public void get(){
        tiledMap=assetManager.get("tiledMap.tmx");
        acornTexture=assetManager.get("acorn.png");
        jumpSound=assetManager.get("jump.wav");
        acornSound=assetManager.get("acorn.wav");
        peteMusic=assetManager.get("peteTheme.mp3");
    }
}
