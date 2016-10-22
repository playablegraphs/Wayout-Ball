package com.awayout.game.managers;

import com.awayout.game.FrameWork;
import com.awayout.game.GameVars;
import com.awayout.game.screens.AbstractScreen;
import com.awayout.game.screens.HighScoresScreen;
import com.awayout.game.screens.IntroScreen;
import com.awayout.game.screens.MenuScreen;
import com.awayout.game.screens.OptionsScreen;
import com.awayout.game.screens.PlayScreen;
import com.awayout.game.screens.SelectScreen;
import com.awayout.game.screens.SplashScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;


public class ScreenManager {
	private AbstractScreen actScreen;
	public enum States { MAINMENU,INTRO,SELECT,PLAY,OPTIONS,HIGHSCORES,EXIT }
	public static ContentManager res;
	private static Texture tex;
	private static Texture newtex;
	public static TextureAtlas atlas;
	public static Skin skin;
	public static Sound click;
	public ScreenManager() {
		
		/* Menu Assets  */
		//atlas = new TextureAtlas(Gdx.files.internal("gfx/gui/atlas.pack"));
		//skin = new Skin(Gdx.files.internal("gfx/gui/setsup.json"), atlas);
		//click = rm.asset.get("sfx/click.mp3",Sound.class);
		setState(States.MAINMENU);
		load();
	}
	public void setState(States state){
		switch(state){
		case MAINMENU:
			GameVars.state = GameVars.condition.NOTPLAYING;
			actScreen = new MenuScreen(this);
			break;
		case SELECT:
			actScreen = new SelectScreen(this);
			break;
		case PLAY:
			actScreen = new PlayScreen(this);
			break;
		case INTRO:
			actScreen = new IntroScreen(this);
			break;
		case OPTIONS:
			actScreen = new OptionsScreen(this);
			break;
		case HIGHSCORES:
			actScreen = new HighScoresScreen(this);
			break;
		case EXIT:
			Gdx.app.exit();
			break;
		}
	}
	public void draw(){
		actScreen.draw();
	}
	public void update(float dt){
		if(SplashScreen.isDrew&& GameVars.state != GameVars.condition.NOTPLAYING){
			FrameWork.sb.disableBlending();
			FrameWork.sb.begin();
			FrameWork.sb.draw(newtex, 0, 0,GameVars.WIDTH,GameVars.HEIGHT+GameVars.padding);		
			FrameWork.sb.end();
			FrameWork.sb.enableBlending();
		}
		else if(SplashScreen.isDrew && GameVars.state == GameVars.condition.NOTPLAYING){
			FrameWork.sb.disableBlending();
			FrameWork.sb.begin();
			FrameWork.sb.draw(tex, 0, 0,GameVars.WIDTH,GameVars.HEIGHT+GameVars.padding);
			FrameWork.sb.end();
			FrameWork.sb.enableBlending();
		}
		actScreen.update(dt);
	}
	public void dispose() {
		actScreen.dispose();
		tex.dispose();
		newtex.dispose();
	}
	public static void load(){
		 tex= ResourceManager.asset.get("gfx/bg.png");
		 newtex = ResourceManager.asset.get("gfx/gameground.png");
	}
}
