package com.awayout.game.screens;

import com.awayout.game.FrameWork;
import com.awayout.game.GameVars;
import com.awayout.game.managers.PreferencesSave;
import com.awayout.game.managers.ResourceManager;
import com.awayout.game.managers.ScreenManager;
import com.awayout.game.managers.ScreenManager.States;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class MenuScreen extends AbstractScreen{
	
	private Stage stage;
	private Table table;
	private TextButton playButton;
	private TextButton optionsButton;
	private TextButton exitButton;
	private TextButton scoreButton;
	private TextButton introButton;
	private PreferencesSave save;
	private String intro,play,options,exit,highscore;
	private String skin;
	
	/* Music */
	public static Music music;
	
	public MenuScreen(ScreenManager stateManager) {
		super(stateManager);
	}
	@Override
	public void init() {
		
		/* Stage and Input */
		stage = new Stage(new StretchViewport(GameVars.WIDTH/3, (GameVars.HEIGHT+GameVars.padding)/3),FrameWork.sb);
		Gdx.input.setInputProcessor(stage);
		
		/* SaveStuff*/
		save = new PreferencesSave();
		save.load();
		
		skin = "gfx/gui/setsup.json";
		
		/* Table */
		table = new Table(ResourceManager.asset.get(skin,Skin.class));
		int ratio = GameVars.WIDTH;
		table.setBounds(stage.getWidth()/2 - ratio/2, stage.getHeight()/2 - ratio/2,
				ratio, ratio);
		initiliazeButtons();
		table.add(introButton).padTop(15);
		table.row();
		table.add(playButton).padTop(15);
		table.row();
		table.add(scoreButton).padTop(15);
		table.row();
		table.add(optionsButton).padTop(15);
		table.row();
		table.add(exitButton).padTop(15);
		
		
		stage.addActor(table);
		
		/* Settings state */
		GameVars.state = GameVars.condition.NOTPLAYING;
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		handleInput();

		stage.act(dt);
		stage.draw();
	
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		}
	public void initiliazeButtons(){
		
		exit = FrameWork.myBundle.get("exit");
		play = FrameWork.myBundle.get("play");
		highscore = FrameWork.myBundle.get("highscore");
		intro = FrameWork.myBundle.get("intro");
		options = FrameWork.myBundle.get("options");
		
		introButton = new TextButton(intro,ResourceManager.asset.get(skin,Skin.class));
		introButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				
				sManager.setState(States.INTRO);
			}
		});
		playButton = new TextButton(play,ResourceManager.asset.get(skin,Skin.class));
		playButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				
				sManager.setState(States.SELECT);
			}
		});
		optionsButton = new TextButton(options,ResourceManager.asset.get(skin,Skin.class));
		optionsButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				sManager.setState(States.OPTIONS);
			}
		});
		exitButton = new TextButton(exit,ResourceManager.asset.get(skin,Skin.class));
		exitButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				sManager.setState(States.EXIT);
			}
		});
		scoreButton = new TextButton(highscore,ResourceManager.asset.get(skin,Skin.class));
		scoreButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				sManager.setState(States.HIGHSCORES);
			}
		});
	}
}
