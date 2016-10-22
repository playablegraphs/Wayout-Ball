package com.awayout.game.screens;

import com.awayout.game.FrameWork;
import com.awayout.game.GameVars;
import com.awayout.game.managers.OnScreenController;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class miniOptionsScreen extends OnScreenController{

	private Table optionwindow;
	private Button backButton;
	private Button vibrate;
	private Button sounds;
	private ButtonStyle vibratestyle;
	private ButtonStyle soundstyle;
	private PreferencesSave save;
	private Stage stage;
	private int ratio;
	private String vibra,soun,control;
	private String skin;
	private Skin controlskin;
	private ButtonStyle controllerstyle;
	private Button controller;
	public void init() {

		stage = new Stage(new StretchViewport(GameVars.WIDTH/3, (GameVars.HEIGHT+GameVars.padding)/3),FrameWork.sb);
		Gdx.input.setInputProcessor(stage);

		save = new PreferencesSave();
		skin = "gfx/gui/setsup.json";
		control = FrameWork.myBundle.get("control");
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

		vibra = FrameWork.myBundle.get("vibrate");
		soun = FrameWork.myBundle.get("sound");
		Label con = new Label(control,ResourceManager.asset.get(skin,Skin.class),"black");
		Label vib = new Label(vibra,ResourceManager.asset.get(skin,Skin.class),"black");
		Label sou = new Label(soun,ResourceManager.asset.get(skin,Skin.class),"black");
		optionwindow= new Table(ResourceManager.asset.get(skin,Skin.class));

		ratio = GameVars.WIDTH/6;
		optionwindow.setBounds(stage.getWidth()/2 - ratio/2, stage.getHeight()/2 - ratio/2,
				ratio, ratio);
		Buttoninitiliaze();
		optionwindow.setVisible(true);
		optionwindow.setBackground("metalPanel_green");
		optionwindow.row().padTop(20);
		optionwindow.add(vib).expandX();
		optionwindow.add(vibrate).expandX();
		optionwindow.row();
		optionwindow.add(con).expandX();
		optionwindow.add(controller);
		optionwindow.row();
		optionwindow.add(sou).expandX();
		optionwindow.add(sounds).expandX();
		optionwindow.row();
		Label label = new Label(FrameWork.myBundle.get("options"),ResourceManager.asset.get(skin,Skin.class));
		label.setBounds(stage.getWidth()/2 - label.getWidth()/2,(stage.getHeight()/2 - label.getHeight()/2)+optionwindow.getHeight()/3, label.getWidth(), label.getHeight());

		stage.addActor(optionwindow);
		stage.addActor(backButton);
		stage.addActor(label);
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
	public void update(float dt){
		stage.act(dt);
		stage.draw();
	}
	public void Buttoninitiliaze(){
		backButton = new Button(ResourceManager.asset.get(skin,Skin.class),"back");
		backButton.setBounds(stage.getWidth()/2 - ratio/2+optionwindow.getWidth()/2-40/2, stage.getHeight()/2 - ratio/2, 40, 40);
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();

				save.setOptions(GameVars.Sound, GameVars.Vibrate);
				optionwindow.setVisible(false);
				GameVars.state = GameVars.condition.PAUSED;
				HudScreen.pause.setVisible(true);


			}
		});
		vibrate = new Button(vibratestyle);
		vibrate.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound) ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();

				if(GameVars.Vibrate == true){
					GameVars.Vibrate = false;
					init();
				}
				else if (GameVars.Vibrate == false){
					GameVars.Vibrate = true;
					init();
				}
			}
		});
		sounds = new Button(soundstyle);
		sounds.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameVars.Sound == true){
					GameVars.Sound = false;
					ResourceManager.asset.get("sfx/maintheme.ogg",Music.class).stop();
					init();
				}
				else if (GameVars.Sound == false){
					GameVars.Sound = true;
					if(GameVars.Sound) {
						ResourceManager.asset.get("sfx/maintheme.ogg",Music.class).play();
						ResourceManager.asset.get("sfx/click.mp3",Sound.class).play();
					}
					init();
				}
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
					init();		
					;}
				else if (GameVars.control == false){
					GameVars.control = true;					
					stage.dispose();
					init();
					;}
			}
		});
	}
	public void dispose() {
		if(stage != null){
			stage.dispose();
		}
	}
}
