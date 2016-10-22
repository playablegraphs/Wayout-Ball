package com.awayout.game;

import java.util.Locale;

import com.awayout.game.input.GameInputProcessor;
import com.awayout.game.input.GameKeys;
import com.awayout.game.managers.AdManager;
import com.awayout.game.managers.ResourceManager;
import com.awayout.game.screens.SplashScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class FrameWork extends Game implements Disposable {
	public static Viewport thePort;
	public static OrthographicCamera gameCam;
	public static SpriteBatch sb;
	public static AdManager adm;
	public static I18NBundle myBundle;
	private FileHandle baseFileHandle;
	public SplashScreen ss;
	private ResourceManager rm;
	private Locale tr_TR;
	private Locale en_GB;
	public FrameWork(AdManager adm){
		FrameWork.adm = adm;
	}
	@Override
	public void create() {

		rm = new ResourceManager();
		rm.load();
		ResourceManager.asset.load("gfx/mainsplash.jpg", Texture.class);
		ResourceManager.asset.finishLoadingAsset("gfx/mainsplash.jpg");
		ss = new SplashScreen();


		setScreen(ss);
		
		
		
		
		sb = new SpriteBatch();
		
		/* Camera & ViewPort */
		gameCam = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		thePort = new FitViewport(GameVars.WIDTH, GameVars.HEIGHT+GameVars.padding,gameCam);
		gameCam.setToOrtho(false,thePort.getWorldWidth(),thePort.getWorldHeight());

		Gdx.input.setInputProcessor(new GameInputProcessor());

		//Gdx.input.setCatchBackKey(true);

		/* Localization */
		baseFileHandle = Gdx.files.internal("i18n/MyBundle.properties");

		tr_TR = new Locale("tr", "TR");
		en_GB = new Locale("en", "GB");

		myBundle = I18NBundle.createBundle(Gdx.files.internal("i18n/MyBundle"));
		//myBundle = I18NBundle.createBundle(baseFileHandle, en_GB);
		
	}

	@Override
	public void dispose() {
		/* Call SplashScreen Dispose*/
		
		sb.dispose();
		rm.dispose();
		ss.dispose();
	}

	@Override
	public void pause() {
		/* When The App Pauses */	
		/*if(GameVars.state == GameVars.condition.PLAYING){
			GameVars.state = GameVars.condition.PAUSED;
		}*/
	}

	@Override
	public void resume() {
		/*if(GameVars.state == GameVars.condition.PLAYING){
			GameVars.state = GameVars.condition.PAUSED;
		}*/
		Texture.setAssetManager(ResourceManager.asset);
	}
	@Override
	public void render() {
		Gdx.gl.glClearColor(255, 255, 255, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		/* Projection Matrix */
		sb.setProjectionMatrix(gameCam.combined);
		/* ClearScreen */
		if(!rm.getAsset().update()){
			super.render();
			// System.out.println(rm.getAsset().getProgress() * 100 + "%");
		}
		else super.render();
		
		


		/* Camera Update */
		gameCam.update();
		
		/* GameKeys */
		GameKeys.update();
	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
