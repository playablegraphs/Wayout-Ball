package com.awayout.game.screens;

import static com.awayout.game.managers.OnScreenController.D;
import static com.awayout.game.managers.OnScreenController.U;
import static com.awayout.game.managers.OnScreenController.R;
import static com.awayout.game.managers.OnScreenController.L;
import com.awayout.game.FrameWork;
import com.awayout.game.GameVars;
import com.awayout.game.b2d.B2dWorld;
import com.awayout.game.b2d.Ball;
import com.awayout.game.managers.PreferencesSave;
import com.awayout.game.managers.ResourceManager;
import com.awayout.game.managers.ScreenManager;
import com.awayout.game.managers.ScreenManager.States;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;

public class PlayScreen extends AbstractScreen{

	private Ball ball;
	private B2dWorld b2dworld;
	public static Vector2 movement = new Vector2(0.0f,0.0f);
	public static int level;
	public float fPx,fPy;
	private HudScreen hud;
	private GlyphLayout layout;
	private BitmapFont font;
	private String keytut,gemtut,switchtut,onehittut,bannedwalls,tuttitle;
	private float x = 0,y = 0;
	public PlayScreen(ScreenManager stateManager) {
		super(stateManager);
	}
	@Override
	public void init() {

		/* Tutorial*/
		if(GameVars.currentLevel == 0){
			InsertTutorial();
			GameVars.tutorials = true;
			GameVars.tutorialpage = 0;
		}
		else if(GameVars.Sound) ResourceManager.asset.get("sfx/maintheme.ogg", Music.class).play();

		/* Ball,b2dworld,hud */
		ball = new Ball();
		b2dworld = new B2dWorld("levels/b"+GameVars.currentLevel+".tmx");
		hud = new HudScreen();

		layout = new GlyphLayout();
		
		ball.defineBall();
		GameVars.state = GameVars.condition.PLAYING;
		GameVars.button = GameVars.buttonswitch.UP;

		/* GameVars */
		GameVars.canresume = true;
		GameVars.retryclicked = false;

		/* Font */
		font = new BitmapFont(Gdx.files.internal("fonts/17bit.fnt"));
		font.setColor(new Color(Color.BLACK));
		if(FrameWork.adm.isConnected())FrameWork.adm.hideBannerAd();
	}
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		if(GameVars.state == GameVars.condition.NOTPLAYING) sManager.setState(States.MAINMENU);

		if(GameVars.state == GameVars.condition.FINISHED){
			if(GameVars.currentLevel == GameVars.MAX_LEVEL){
				GameVars.state = GameVars.condition.GAMEFINISHED;
			}
				
			else{
				GameVars.dead = false;
				GameVars.hpAmount = 83;
				GameVars.currentScore = 0;
				GameVars.levelTime = 120;
				GameVars.retryclicked = false;
				GameVars.once = true;
				GameVars.unlocked = false;
				ball.defineBall();
				Ball.getBodyBall().setLinearVelocity(0, 0);
				GameVars.currentLevel++;				
				sManager.setState(States.PLAY);
			}
		}
		// over
		if (GameVars.state == GameVars.condition.DEAD || GameVars.state == GameVars.condition.PAUSED || GameVars.dead){
			Ball.getBodyBall().setActive(false); 
			Ball.getBodyBall().setLinearVelocity(0, 0);
		}
		if (GameVars.retryclicked){ 
			GameVars.dead = false;
			ball.defineBall();
			GameVars.hpAmount = 83;
			GameVars.currentScore = 0;
			GameVars.levelTime = 120;
			Ball.getBodyBall().setLinearVelocity(0, 0);
			GameVars.retryclicked = false;
			GameVars.once = true;
			GameVars.unlocked = false;
			sManager.setState(States.PLAY);
		}
		if(GameVars.state == GameVars.condition.PLAYING) {
			Ball.getBodyBall().setActive(true);
		}
		handleInput();	

