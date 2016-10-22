package com.awayout.game.screens;

import com.awayout.game.managers.ScreenManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public abstract class AbstractScreen {
	protected ScreenManager sManager;
	protected TextureAtlas atlas;


	public AbstractScreen(ScreenManager sManager){
		this.sManager = sManager;
		init();
	}
	public abstract void init();
	public abstract void update(float dt);
	public abstract void draw();
	public abstract void handleInput();
	public abstract void dispose();
}
