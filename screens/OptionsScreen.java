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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class OptionsScreen extends AbstractScreen{

	private Stage stage;
	//private Skin ResourceManager.asset.get(skin,Skin.class);
	private Table table;
	private Button backButton;
	private Button vibrate;
	private Button sounds;
	private Button controller;
	private ButtonStyle vibratestyle;
	private ButtonStyle soundstyle;
	private ButtonStyle controllerstyle;
	private PreferencesSave save;
	private Button resetdata;
	private Button yes,no;
	private Dialog dialog;
	private String vibra,soun,reset,skin,options,control;
	private String yst,nst,yousure;
	private Skin controlskin;
	//private Sound sound;
	public OptionsScreen(ScreenManager sManager) {
		super(sManager);
	}

	@Override
	public void init() {

		/* Language */
		vibra = FrameWork.myBundle.get("vibrate");
		soun = FrameWork.myBundle.get("sound");
		reset = FrameWork.myBundle.get("resetdata");
		options = FrameWork.myBundle.get("options");
		yst = FrameWork.myBundle.get("ok");
		nst = FrameWork.myBundle.get("no");
		yousure = FrameWork.myBundle.get("yousure");
		control = FrameWork.myBundle.get("control");

		/* Stage&Input */
		stage= new Stage(new StretchViewport(GameVars.WIDTH/3, (GameVars.HEIGHT+GameVars.padding)/3),FrameWork.sb);
		Gdx.input.setInputProcessor(stage);

		/* Save&Sound */
		save = new PreferencesSave();
		skin = "gfx/gui/setsup.json";
		if(GameVars.Vibrate == true){
			vibratestyle = checkbutton();
		}
		else if (GameVars.Vibrate == false){ 
			vibratestyle = uncheckbutton();

		}
		if(GameVars.Sound == true){
			soundstyle = checkbutton();

		}
		
		else if (GameVars.Sound == false){ 
			soundstyle = uncheckbutton();

		}
		if(GameVars.control == true){
			controllerstyle = analog();
		
		}
		else if (GameVars.control == false) controllerstyle = accel();

		/* Buttons */
		Buttoninitiliaze();

		/* Table */
		table = new Table(ResourceManager.asset.get(skin,Skin.class));
		int ratio = GameVars.WIDTH/3;

		Label con = new Label(control,ResourceManager.asset.get(skin,Skin.class),"black");
		Label vib = new Label(vibra,ResourceManager.asset.get(skin,Skin.class),"black");
		Label sou = new Label(soun,ResourceManager.asset.get(skin,Skin.class),"black");
		Label res = new Label(reset,ResourceManager.asset.get(skin,Skin.class),"black");
		table.setBounds(stage.getWidth()/2 - ratio/2, stage.getHeight()/2 - ratio/2,
				ratio, ratio);
		table.setBackground("metalPanel_green");
		table.row().padTop(50);
		table.add(vib).expandX();
		table.add(vibrate).expandX();
		table.row().padTop(25);
		table.add(sou).expandX();
		table.add(sounds).expandX();
		table.row().padTop(25);
		table.add(con).expandX();
		table.add(controller);
		table.row().padTop(25);
		table.add(res).expandX();
		table.add(resetdata).expandX();

		Label label = new Label(options,ResourceManager.asset.get(skin,Skin.class));
		label.setBounds(table.getWidth()/2 - label.getWidth()/2,(stage.getHeight()/2 - label.getHeight()/2)+table.getHeight()/3, label.getWidth(), label.getHeight());

		stage.addActor(backButton);
		stage.addActor(table);
		stage.addActor(label);
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
	public Button.ButtonStyle checkbutton(){
		Button.ButtonStyle style = new Button.ButtonStyle
				(ResourceManager.asset.get(skin,Skin.class).getDrawable("green_boxCheckmark"), ResourceManager.asset.get(skin,Skin.class).getDrawable("green_boxCheckmark"), ResourceManager.asset.get(skin,Skin.class).getDrawable("green_boxCheckmark"));

		return style;
	}
	public Button.ButtonStyle uncheckbutton(){
		Button.ButtonStyle style = new Button.ButtonStyle
				(ResourceManager.asset.get(skin,Skin.class).getDrawable("green_boxCross"), ResourceManager.asset.get(skin,Skin.class).getDrawable("green_boxCross"), ResourceManager.asset.get(skin,Skin.class).getDrawable("green_boxCross"));

		return style;
	}
	public Button.ButtonStyle analog(){
		controlskin = new Skin();
		controlskin.add("gamepadcheck", new Texture("gfx/gui/gamepadcheck.png"));
		ButtonStyle touchpadStyle = new ButtonStyle();
		Drawable touchBackground = controlskin.getDrawable("gamepadcheck");
		touchpadStyle.down = touchBackground;
		touchpadStyle.up = touchBackground;
		return touchpadStyle;
	}
	public Button.ButtonStyle accel(){
		controlskin = new Skin();
		controlskin.add("accelcheck", new Texture("gfx/gui/accelcheck.png"));
		ButtonStyle touchpadStyle = new ButtonStyle();
		Drawable touchBackground = controlskin.getDrawable("accelcheck");
		touchpadStyle.up = touchBackground;
		touchpadStyle.down = touchBackground;
		return touchpadStyle;
	}
	public void Buttoninitiliaze(){
		backButton = new Button(ResourceManager.asset.get(skin,Skin.class),"back");
		backButton.setBounds(GameVars.WIDTH/6 - ResourceManager.asset.get(skin,Skin.class).getDrawable("back").getMinWidth()/2, 0, ResourceManager.asset.get(skin,Skin.class).getDrawable("back").getMinWidth()
				, ResourceManager.asset.get(skin,Skin.class).getDrawable("back").getMinHeight());
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();

				save.setOptions(GameVars.Sound, GameVars.Vibrate);
				sManager.setState(States.MAINMENU);
			}
		});
		vibrate = new Button(vibratestyle);
		vibrate.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();

				if(GameVars.Vibrate == true){
					GameVars.Vibrate = false;
					stage.dispose();
					sManager.setState(States.OPTIONS);
					;}
				else if (GameVars.Vibrate == false){
					GameVars.Vibrate = true;
					stage.dispose();
					sManager.setState(States.OPTIONS);
					;}
			}
		});
		sounds = new Button(soundstyle);
		sounds.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

				if(GameVars.Sound == true){
					GameVars.Sound = false;
					stage.dispose();
					ResourceManager.asset.get("sfx/maintheme.ogg",Music.class).stop();
					sManager.setState(States.OPTIONS)
					;}
				else if (GameVars.Sound == false){
					GameVars.Sound = true;
					ResourceManager.asset.get("sfx/maintheme.ogg",Music.class).play();
					ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
					stage.dispose();
					sManager.setState(States.OPTIONS)
					;}
			}
		});
		// yes button
		yes = new TextButton(yst,ResourceManager.asset.get(skin,Skin.class),"available");
		yes.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				dialog.setVisible(false);
				save.swipealldata();
				GameVars.currentLevel = 0;
				GameVars.gems = 0;
				PreferencesSave.KeyLevel = 1;
				sManager.setState(States.MAINMENU);
			}
		});
		// no button
		no = new TextButton(nst,ResourceManager.asset.get(skin,Skin.class),"available");
		no.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				dialog.setVisible(false);
			}
		});
		resetdata = new Button(ResourceManager.asset.get(skin,Skin.class),"reset");
		resetdata.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				dialog = new Dialog(yousure, ResourceManager.asset.get(skin,Skin.class),"high");
				dialog.padTop(25);
				dialog.padLeft(15);
				dialog.setBackground("metalPanel_green");
				dialog.setBounds(GameVars.WIDTH/2, (GameVars.HEIGHT+GameVars.padding)/2, GameVars.WIDTH/4, GameVars.HEIGHT/4);
				dialog.add(yes).width(100);
				dialog.add(no).width(100);
				dialog.setVisible(true);
				dialog.show(stage);
			}
		});
		controller = new Button(controllerstyle);
		controller.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
				if(GameVars.control == true){
					GameVars.control = false;
					stage.dispose();
					sManager.setState(States.OPTIONS)
					;}
				else if (GameVars.control == false){
					GameVars.control = true;					
					stage.dispose();
					sManager.setState(States.OPTIONS)
					;}
			}
		});
	}
}