		/* Update hud,world,Ball */
		b2dworld.update(dt);
		ball.update(dt);
		hud.update(dt);
	}
	@Override
	public void draw() {
		/* draws hud,world,Ball */
		b2dworld.draw();
		ball.draw();
		hud.draw();

		/* Tutorial */
		if(GameVars.tutorials && GameVars.currentLevel == 0){
			FrameWork.sb.begin();
			switch(GameVars.tuts){
			case EMPTY:
				layout.setText(font, tuttitle);
				font.draw(FrameWork.sb,layout,GameVars.WIDTH/6 - layout.width/2,GameVars.HEIGHT/6 + layout.height*3.5f);
				break;
			case KEY:
				layout.setText(font, keytut);
				font.draw(FrameWork.sb,layout,GameVars.WIDTH/6 - layout.width/2,GameVars.HEIGHT/6 + layout.height*1.5f);
				break;
			case BANNEDWALLS:
				layout.setText(font, bannedwalls);
				font.draw(FrameWork.sb,layout,GameVars.WIDTH/6 - layout.width/2,GameVars.HEIGHT/6 + layout.height*1.5f);
				break;
			/*case GEMS:
				layout.setText(font, gemtut);
				font.draw(FrameWork.sb,layout,GameVars.WIDTH/6 - layout.width/2,GameVars.HEIGHT/6 + layout.height*1.5f);
				break;*/
			case ONEHIT:
				layout.setText(font, onehittut);
				font.draw(FrameWork.sb,layout,GameVars.WIDTH/6 - layout.width/2,GameVars.HEIGHT/6 + layout.height*1.5f);
				break;
			case SWITCH:
				layout.setText(font, switchtut);
				font.draw(FrameWork.sb,layout,GameVars.WIDTH/6 - layout.width/2,GameVars.HEIGHT/6 + layout.height*1.5f);
				break;
			}
			FrameWork.sb.end();
		}
	}
	@Override
	public void handleInput() {
		float dt = Gdx.graphics.getDeltaTime();
		if(!GameVars.control){
			x =-Gdx.input.getAccelerometerX()*0.4f;	y =-Gdx.input.getAccelerometerY()*0.4f;
		}
		if(GameVars.control){
			y = 0; x = 0;
			if(D) {
				y = -2 ;
				if(y > -3f) y -= dt;
			}
			if(U){
				y = 2 ;
				if(y < 3f)y += dt;
			}
			if(R){
				x = 2;
				if(x < 3f) x += dt;
			}
			if(L){
				x = -2;
				if(x > -3f) x -= dt;
			}
		}

		if(x == 0 && y == 0){
			x = Ball.bodyBall.getLinearVelocity().x;
			y = Ball.bodyBall.getLinearVelocity().y;
			x = x > 0 ? x - dt*10: x + dt*10;
			y = y > 0 ? y - dt*10: y + dt*10;
		}
		if(x > y) Ball.bodyBall.setAngularVelocity(-1f);
		else if (x < y) Ball.bodyBall.setAngularVelocity(1f);
		else if ( x == 0 && y == 0) Ball.bodyBall.setAngularVelocity(0f);
		//Ball.bodyBall.setAngularVelocity(x*y);
		if(GameVars.tutorials && GameVars.currentLevel == 0 || GameVars.state != GameVars.condition.PLAYING){

		}
		else {
			Ball.bodyBall.setLinearVelocity(x, y);
			if(x < 0) x = (float) Math.sqrt(x*x);
			if(y < 0) y = (float) Math.sqrt(y*y);
			if(x > y && !GameVars.colliding) {
				GameVars.currentScore += x *dt / 5;
			}
			else if(y > x && !GameVars.colliding){
				GameVars.currentScore += y *dt / 5;
			}
		}
	}

	public void InsertTutorial(){
		keytut = FrameWork.myBundle.get("keytut");
		gemtut = FrameWork.myBundle.get("gemtut");
		switchtut = FrameWork.myBundle.get("switchtut");
		onehittut = FrameWork.myBundle.get("onehittut");
		bannedwalls = FrameWork.myBundle.get("bannedwalls");
		tuttitle = FrameWork.myBundle.get("tuttitle");	
	}
	@Override
	public void dispose() {
		font.dispose();
		hud.dispose();
		ball.dispose();
		b2dworld.dispose();
	}
}