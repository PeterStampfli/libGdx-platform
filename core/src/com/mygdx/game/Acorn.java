package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utilities.Device;

/**
 * Created by peter on 1/23/17.
 */

public class Acorn {
    private final Vector2 position;
    private Device device;

    public Acorn(float x, float y, Device device){
        position=new Vector2(x,y);
        this.device=device;
    }

    public void draw(){
        device.spriteBatch.draw(device.assets.acornTexture,position.x,position.y);
    }
}
