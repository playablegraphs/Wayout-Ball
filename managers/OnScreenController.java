package com.awayout.game.managers;

import com.awayout.game.GameVars;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OnScreenController{
	
	public static boolean R,L,U,D;
	public Button right,left,up,down;
	private Skin skin;
	public OnScreenController(){
		
		skin = new Skin();
		skin.add("left", new Texture(Gdx.files.internal("gfx/control/left.png")));
		skin.add("right", new Texture(Gdx.files.internal("gfx/control/right.png")));
		skin.add("top", new Texture(Gdx.files.internal("gfx/control/top.png")));
		skin.add("down", new Texture(Gdx.files.internal("gfx/control/down.png")));
		
		left = new Button(style("left"));	
		left.setBounds(GameVars.WIDTH/3 - skin.getDrawable("left").getMinWidth() *1.85f				
				, skin.getDrawable("left").getMinHeight()*0.6f
				, skin.getDrawable("left").getMinWidth()*0.75f, skin.getDrawable("left").getMinHeight()*0.75f);
		left.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				L = true;
				return false;
			}		
		});
		right = new Button(style("right"));
		right.setBounds(GameVars.WIDTH/3 - skin.getDrawable("right").getMinWidth()*0.75f
				, skin.getDrawable("left").getMinHeight()*0.6f
				, skin.getDrawable("right").getMinWidth()*0.75f, skin.getDrawable("right").getMinHeight()*0.75f);
		right.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				R = true;
				return false;
			}
		});
		up = new Button(style("top"));
		up.setBounds(GameVars.WIDTH/3 - skin.getDrawable("top").getMinWidth()*1.30f
				, skin.getDrawable("top").getMinHeight()*1.20f
				, skin.getDrawable("top").getMinWidth()*0.75f, skin.getDrawable("top").getMinHeight()*0.75f);
		up.addListener(new ClickListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		    	U = true;	
		    	return false;
		    };
		});
		down = new Button(style("down"));
		down.setBounds(GameVars.WIDTH/3 - skin.getDrawable("down").getMinWidth()*1.30f
				, 0, skin.getDrawable("down").getMinWidth()*0.75f, skin.getDrawable("down").getMinHeight()*0.75f);
		down.addListener(new ClickListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				D = true;
				return false;
			};
		});
	}
	public Button.ButtonStyle style(String direction){
		ButtonStyle style = new ButtonStyle();
		style.up = skin.getDrawable(direction);
		return style;
	}
}
