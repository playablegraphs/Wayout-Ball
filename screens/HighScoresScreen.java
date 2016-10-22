package com.awayout.game.screens;

import com.awayout.game.FrameWork;
import com.awayout.game.GameVars;
import com.awayout.game.managers.PreferencesSave;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class HighScoresScreen extends AbstractScreen {

	private Stage stage;
	private Table table;
	private String str;
	private Button backButton;
	private PreferencesSave save;
	private Sound sound;
	private String skin;

	public HighScoresScreen(ScreenManager gStateManager) {
		super(gStateManager);
	}
	@Override
	public void init() {
		save = new PreferencesSave();
		stage = new Stage(new StretchViewport(GameVars.WIDTH/3, (GameVars.HEIGHT+GameVars.padding)/3),FrameWork.sb);
		Gdx.input.setInputProcessor(stage);

		skin = "gfx/gui/setsup.json";
		
		save.load();
		sound = Gdx.audio.newSound(Gdx.files.internal("sfx/click.mp3"));

		table = new Table(ResourceManager.asset.get(skin,Skin.class));
		int ratio = 360;
		table.setBounds(stage.getWidth()/2 - ratio/2, stage.getHeight()/2 - ratio/2,
				ratio, ratio);
		table.setBackground("metalPanel_green");
		backButton = new Button(ResourceManager.asset.get(skin,Skin.class),"back");
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sManager.setState(States.MAINMENU);
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
			}
		});
		backButton.setBounds(GameVars.WIDTH/6 - ResourceManager.asset.get(skin,Skin.class).getDrawable("back").getMinWidth()/2, 0, ResourceManager.asset.get(skin,Skin.class).getDrawable("back").getMinWidth()
				, ResourceManager.asset.get(skin,Skin.class).getDrawable("back").getMinHeight());
		

		table.top().padTop(GameVars.WIDTH/9);
		String danone = FrameWork.myBundle.get("danone");
		Label none = new Label(danone, ResourceManager.asset.get(skin,Skin.class),"high");

		if(PreferencesSave.pdatalength > 10) PreferencesSave.pdatalength = 10;

		if(PreferencesSave.pdatalength == 0) table.add(none).row();
		else			
			for (int i = 0; i < PreferencesSave.pdatalength; i++) {
				str = FrameWork.myBundle.format("highscoreshow",save.getName(i),save.getScore(i));
				Label lbl = new Label(str, ResourceManager.asset.get(skin,Skin.class),"high");
				table.add(lbl).row();
			}
		String highscore = FrameWork.myBundle.get("highscore");
		Label label = new Label(highscore,ResourceManager.asset.get(skin,Skin.class));
		label.setBounds(table.getWidth()/2 - label.getWidth()/2,(stage.getHeight()/2 - label.getHeight()/2)+table.getHeight()/3, label.getWidth(), label.getHeight());

		stage.addActor(table);
		stage.addActor(backButton);
		stage.addActor(label);
	}

	@Override
	public void update(float dt) {
		// draws stage
		stage.act(dt);
		stage.draw();
	}

	@Override
	public void draw() {

	}
	@Override
	public void handleInput() {
	}

	@Override
	public void dispose() {
		sound.dispose();
		stage.dispose();
	}
}
