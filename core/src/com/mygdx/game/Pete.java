package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.Constants;
import com.mygdx.game.utilities.Device;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by peter on 1/21/17.
 */

public class Pete {
    private TheGame theGame;
    private Device device;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private final Rectangle collisionRectangle;
    private Vector2 position;
    private Vector2 speed;

    private final Animation walking;
    private final TextureRegion standing;
    private final TextureRegion jumpUp;
    private final TextureRegion jumpDown;
    private Texture peteTexture;

    private boolean canJump=true;
    private float jumpedDistance=0;
    private boolean facingRight=true;

    public Pete(TheGame theGame){
        this.theGame=theGame;
        device=theGame.device;
        shapeRenderer=device.shapeRenderer;
        spriteBatch=device.spriteBatch;
        collisionRectangle=new Rectangle(0,0, Constants.PETE_WIDTH,Constants.PETE_HEIGHT);
        position=new Vector2();
        speed=new Vector2();
        peteTexture=device.assetManager.get("pete.png");
        TextureRegion[] regions=TextureRegion.split(peteTexture,Constants.PETE_WIDTH,Constants.PETE_HEIGHT)[0];
        walking=new Animation(0.25f,regions[0],regions[1]);
        standing=regions[0];
        jumpUp=regions[2];
        jumpDown=regions[3];
    }

    public void updateCollisionRectangle(){
        collisionRectangle.setPosition(position);
    }

    public void update(float delta){
        if (input.isKeyPressed(Input.Keys.RIGHT)){
            speed.x=Constants.PETE_X_SPEED;
            facingRight=true;
        }
        else if (input.isKeyPressed(Input.Keys.LEFT)){
            speed.x=-Constants.PETE_X_SPEED;
            facingRight=false;
        }
        else {
            speed.x=0;
        }
        if (input.isKeyPressed(Input.Keys.SPACE)&&canJump){
            speed.y=Constants.PETE_Y_SPEED;
            jumpedDistance+=speed.y*delta;
            canJump=(jumpedDistance<Constants.PETE_MAX_JUMPDISTANCE);
        } else {
            speed.y=-Constants.PETE_Y_SPEED;
        }
        position.mulAdd(speed,delta);

        position.x=MathUtils.clamp(position.x,0,Constants.WORLD_WIDTH-Constants.PETE_WIDTH);
        if (position.y<0){
            position.y=0;
            jumpedDistance=0;
            canJump=true;
        }
        updateCollisionRectangle();

    }

    public void drawDebug(){
        shapeRenderer.setColor(Color.SALMON);
        shapeRenderer.rect(position.x,position.y,Constants.PETE_WIDTH,Constants.PETE_HEIGHT);
    }

    public void draw(){
        TextureRegion toDraw=standing;
        if (speed.x!=0){
            toDraw=walking.getKeyFrame(Basic.getTime());
        }
        if (speed.y>0){
            toDraw=jumpUp;
        } else if (speed.y<0){
            toDraw=jumpDown;
        }

        toDraw.flip(!facingRight&&!toDraw.isFlipX(),false);

        if (facingRight&&toDraw.isFlipX()){
            toDraw.flip(true,false);
        }

        spriteBatch.draw(toDraw,position.x,position.y);
    }

}
