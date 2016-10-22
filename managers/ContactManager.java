package com.awayout.game.managers;

import com.awayout.game.GameVars;
import com.awayout.game.b2d.B2dvars;
import com.awayout.game.b2d.Ball;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;



public class ContactManager implements ContactListener{

	private PreferencesSave save = new PreferencesSave();

	@Override // called when two fixtures start to collide
	public void beginContact(Contact contact) {
		Fixture fa  = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();


		if(fa.getUserData() != null && fa.getUserData().equals(B2dvars.BIT_FINISHPOINT)){
			if(GameVars.unlocked){
				if(GameVars.currentLevel != 0) save.addLevel(GameVars.currentLevel);
				GameVars.state = GameVars.condition.FINISHED;
				GameVars.totalScore += GameVars.currentScore;
				if(GameVars.currentLevel == 15) GameVars.state = GameVars.condition.GAMEFINISHED; 
			}
		}
		if(fb.getUserData() != null && fb.getUserData().equals(B2dvars.BIT_FINISHPOINT)){
			if(GameVars.unlocked){
				if(GameVars.currentLevel != 0) save.addLevel(GameVars.currentLevel);
				GameVars.state = GameVars.condition.FINISHED;
				GameVars.totalScore += GameVars.currentScore;
				if(GameVars.currentLevel == 15) GameVars.state = GameVars.condition.GAMEFINISHED; 
			}
		}
		if(fa.getUserData() != null && fa.getUserData().equals(B2dvars.BIT_BWALLS)){
			GameVars.touched = 1;
			if(GameVars.Vibrate) Gdx.input.vibrate(200);
		//	GameVars.colliding = true;
		}
		if(fb.getUserData() != null && fb.getUserData().equals(B2dvars.BIT_BWALLS)){
			GameVars.touched = 1;
			if(GameVars.Vibrate) Gdx.input.vibrate(200);
		//	GameVars.colliding = true;
		}
		if(fa.getUserData() != null && fa.getUserData().equals(B2dvars.BIT_WALLS)){
		//	GameVars.colliding = true;
		}
		if(fb.getUserData() != null && fb.getUserData().equals(B2dvars.BIT_WALLS)){
		//	GameVars.colliding = true;

		}
		if(fa.getUserData() != null && fa.getUserData().equals(B2dvars.BIT_ONEHIT)){
			GameVars.totalScore += GameVars.currentScore;
			GameVars.state = GameVars.condition.DEAD;
			GameVars.dead = true;
		}
		if(fb.getUserData() != null && fb.getUserData().equals(B2dvars.BIT_ONEHIT)){
			GameVars.totalScore += GameVars.currentScore;
			GameVars.state = GameVars.condition.DEAD;
			GameVars.dead = true;
			
		}
		if(fa.getUserData() != null && fa.getUserData().equals(B2dvars.BIT_GEM)){
			fa.getBody().setUserData("DEAD");
			if(GameVars.Sound)ResourceManager.asset.get("sfx/cgem.mp3", Sound.class).play();
			GameVars.b2ddeletetrigger = true;
			GameVars.currentScore += GameVars.currentLevel * 10;
			save.addGem(++GameVars.gems);
		}
		if(fb.getUserData() != null && fb.getUserData().equals(B2dvars.BIT_GEM)){
			fb.getBody().setUserData("DEAD");
			if(GameVars.Sound)ResourceManager.asset.get("sfx/cgem.mp3", Sound.class).play();
			GameVars.b2ddeletetrigger = true;
			GameVars.currentScore += GameVars.currentLevel * 10;
			save.addGem(++GameVars.gems);
		}
		if(fa.getUserData() != null && fa.getUserData().equals(B2dvars.BIT_KEY)){
			fa.getBody().setUserData("DEAD");
			GameVars.b2ddeletetrigger = true;
			GameVars.unlocked = true;
			if(GameVars.Sound)ResourceManager.asset.get("sfx/beep.mp3", Sound.class).play();
		}
		if(fb.getUserData() != null && fb.getUserData().equals(B2dvars.BIT_KEY)){
			fb.getBody().setUserData("DEAD");
			GameVars.b2ddeletetrigger = true;
			GameVars.unlocked = true;
			if(GameVars.Sound)ResourceManager.asset.get("sfx/beep.mp3", Sound.class).play();
		}
		if(fa.getUserData() != null && fa.getUserData().equals(B2dvars.BIT_TELEPORT)){
			GameVars.point = GameVars.teleportpoint.point2;
			Ball.bodyBall.setLinearVelocity(0, 0);
		}
		if(fb.getUserData() != null && fb.getUserData().equals(B2dvars.BIT_TELEPORT)){
			GameVars.point = GameVars.teleportpoint.point2;
			Ball.bodyBall.setLinearVelocity(0, 0);
		}
		if(fa.getUserData() != null && fa.getUserData().equals("down")){
			if(GameVars.Sound && GameVars.button == GameVars.buttonswitch.UP)
				ResourceManager.asset.get("sfx/switch.mp3", Sound.class).play();
			
			GameVars.button = GameVars.buttonswitch.DOWN;
			GameVars.b2ddeletetrigger = true;

		}
		if(fb.getUserData() != null && fb.getUserData().equals("down")){
			if(GameVars.Sound && GameVars.button == GameVars.buttonswitch.UP)
				ResourceManager.asset.get("sfx/switch.mp3", Sound.class).play();
			
			GameVars.button = GameVars.buttonswitch.DOWN;
			GameVars.b2ddeletetrigger = true;

		}
		if(fa.getUserData() != null && fa.getUserData().equals(B2dvars.BIT_BALL)){
			GameVars.colliding = true;
		}
		if(fb.getUserData() != null && fb.getUserData().equals(B2dvars.BIT_BALL)){
			GameVars.colliding = true;
		}
		
	}
	@Override // called when two fixture no longer collide
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fa  = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		if(fa.getUserData() != null && fa.getUserData().equals(B2dvars.BIT_BWALLS)){
			GameVars.touched = 0;
			//GameVars.colliding = true;
		}
		if(fb.getUserData() != null && fb.getUserData().equals(B2dvars.BIT_BWALLS)){
			GameVars.touched = 0;
		//	GameVars.colliding = true;
		}
		if(fa.getUserData() != null && fa.getUserData().equals(B2dvars.BIT_WALLS)){
			//GameVars.colliding = false;
		}
		if(fb.getUserData() != null && fb.getUserData().equals(B2dvars.BIT_WALLS)){
		//	GameVars.colliding = false;
		}
		if(fa.getUserData() != null && fa.getUserData().equals(B2dvars.BIT_BALL)){
			GameVars.colliding = false;
		}
		if(fb.getUserData() != null && fb.getUserData().equals(B2dvars.BIT_BALL)){
			GameVars.colliding = false;
		}

	}

	// collision detection
	// preSolve
	// collision handling
	// postSolve
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
