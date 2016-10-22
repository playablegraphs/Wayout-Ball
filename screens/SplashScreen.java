package com.awayout.game.screens;

import com.awayout.game.FrameWork;
import com.awayout.game.managers.ResourceManager;
import com.awayout.game.managers.ScreenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class SplashScreen implements Screen {
	private ScreenManager sManager;
	private final float splashTimer;
	private float splashTime;
	public static boolean isDrew;
	private Texture mainsplash;
	private ResourceManager rm;
	
	public SplashScreen(){
		mainsplash = ResourceManager.asset.get("gfx/mainsplash.jpg", Texture.class);
		isDrew = false;
		splashTimer = 1;
		//rm = new ResourceManager();
	}
	
	public void dispose() {
		mainsplash.dispose();
		if(sManager != null) sManager.dispose();
	}
	@Override
	public void show() {
	}
	@Override
	public void render(float dt) {
		splashTime += dt;
		if(splashTimer < splashTime && !isDrew && ResourceManager.asset.update()){
			splashTime = 0;
			sManager = new ScreenManager();
			isDrew = true;
		}
		if(!isDrew){
			//FrameWork.sb.disableBlending();
			FrameWork.sb.begin();
			FrameWork.sb.draw(mainsplash,FrameWork.thePort.getWorldWidth()/2 - mainsplash.getWidth()/2,
					FrameWork.thePort.getWorldHeight()/2 - mainsplash.getHeight()/4
					);
			FrameWork.sb.end();
			//FrameWork.sb.enableBlending();

		}
		if(isDrew && sManager != null && ResourceManager.asset.update())
			sManager.update(dt);
		if(isDrew && sManager != null && ResourceManager.asset.update())
			sManager.draw();
		
		//System.out.print("time: "+splashTime+"timer"+splashTimer+"draw"+isDrew);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {}

	@Override
	public void hide() {
}
}
