package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilities.Assets;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.Constants;
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
    private Assets assets;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    private Pete pete;
    private Acorns acorns;
    public float levelWidth;


    public GameScreen(TheGame theGame){
        this.theGame=theGame;
        device=theGame.device.createShapeRenderer();
        shapeRenderer=device.shapeRenderer;
        spriteBatch=device.spriteBatch;
        viewport=device.viewport;
        assets=device.assets;
        acorns=new Acorns(device);
    }

    @Override
    public void show(){
        if (orthogonalTiledMapRenderer==null) {
            orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(assets.tiledMap, spriteBatch);
            viewport.apply(true);
            orthogonalTiledMapRenderer.setView(device.camera);
            device.disposer.add(orthogonalTiledMapRenderer,"tileMapRenderer");
        }
        pete=new Pete(theGame);
        acorns.populate();
        TiledMapTileLayer tiledMapTileLayer=(TiledMapTileLayer) assets.tiledMap.getLayers().get(0);
        levelWidth=tiledMapTileLayer.getWidth()*tiledMapTileLayer.getTileWidth();
        assets.peteMusic.setLooping(true);
        assets.peteMusic.play();
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
        acorns.draw();
        spriteBatch.end();
    }

    public void updateCamera(){
        device.camera.position.x= MathUtils.clamp(pete.position.x, Constants.WORLD_WIDTH/2,
                                                  levelWidth-Constants.WORLD_WIDTH/2);
        device.camera.update();
        orthogonalTiledMapRenderer.setView(device.camera);
    }

    @Override
    public void render(float delta){
        pete.update(delta);
        pete.handleCollision();
        acorns.collisions(pete);
        updateCamera();
        viewport.apply();

        Basic.clearBackground(Color.CYAN);

        draw();
        drawDebug();

    }

}
