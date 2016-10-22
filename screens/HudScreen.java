package com.awayout.game.screens;

import javax.swing.GroupLayout.Alignment;

import com.awayout.game.FrameWork;
import com.awayout.game.GameVars;
import com.awayout.game.b2d.Ball;
import com.awayout.game.input.GameInputProcessor;
import com.awayout.game.managers.DrawString;
import com.awayout.game.managers.OnScreenController;
import com.awayout.game.managers.PreferencesSave;
import com.awayout.game.managers.ResourceManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class HudScreen extends OnScreenController {

	private TextButton resume,retry,options,quit,yes,no;
	private Button pauseclick,retrynew;
	private TextureAtlas atlas;
	private TextureAtlas sayiatlasi;
	private AtlasRegion region;
	private AtlasRegion region2;
	private int x,y;
	private TextureRegion[]  fullhp;
	private TextureRegion[]  decreasinghp;
	private BitmapFont font;
	public Stage stage;
	public static Window pause;
	private Dialog sure;
	private Button okButton;
	private TextField nameField;
	public static String playerName;
	private Table scorewindow;
	public static boolean enterscore;
	private OrthographicCamera hudCam;
	private PreferencesSave save;
	private miniOptionsScreen mos;
	private Label label,xlabel;
	private ImageButton againbutton,okaybutton;
	private Texture key,score;
	private Drawable again,okay;
	private TextureRegion[] sayilar;
	private String skin,yousure;
	private Texture watch,level;
	private DrawString drawString;
	public HudScreen() {

		mos = new miniOptionsScreen();
		save = new PreferencesSave();
		stage = new Stage(new StretchViewport(GameVars.WIDTH/3, (GameVars.HEIGHT+GameVars.padding)/3),FrameWork.sb);
		drawString = new DrawString();
		InputMultiplexer im = new InputMultiplexer(new GameInputProcessor(),stage);
		Gdx.input.setInputProcessor(im);
		yousure = FrameWork.myBundle.get("yousure");

		/* String Skin */
		skin = "gfx/gui/setsup.json";


		/* Font */
		font = new BitmapFont(Gdx.files.internal("fonts/17bit.fnt"));
		font.setColor(new Color(Color.BLACK));

		/* TextureAtlasses */
		atlas = new TextureAtlas(Gdx.files.internal("gfx/gui/hpson.pack"));
		sayiatlasi = new TextureAtlas(Gdx.files.internal("gfx/gui/sayilar.pack"));


		/* TextureRegions */
		region = atlas.findRegion("hpnd");
		region2 = atlas.findRegion("hped");
		fullhp = new TextureRegion[83];
		sayilar = new TextureRegion[10];


		x = region2.getRegionWidth()/83;
		y = region.getRegionWidth()/83;

		for (int i = 0; i < 10; i++) {
			sayilar[i] = new TextureRegion(sayiatlasi.findRegion("hud"+i));
		}

		for (int i = 0; i < fullhp.length; i++) {
			fullhp[i] = new TextureRegion(region,0+(i*y),0,y,region.getRegionHeight());
		}
		decreasinghp = new TextureRegion[83];
		for (int i = 0; i < decreasinghp.length; i++) {
			decreasinghp[i] = new TextureRegion(region2,0+(i*x),0,x,region2.getRegionHeight());
		}
		scorewindow = new Table(ResourceManager.asset.get(skin,Skin.class));
		scorewindow.setVisible(false);
		/* Texture initiliaze */ 
		key = new Texture(Gdx.files.internal("gfx/sprites/fullkey.png"));
		watch = ResourceManager.asset.get("gfx/sprites/watch.png", Texture.class);
		score = new Texture(Gdx.files.internal("gfx/sprites/star.png"));
		level = new Texture(Gdx.files.internal("gfx/sprites/badge.png"));
		// initiliazing buttons
		initiliazeButtons();

		// pause window
		pause = new Window("",ResourceManager.asset.get(skin,Skin.class));
		pause.setVisible(false);
		pause.setSize(200, 200);
		pause.setPosition(stage.getWidth()/2 - pause.getWidth()/2, stage.getHeight()/2 - pause.getHeight()/2);
		pause.add(resume);
		pause.row();
		pause.add(retry);
		pause.row();
		pause.add(options);
		pause.row();
		pause.add(quit);

		// Dialog
		sure = new Dialog(yousure, ResourceManager.asset.get(skin,Skin.class));
		//sure.setSize(100, 100);
		sure.setPosition(stage.getWidth()/2 - sure.getWidth()/2, stage.getHeight()/2 - sure.getHeight()/2);
		sure.padTop(25);
		sure.background("metalPanel_green");
		sure.row();
		sure.setVisible(false);
		sure.add(yes);
		sure.add(no);
		sure.pack();

		// table
		Table table = new Table(ResourceManager.asset.get(skin,Skin.class));
		table.setFillParent(true);
		table.right();
		table.top();
		table.add(pauseclick).top().right();

		OnScreenController osc = new OnScreenController();
		stage.addActor(table);
		stage.addActor(pause);
		stage.addActor(sure);

		if(GameVars.control){
			stage.addActor(left);
			stage.addActor(right);
			stage.addActor(up);
			stage.addActor(down);
		}

		hudCam = new OrthographicCamera();
		FrameWork.thePort = new FitViewport(GameVars.WIDTH, GameVars.HEIGHT+GameVars.padding,hudCam);
		hudCam.setToOrtho(false,FrameWork.thePort.getWorldWidth(),FrameWork.thePort.getWorldHeight());
	}
	public void draw() {
		/*
		// Strings
		scoreStr = FrameWork.myBundle.format("scoreStr",GameVars.currentScore);
		levelStr = FrameWork.myBundle.format("levelStr", GameVars.currentLevel);
		timeStr = FrameWork.myBundle.format("timeStr", GameVars.levelTime);
		gemStr = FrameWork.myBundle.format("gemStr", GameVars.gems);
		// layout setting and drawing
		scorelayout.setText(font, scoreStr);
		levellayout.setText(font, levelStr);
		timelayout.setText(font, timeStr);
		gemlayout.setText(font, gemStr);
		 */

		FrameWork.sb.setProjectionMatrix(hudCam.combined);	
		FrameWork.sb.begin();

		

		// hpBar effect
		for (int i = 0; i < decreasinghp.length; i++) { 
			FrameWork.sb.draw(decreasinghp[i], (GameVars.WIDTH-region.getRegionWidth()*3)/2+ (i * (x*3)) , GameVars.HEIGHT,y*3,region.getRegionHeight()*3);
		}
		for (int i = 0; i < GameVars.hpAmount; i++) { 
			FrameWork.sb.draw(fullhp[i], (GameVars.WIDTH-region.getRegionWidth()*3)/2+ (i * (y*3)) - 1, GameVars.HEIGHT,y*3,region.getRegionHeight()*3);
		}

		FrameWork.sb.draw(level,GameVars.WIDTH/2 - level.getWidth()/2,GameVars.HEIGHT+GameVars.padding - level.getHeight()*0.64f,120,120);

		FrameWork.sb.draw(watch,GameVars.WIDTH*0.15f,GameVars.HEIGHT+GameVars.padding - watch.getHeight()*1.25f,50,100);

		FrameWork.sb.draw(score,GameVars.WIDTH/2 + score.getWidth()/2,GameVars.HEIGHT+GameVars.padding-score.getHeight()*1f,100,100);

		String asd = String.format("%.2f", GameVars.currentScore+GameVars.totalScore);

		drawString.drawString(FrameWork.sb, ""+GameVars.levelTime, GameVars.WIDTH*0.15f + 80, GameVars.HEIGHT+GameVars.padding - watch.getHeight());

		drawString.drawString(FrameWork.sb, asd, GameVars.WIDTH*0.685f, GameVars.HEIGHT+GameVars.padding - watch.getHeight());

		if(GameVars.currentLevel > 9)
			drawString.drawString(FrameWork.sb, ""+GameVars.currentLevel, GameVars.WIDTH/2 - level.getWidth()/2, GameVars.HEIGHT+GameVars.padding - level.getHeight()/2);
		else drawString.drawString(FrameWork.sb, ""+GameVars.currentLevel, GameVars.WIDTH/2 - level.getWidth()*0.39f, GameVars.HEIGHT+GameVars.padding - level.getHeight()/2);

		if(GameVars.unlocked)
			FrameWork.sb.draw(key,GameVars.WIDTH*0.175f - key.getWidth()*4.25f,GameVars.HEIGHT+GameVars.padding - key.getHeight()*2.85f,key.getWidth()*2,key.getHeight()*2);
		//else FrameWork.sb.draw(ekey,GameVars.WIDTH/2 - ekey.getWidth()*4.25f,GameVars.HEIGHT+GameVars.padding - ekey.getHeight()*2.7f,ekey.getWidth()*2,ekey.getHeight()*2);

		FrameWork.sb.end();
		// drawing stage
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());

		if(GameVars.state == GameVars.condition.OPTIONS) mos.update(Gdx.graphics.getDeltaTime());
	}
	public void update(float dt) {

		hudCam.update();
		// gameover
		if(GameVars.state == GameVars.condition.DEAD || GameVars.state == GameVars.condition.GAMEFINISHED){ 
			if(GameVars.once && Ball.statetime > 1f){
				GameVars.once = false;
				if(FrameWork.adm.isConnected())FrameWork.adm.showBannerAd();
				definescorewindow();
			}
			else if(GameVars.currentLevel == 15 && GameVars.once){
				GameVars.once = false;
				if(FrameWork.adm.isConnected())FrameWork.adm.showBannerAd();
				definescorewindow();
			}
		}
		// hpsover
		if( GameVars.hpAmount <= 0){
			GameVars.state = GameVars.condition.DEAD;
			GameVars.dead = true;
		}

		// timesover
		if(GameVars.levelTime <= 0){
			GameVars.state = GameVars.condition.DEAD;
			GameVars.dead = true;
		}

		// TimeUtils
		if(TimeUtils.timeSinceNanos(GameVars.startTime) > 1000000000 &&
				GameVars.state == GameVars.condition.PLAYING
				&& !GameVars.tutorials
				){
			GameVars.levelTime--;
			GameVars.startTime = TimeUtils.nanoTime();
		}

		// Score savior
		if(GameVars.currentScore < 0) GameVars.currentScore = 0;



		if(GameVars.state == GameVars.condition.PAUSED){
			InputMultiplexer im = new InputMultiplexer(new GameInputProcessor(),stage);
			Gdx.input.setInputProcessor(im);
			pause.setVisible(true);
			GameVars.state = GameVars.condition.EMPTY;
		}

		if(!scorewindow.isVisible())
			if(FrameWork.adm.isConnected()) FrameWork.adm.hideBannerAd();
		
		if(!GameVars.control && !GameVars.done){
			right.remove();
			left.remove();
			up.remove();
			down.remove();
			GameVars.done = true;
		}
		else if(GameVars.control && GameVars.done){
			stage.addActor(right);
			stage.addActor(left);
			stage.addActor(up);
			stage.addActor(down);
			GameVars.done = false;
		}

	}
	public void initiliazeButtons(){

		// pauseclick button

		pauseclick = new Button(ResourceManager.asset.get(skin,Skin.class),"pauseclicknew");
		pauseclick.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.tutorials && GameVars.currentLevel == 1){

				}
				else {
					if(!scorewindow.isVisible()){
						if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
						if(pause.isVisible() == false ){ 
							GameVars.state = GameVars.condition.PAUSED;
						}
						else if (pause.isVisible() == true) {
							pause.setVisible(false);
							GameVars.state = GameVars.condition.PLAYING;
						}
					}
				}
			}
		});
		// retry button
		String retr = FrameWork.myBundle.get("retry");
		retry = new TextButton(retr,ResourceManager.asset.get(skin,Skin.class));
		retry.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				pause.setVisible(false);
				GameVars.state = GameVars.condition.PLAYING;
				GameVars.retryclicked = true;
			}
		});
		// options button
		String opt = FrameWork.myBundle.get("options");
		options = new TextButton(opt,ResourceManager.asset.get(skin,Skin.class));
		options.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				pause.setVisible(false);
				mos.init();
				GameVars.state = GameVars.condition.OPTIONS;
			}
		});
		// yes button
		String yesit = FrameWork.myBundle.get("yes");
		yes = new TextButton(yesit,ResourceManager.asset.get(skin,Skin.class),"available");
		yes.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameVars.state = GameVars.condition.NOTPLAYING;
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();

			}
		});

		// no button
		String nos = FrameWork.myBundle.get("no");
		no = new TextButton(nos,ResourceManager.asset.get(skin,Skin.class),"available");
		no.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				pause.setVisible(true);
				sure.setVisible(false);
			}
		});

		// quit button
		String exit = FrameWork.myBundle.get("exit");
		quit = new TextButton(exit,ResourceManager.asset.get(skin,Skin.class));
		quit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				pause.setVisible(false);
				sure.setVisible(true);

				//sure.show(stage);

			}
		});
		// ok button
		String ok = FrameWork.myBundle.get("ok");
		okButton = new TextButton(ok,ResourceManager.asset.get(skin,Skin.class),"available");
		okButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				long newScore = MathUtils.round(GameVars.totalScore);
				playerName = nameField.getText();
				scorewindow.setVisible(false);
				pause.setVisible(true);
				GameVars.state = GameVars.condition.PAUSED;
				GameVars.canresume = false;
				label.setVisible(false);
				save.addScore(playerName, newScore);
				xlabel.setVisible(false);
				retrynew.setVisible(false);

			}});

		// resume button
		String resumer = FrameWork.myBundle.get("resume");
		resume = new TextButton(resumer,ResourceManager.asset.get(skin,Skin.class));
		resume.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				if(GameVars.canresume){
					pause.setVisible(false);
					GameVars.state = GameVars.condition.PLAYING;
				}
			}
		});
		// okay button
		ResourceManager.asset.get(skin,Skin.class).add("okay", new Texture(Gdx.files.internal("gfx/sprites/checkmark.png")));
		okay = ResourceManager.asset.get(skin,Skin.class).getDrawable("okay");
		okaybutton = new ImageButton(okay,okay);

		okaybutton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				long newScore = MathUtils.round(GameVars.totalScore);
				playerName = nameField.getText();
				if(playerName == null) playerName = "Player";
				scorewindow.setVisible(false);
				pause.setVisible(true);
				GameVars.state = GameVars.condition.PAUSED;
				GameVars.canresume = false;
				label.setVisible(false);
				if(newScore > 0)save.addScore(playerName, newScore);
				xlabel.setVisible(false);
				retrynew.setVisible(false);
			}
		});
		// again button
		ResourceManager.asset.get(skin,Skin.class).add("again", new Texture(Gdx.files.internal("gfx/sprites/return.png")));
		again = ResourceManager.asset.get(skin,Skin.class).getDrawable("again");
		againbutton = new ImageButton(again,again);
		againbutton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				long newScore = MathUtils.round(GameVars.totalScore);
				playerName = nameField.getText();
				if(playerName == null) playerName = "Player";

				scorewindow.setVisible(false);
				pause.setVisible(true);
				GameVars.state = GameVars.condition.PAUSED;
				GameVars.canresume = false;
				label.setVisible(false);
				if(newScore > 0)save.addScore(playerName, newScore);
				xlabel.setVisible(false);
				retrynew.setVisible(false);
				pause.setVisible(false);
				GameVars.state = GameVars.condition.PLAYING;
				GameVars.retryclicked = true;
			}
		});
	}
	public void definescorewindow(){

		xlabel = new Label("= 2 x",ResourceManager.asset.get(skin,Skin.class),"black");
		scorewindow.setVisible(true);
		String str = FrameWork.myBundle.format("str",Math.round(GameVars.currentScore+GameVars.totalScore));
		GameVars.state = GameVars.condition.EMPTY;
		// Retry Button
		retrynew = new Button(ResourceManager.asset.get(skin,Skin.class), "newretry");
		retrynew.setBounds(GameVars.WIDTH/2 - retrynew.getWidth()*2.5f+25, GameVars.HEIGHT/2 - retrynew.getHeight()*2.5f, retrynew.getWidth()
				, retrynew.getHeight());
		retrynew.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				if(GameVars.gems >= 2){
					GameVars.gems -= 2;
					GameVars.usedgem = true;
					xlabel.setVisible(false);
					GameVars.retryclicked = true;
					retrynew.setVisible(false);
				}
			}	
		});
		String nameof = FrameWork.myBundle.get("name");
		String scoreof = FrameWork.myBundle.get("score");
		Label strname = new Label(nameof,ResourceManager.asset.get(skin,Skin.class),"17bit");
		Label totalscore = new Label(str, ResourceManager.asset.get(skin,Skin.class),"17bit");
		Label Score = new Label(scoreof,ResourceManager.asset.get(skin,Skin.class),"17bit");

		xlabel.setBounds(GameVars.WIDTH/2 - xlabel.getWidth()+25, GameVars.HEIGHT/2 - xlabel.getHeight()*3.5f -5, xlabel.getWidth(), xlabel.getHeight());
		nameField = new TextField("", ResourceManager.asset.get(skin,Skin.class));
		nameField.setMessageText("----");
		nameField.setAlignment(Align.center);
		/*nameField.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});*/
		// we will add this Gdx.input.setOnscreenKeyboardVisible(true);

		//int ratio = GameVars.WIDTH/6;
		scorewindow.setBackground("metalPanel_green");
		scorewindow.row();
		scorewindow.add(strname).align(Align.right).width(75);
		scorewindow.add(nameField).width(75);
		scorewindow.row();
		scorewindow.padTop(35);
		scorewindow.add(Score).left();
		scorewindow.add(totalscore).align(Align.center);
		scorewindow.row();
		scorewindow.add(okaybutton).right();
		scorewindow.add(againbutton).left();
		String gameover = FrameWork.myBundle.get("gameover");

		//scorewindow.pack();
		scorewindow.setSize(160, 200);
		scorewindow.setPosition(stage.getWidth()/2 -scorewindow.getWidth()/2, stage.getHeight()/2 - scorewindow.getHeight()/2);

		stage.addActor(scorewindow);

		label = new Label(gameover,ResourceManager.asset.get(skin,Skin.class));
		label.setPosition(GameVars.WIDTH/6 - label.getWidth()/2, GameVars.HEIGHT/6 - label.getHeight()/2 + scorewindow.getHeight()/2);
		label.setVisible(true);

		stage.addActor(label);
		//stage.addActor(okButton);
		if(GameVars.currentLevel != 1 && GameVars.currentLevel != 5 && GameVars.currentLevel != 10 && GameVars.currentLevel != 15 && GameVars.currentLevel != 0){
			stage.addActor(xlabel);
			stage.addActor(retrynew);
		}


	}
	public void dispose() {
		if(key != null){
			atlas.dispose();
			font.dispose();
			key.dispose();
			mos.dispose();
			region.getTexture().dispose();
			region2.getTexture().dispose();
			sayiatlasi.dispose();
			stage.dispose();
		}
	}
}
