package com.mygdx.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utilities.Constants;
import com.mygdx.game.utilities.Device;

import java.util.Iterator;

/**
 * Created by peter on 1/24/17.
 */

public class Acorns {

    private Array<Vector2> positions=new Array<Vector2>();
    private Device device;
    private Rectangle collisionRectangle=new Rectangle(0,0, Constants.ACORN_WIDTH,Constants.ACORN_HEIGHT);


    public Acorns(Device device){
        this.device=device;
    }

    public void add(float x,float y){
        positions.add(new Vector2(x,y));
    }

    public void populate(){
        MapLayer mapLayer=device.assets.tiledMap.getLayers().get("Collectables");
        for (MapObject mapObject:mapLayer.getObjects()){
            add(mapObject.getProperties().get("x",Float.class),mapObject.getProperties().get("y",Float.class));
        }
    }

    public void collisions(Pete pete){
        Rectangle peteRectangle=pete.collisionRectangle;
        Iterator<Vector2>iter=positions.iterator();
        Vector2 position;
        while (iter.hasNext()) {
            position=iter.next();
            collisionRectangle.setPosition(position.x,position.y);
            if (peteRectangle.overlaps(collisionRectangle)){
                device.assets.soundManager.play("acorn");
                iter.remove();
            }
        }
    }

    public void draw(){
        for (Vector2 acorn:positions){
            device.spriteBatch.draw(device.assets.acornTexture,acorn.x,acorn.y);
        }
    }
}
