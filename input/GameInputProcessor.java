package com.awayout.game.input;

import com.awayout.game.GameVars;
import com.awayout.game.b2d.B2dWorld;
import com.awayout.game.b2d.Ball;
import com.awayout.game.managers.OnScreenController;
import com.awayout.game.managers.PreferencesSave;
import com.awayout.game.managers.ResourceManager;
import com.awayout.game.managers.ScreenManager;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GameInputProcessor extends InputAdapter {

	private PreferencesSave save = new PreferencesSave();
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.UP){
			GameKeys.setKey(GameKeys.UP, true);
		}
		if(keycode == Keys.DOWN){
			GameKeys.setKey(GameKeys.DOWN, true);
		}
		if(keycode == Keys.RIGHT){
			GameKeys.setKey(GameKeys.RIGHT, true);
		}
		if(keycode == Keys.LEFT){
			GameKeys.setKey(GameKeys.LEFT, true);
		}
		if(keycode == Keys.ENTER){
			GameKeys.setKey(GameKeys.ENTER, true);
		}
		if(keycode == Keys.ESCAPE){
			GameKeys.setKey(GameKeys.ESCAPE, true);
		}
		if(keycode == Keys.SPACE){
			GameKeys.setKey(GameKeys.SPACE, true);
		}
		if(keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT){
			GameKeys.setKey(GameKeys.SHIFT, true);
		}
		if(keycode == Keys.BACK){
			
		}
		return true;
	}
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.UP){
			GameKeys.setKey(GameKeys.UP, false);
		}
		if(keycode == Keys.DOWN){
			GameKeys.setKey(GameKeys.DOWN, false);
		}
		if(keycode == Keys.RIGHT){
			GameKeys.setKey(GameKeys.RIGHT, false);
		}
		if(keycode == Keys.LEFT){
			GameKeys.setKey(GameKeys.LEFT, false);
		}
		if(keycode == Keys.ENTER){
			GameKeys.setKey(GameKeys.ENTER, false);
		}
		if(keycode == Keys.ESCAPE){
			GameKeys.setKey(GameKeys.ESCAPE, false);
		}
		if(keycode == Keys.SPACE){
			GameKeys.setKey(GameKeys.SPACE, false);
		}
		if(keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT){
			GameKeys.setKey(GameKeys.SHIFT, false);
		}
		return true;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//Ball.getBodyBall().setLinearVelocity(screenX/10, screenY/10);
		if(GameVars.tutorials && GameVars.currentLevel == 0){
			GameVars.tutorialpage++;
			if(GameVars.tutorialpage == 1){
				B2dWorld.point.setActive(true);
				GameVars.attached = true;
				GameVars.tuts = GameVars.tutorial.KEY;

			}
			else if (GameVars.tutorialpage == 2){ GameVars.tuts = GameVars.tutorial.BANNEDWALLS;GameVars.attached = true;
			}
			else if ( GameVars.tutorialpage == 3){ GameVars.tuts = GameVars.tutorial.ONEHIT;GameVars.attached = true;
			}
			else if (GameVars.tutorialpage == 4){ GameVars.tuts = GameVars.tutorial.SWITCH;GameVars.attached = true;
			}
			else if (GameVars.tutorialpage == 5) {
				GameVars.tutorials = false;
				GameVars.attached = false;
				GameVars.tuts = GameVars.tutorial.EMPTY;
				if(GameVars.Sound){
						ResourceManager.asset.get("sfx/maintheme.ogg", Music.class).play();
				}
			}
		}
		return super.touchDown(screenX, screenY, pointer, button);
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		OnScreenController.D = false;
		OnScreenController.L = false;
		OnScreenController.R = false;
		OnScreenController.U = false;
		return false;
	};
	@Override
	public boolean keyTyped(char c) {
		switch(c)
		{
		case 'g':
			if(GameVars.Sound)ResourceManager.asset.get("sfx/cgem.mp3", Sound.class).play();
			GameVars.b2ddeletetrigger = true;
			GameVars.currentScore += GameVars.currentLevel * 10;
			save.addGem(++GameVars.gems);
			break;
		case 't':
			GameVars.totalScore += GameVars.currentScore;
			GameVars.state = GameVars.condition.DEAD;
			GameVars.dead = true;
			break;
		case 'k':
			GameVars.unlocked = true;
			if(GameVars.Sound)ResourceManager.asset.get("sfx/beep.mp3", Sound.class).play();
		
			break;
		case 'b':
			if(GameVars.unlocked){
				if(GameVars.currentLevel != 0) save.addLevel(GameVars.currentLevel);
				GameVars.state = GameVars.condition.FINISHED;
				GameVars.totalScore += GameVars.currentScore;
				if(GameVars.currentLevel == 15) GameVars.state = GameVars.condition.GAMEFINISHED; 
			}
			break;
		case 'd':
			if(GameVars.Sound && GameVars.button == GameVars.buttonswitch.UP)
				ResourceManager.asset.get("sfx/switch.mp3", Sound.class).play();
			
			GameVars.button = GameVars.buttonswitch.DOWN;
			
			break;
		}
		return false;
	}
}
