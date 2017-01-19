package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.Constants;
import com.mygdx.game.utilities.Device;

/**
 * Created by peter on 1/19/17.
 */

public class LoadingScreen extends ScreenAdapter {

    private TheGame theGame;
    private Device device;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private AssetManager assetManager;

    private float progress;
    private static final float BAR_WIDTH=200;
    private static final float BAR_HEIGHT=20;


    public LoadingScreen(TheGame theGame){
        this.theGame=theGame;
        device=theGame.device.createShapeRenderer();
        shapeRenderer=device.shapeRenderer;
        viewport=device.viewport;
        assetManager=device.assetManager;
    }

    @Override
    public void show(){
        assetManager.load("tiledMap.tmx", TiledMap.class);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta){
        if (assetManager.update()){
            theGame.setScreen(theGame.gameScreen);
        }
        else {
            progress=assetManager.getProgress();
        }

        Basic.clearBackground(Color.DARK_GRAY);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setColor(Color.ROYAL);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(Constants.WORLD_WIDTH/2-BAR_WIDTH/2,Constants.WORLD_HEIGHT/2-BAR_HEIGHT/2,
                progress*BAR_WIDTH, BAR_HEIGHT);
        shapeRenderer.end();


    }

}
