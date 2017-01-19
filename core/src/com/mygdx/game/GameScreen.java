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

import static com.mygdx.game.utilities.Constants.WORLD_HEIGHT;
import static com.mygdx.game.utilities.Constants.WORLD_WIDTH;

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
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public void drawDebug(){
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(0,0, WORLD_WIDTH, WORLD_HEIGHT);
        shapeRenderer.end();
    }

    public void draw(){
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        spriteBatch.end();
        orthogonalTiledMapRenderer.render();
    }

    @Override
    public void render(float delta){

        viewport.apply(true);

        Basic.clearBackground(Color.CYAN);
        drawDebug();

        draw();
    }

}
