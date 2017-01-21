package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.Device;

/**
 * Created by peter on 1/19/17.
 */

public class GameScreen extends ScreenAdapter {

    private TheGame theGame;
    private Device device;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    private Pete pete;


    public GameScreen(TheGame theGame){
        this.theGame=theGame;
        device=theGame.device.createShapeRenderer();
        shapeRenderer=device.shapeRenderer;
        spriteBatch=device.spriteBatch;
        viewport=device.viewport;
        assetManager=device.assetManager;
    }

    @Override
    public void show(){
        tiledMap=assetManager.get("tiledMap.tmx");
        if (orthogonalTiledMapRenderer==null) {
            orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, spriteBatch);
            viewport.apply(true);
            orthogonalTiledMapRenderer.setView(device.camera);
            device.disposer.add(orthogonalTiledMapRenderer,"tileMapRenderer");
        }
        pete=new Pete(theGame);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public void drawDebug(){
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        pete.drawDebug();
        shapeRenderer.end();
    }

    public void draw(){
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        orthogonalTiledMapRenderer.render();

        spriteBatch.begin();
        pete.draw();

        spriteBatch.end();
    }

    @Override
    public void render(float delta){
        pete.update(delta);
        viewport.apply(true);

        Basic.clearBackground(Color.CYAN);

        draw();
        drawDebug();

    }

}
