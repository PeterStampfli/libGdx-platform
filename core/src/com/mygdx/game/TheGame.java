package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.utilities.Constants;
import com.mygdx.game.utilities.Device;

public class TheGame extends Game {

	public Device device;
	public GameScreen gameScreen;
	public LoadingScreen loadingScreen;

	@Override
	public void create () {
		device=new Device().createSpriteBatch();
		device.setLogging(true);
		device.viewport=new FitViewport(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT,device.camera);
		device.assetManager.setLoader(TiledMap.class,new TmxMapLoader(new InternalFileHandleResolver()));

		gameScreen=new GameScreen(this);
		loadingScreen=new LoadingScreen(this);
		setScreen(loadingScreen);



	}



	@Override
	public void dispose () {
		device.dispose();
	}
}
