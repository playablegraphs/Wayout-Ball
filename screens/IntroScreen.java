package com.awayout.game.screens;

import com.awayout.game.FrameWork;
import com.awayout.game.GameVars;
import com.awayout.game.managers.ResourceManager;
import com.awayout.game.managers.ScreenManager;
import com.awayout.game.managers.ScreenManager.States;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class IntroScreen extends AbstractScreen{
	
	private Stage stage;
	private Table table;
	private Button back;
	private Label introduce;
	private Label introduction;
	private String skin;
	private String instruct;
	public IntroScreen(ScreenManager sManager) {
		super(sManager);
	}

	@Override
	public void init() {
		
		/* Stage&Input */
		stage= new Stage(new StretchViewport(GameVars.WIDTH/3, (GameVars.HEIGHT+GameVars.padding)/3),FrameWork.sb);
		Gdx.input.setInputProcessor(stage);
		
		/* Skin */
		skin = "gfx/gui/setsup.json";
		
		/* Introduce */
		instruct = FrameWork.myBundle.get("instruct");
		introduce = new Label(instruct ,ResourceManager.asset.get(skin,Skin.class),"sansblack");
		introduce.setAlignment(Align.center);
		
		/* BackButton */
		back = new Button(ResourceManager.asset.get(skin,Skin.class),"back");
		back.setBounds(GameVars.WIDTH/6 - back.getWidth()/2, back.getHeight()/2, back.getWidth(), back.getHeight());
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				sManager.setState(States.MAINMENU);
			}
		});
		double[] dbl = {1.1,2.2};
		/* Table */
		table = new Table(ResourceManager.asset.get(skin,Skin.class));
		table.setBackground("metalPanel_green");
		int ratio = GameVars.WIDTH/3;
		table.setBounds(stage.getWidth()/2 - ratio/2, stage.getHeight()/2 - ratio/2,
				ratio, ratio);
		table.add(introduce).pad(15).padTop(60);
		
		introduction = new Label(FrameWork.myBundle.get("intro"),ResourceManager.asset.get(skin,Skin.class));
		introduction.setBounds(table.getWidth()/2 - introduction.getWidth()/2,(stage.getHeight()/2 - introduction.getHeight()/2)+table.getHeight()/3, introduction.getWidth(), introduction.getHeight());
		
		
		
		stage.addActor(table);
		stage.addActor(back);
		stage.addActor(introduction);
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
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
		
	}
}
