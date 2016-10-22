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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class SelectScreen extends AbstractScreen{

	private Stage stage;
	private Table table;
	private Button back;
	private PreferencesSave save;
	private String skin;
	private String atlas;
	public SelectScreen(ScreenManager sManager) {
		super(sManager);
	}
	@Override
	public void init() {
		/* Stage&Input */
		stage = new Stage(new StretchViewport(GameVars.WIDTH/3, (GameVars.HEIGHT+GameVars.padding)/3),FrameWork.sb);
		Gdx.input.setInputProcessor(stage);

		/* Save&Sound */ 
		save = new  PreferencesSave();
		save.load();
		
		skin = "gfx/gui/setsup.json";
		atlas = "gdx/gui/atlas.pack";
		/* Table */
		GameVars.state = GameVars.condition.NOTPLAYING;
		table = new Table(ResourceManager.asset.get(skin,Skin.class));
		int ratio = GameVars.WIDTH/3;
		table.setBounds(stage.getWidth()/2 - ratio/2, stage.getHeight()/2 - ratio/2,
				ratio, ratio);
		table.align(Align.center|Align.top);
		String selectlevel = FrameWork.myBundle.get("selectlevel");
		Label label = new Label(selectlevel,ResourceManager.asset.get(skin,Skin.class),"black");
		label.setBounds(GameVars.WIDTH/2 - label.getWidth()/2, table.getHeight()+(stage.getHeight()/2 - table.getHeight()/2)-label.getHeight()/2, label.getWidth(), label.getHeight());

		//if(PreferencesSave.AvailableLevels == 0) PreferencesSave.AvailableLevels = 1;
		String tuto = FrameWork.myBundle.get("tuttitle");
		TextButton tut = new TextButton(tuto,ResourceManager.asset.get(skin,Skin.class),"default");
		tut.setBounds(GameVars.WIDTH/6 - tut.getWidth()/2, GameVars.HEIGHT*0.25f - tut.getHeight()/2,
				ResourceManager.asset.get(skin,Skin.class).getDrawable("butup").getMinWidth(), ResourceManager.asset.get(skin,Skin.class).getDrawable("butup").getMinHeight());
		tut.setName(Integer.toString(0));
		tut.addListener(click());
		table.padTop(100);
		
		GameVars.tuts = GameVars.tutorial.EMPTY;
		GameVars.tutorialpage = 0;
		
		for (int i = 1; i <= PreferencesSave.KeyLevel; i++) {

				String str = String.format("%d", i);
				TextButton level = new TextButton(str,ResourceManager.asset.get(skin,Skin.class),"available");
				table.add(level).pad(5).expandX().size(40).expandY();
				level.setName(Integer.toString(i));
				level.addListener(click());
			
			if(i%5 == 0) {
				table.row() ;
			};
		}
		if(PreferencesSave.KeyLevel != GameVars.MAX_LEVEL){
			for (int i = PreferencesSave.KeyLevel+1; i <= GameVars.MAX_LEVEL; i++) {
					String str2 = String.format("%d", i);
					TextButton level = new TextButton(str2,ResourceManager.asset.get(skin,Skin.class),"disavailable");
					table.add(level).pad(5).expandX().size(40).expandY();
				
				if(i%5 == 0) table.row();
			}
		}
		back = new Button(ResourceManager.asset.get(skin,Skin.class),"back");
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sManager.setState(States.MAINMENU);
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				if(FrameWork.adm.isConnected())FrameWork.adm.hideBannerAd();
			}
		});
		back.setBounds(GameVars.WIDTH/6 - back.getWidth()/2, 50, ResourceManager.asset.get(skin,Skin.class).getDrawable("back").getMinWidth(), 
				ResourceManager.asset.get(skin,Skin.class).getDrawable("back").getMinHeight());
		stage.addActor(table);
		stage.addActor(back);
		stage.addActor(label);
		stage.addActor(tut);
		if(FrameWork.adm.isConnected())FrameWork.adm.showBannerAd();
	}
	@Override
	public void update(float dt) {
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
		stage.dispose();
	}
	public void setlevel(int i){
		GameVars.currentLevel = i;
		GameVars.hpAmount = 83;
		GameVars.currentScore = 0;
		GameVars.levelTime = 120;
		GameVars.unlocked = false;
		GameVars.button = GameVars.buttonswitch.UP;
		GameVars.once = true;
		GameVars.dead = false;
		sManager.setState(States.PLAY);
	}
	public ClickListener click(){
		ClickListener doit = new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) {
					ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
					if(FrameWork.adm.isConnected())FrameWork.adm.hideBannerAd();
				}
				setlevel(Integer.parseInt(event.getListenerActor().getName()));
			}
		};
		return doit;
	}
}
