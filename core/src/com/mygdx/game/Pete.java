package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utilities.Assets;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.Constants;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.SoundManager;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by peter on 1/21/17.
 */

public class Pete {
    private TheGame theGame;
    private Device device;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    Assets assets;
    SoundManager soundManager;
    public final Rectangle collisionRectangle;
    public Vector2 position;
    private Vector2 speed;

    private final Animation walking;
    private final TextureRegion standing;
    private final TextureRegion jumpUp;
    private final TextureRegion jumpDown;
    private Texture peteTexture;

    private boolean canJump = true;
    private float jumpedDistance = 0;
    private boolean facingRight = true;

    public Pete(TheGame theGame) {
        this.theGame = theGame;
        device = theGame.device;
        assets = device.assets;
        soundManager=assets.soundManager;
        shapeRenderer = device.shapeRenderer;
        spriteBatch = device.spriteBatch;
        collisionRectangle = new Rectangle(0, 0, Constants.PETE_WIDTH, Constants.PETE_HEIGHT);
        position = new Vector2(10,50);
        speed = new Vector2();
        peteTexture = device.assetManager.get("pete.png");
        TextureRegion[] regions = TextureRegion.split(peteTexture, Constants.PETE_WIDTH, Constants.PETE_HEIGHT)[0];
        walking = new Animation(0.25f, regions[0], regions[1]);
        standing = regions[0];
        jumpUp = regions[2];
        jumpDown = regions[3];
    }

    public void updateCollisionRectangle() {
        collisionRectangle.setPosition(position);
    }

    public void hasLanded(){
        jumpedDistance = 0;
        canJump = true;

    }

    public void update(float delta) {
        if (input.isKeyPressed(Input.Keys.RIGHT)) {
            speed.x = Constants.PETE_X_SPEED;
            facingRight = true;
        } else if (input.isKeyPressed(Input.Keys.LEFT)) {
            speed.x = -Constants.PETE_X_SPEED;
            facingRight = false;
        } else {
            speed.x = 0;
        }
        if (input.isKeyPressed(Input.Keys.SPACE) && canJump) {
            if (speed.y<Constants.PETE_Y_SPEED){
                soundManager.play("jump");
            }
            speed.y = Constants.PETE_Y_SPEED;
            jumpedDistance += speed.y * delta;
            canJump = (jumpedDistance < Constants.PETE_MAX_JUMPDISTANCE);
        } else {
            speed.y = -Constants.PETE_Y_SPEED;
        }
        position.mulAdd(speed, delta);

        position.x=MathUtils.clamp(position.x,0f,theGame.gameScreen.levelWidth-Constants.PETE_WIDTH);


        if (position.y < 0) {
            position.y = 0;
            hasLanded();
         }
        updateCollisionRectangle();

    }

    public void drawDebug() {
        shapeRenderer.setColor(Color.SALMON);
        shapeRenderer.rect(position.x, position.y, Constants.PETE_WIDTH, Constants.PETE_HEIGHT);
    }

    public void draw() {
        TextureRegion toDraw = standing;
        if (speed.x != 0) {
            toDraw = walking.getKeyFrame(Basic.getTime());
        }
        if (speed.y > 0) {
            toDraw = jumpUp;
        } else if (speed.y < 0) {
            toDraw = jumpDown;
        }

        toDraw.flip(!facingRight && !toDraw.isFlipX(), false);

        if (facingRight && toDraw.isFlipX()) {
            toDraw.flip(true, false);
        }

        spriteBatch.draw(toDraw, position.x, position.y);
    }


    private class CollisionCell {
        public final TiledMapTileLayer.Cell cell;
        public final int cellX;
        public final int cellY;

        public CollisionCell(TiledMapTileLayer.Cell cell, int cellX, int cellY) {
            this.cell = cell;
            this.cellX = cellX;
            this.cellY = cellY;
        }

        public boolean isEmpty() {
            return cell == null;
        }


    }

    private void addCell(Array<CollisionCell> cells,TiledMapTileLayer tiledMapTileLayer, int cellX, int cellY){
        TiledMapTileLayer.Cell cell=tiledMapTileLayer.getCell(cellX,cellY);
        if (cell!=null){
            cells.add(new CollisionCell(cell,cellX,cellY));
        }
    }

    private Array<CollisionCell> getCellsCovered() {
        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) assets.tiledMap.getLayers().get(0);
        Array<CollisionCell> cellsCovered = new Array<CollisionCell>();
        int bottomLeftX=MathUtils.floor(position.x/Constants.CELL_SIZE);
        int bottomLeftY=MathUtils.floor(position.y/Constants.CELL_SIZE);
        addCell(cellsCovered,tiledMapTileLayer,bottomLeftX,bottomLeftY);
        addCell(cellsCovered,tiledMapTileLayer,bottomLeftX+1,bottomLeftY);
        addCell(cellsCovered,tiledMapTileLayer,bottomLeftX,bottomLeftY+1);
        addCell(cellsCovered,tiledMapTileLayer,bottomLeftX+1,bottomLeftY+1);
        return  cellsCovered;
    }

    public void handleCollision(){
        Array<CollisionCell> collisions=getCellsCovered();
        Rectangle intersection=new Rectangle();
        Rectangle cellRectangle=new Rectangle(0,0,Constants.CELL_SIZE,Constants.CELL_SIZE);
        for (CollisionCell collisionCell:collisions){
            cellRectangle.setPosition(collisionCell.cellX*Constants.CELL_SIZE,
                    collisionCell.cellY*Constants.CELL_SIZE);
            Intersector.intersectRectangles(collisionRectangle,cellRectangle,intersection);
            if ((intersection.getHeight()>1)&&(intersection.getWidth()>1)) {           // true intersections
                if (intersection.getWidth()>intersection.getHeight()) {
                    // landing on top of a cell
                    position.y=intersection.getY()+intersection.getHeight();
                    updateCollisionRectangle();
                    hasLanded();
                }
            }
        }
        for (CollisionCell collisionCell:collisions){
            cellRectangle.setPosition(collisionCell.cellX*Constants.CELL_SIZE,
                    collisionCell.cellY*Constants.CELL_SIZE);
            Intersector.intersectRectangles(collisionRectangle,cellRectangle,intersection);
            if ((intersection.getHeight()>1)&&(intersection.getWidth()>1)) {           // true intersections
                if (intersection.getWidth()<intersection.getHeight()) {
                    if (intersection.getX() > position.x) {
                        // colliding with wall going to the right
                        position.x = intersection.getX() - Constants.PETE_WIDTH;
                        updateCollisionRectangle();
                    } else {
                        // colliding with wall going to the left
                        position.x = intersection.getX() + intersection.getWidth();
                        updateCollisionRectangle();
                    }
                }
            }
        }
    }

}
