package com.awayout.game.managers;

import com.awayout.game.GameVars;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawString {
	
	
	public TextureRegion[] numbersregion;
	public Texture cross;
	
	public char c;
	private int a = 0;
	
	public DrawString(){
		//numbers = new char[10];
		numbersregion = new TextureRegion[10];
		for (int i = 0; i < 10; i++) {
			numbersregion[i] = new TextureRegion(new Texture(Gdx.files.internal("gfx/numbers/"+i+".png")));
		}
		cross = new Texture(Gdx.files.internal("gfx/numbers/x.png"));
		
	}
	public void drawString(SpriteBatch sb,String numbers,float x,float y){
		for (a = 0; a < numbers.length(); a++) {
			 char c = numbers.charAt(a);
			 if(c == ',') c = ' ';
				if(c >= '0' && c <= '9') c -= '0';
				else continue;
			sb.draw(numbersregion[c], x+cross.getWidth()/2 + a*40, y,numbersregion[c].getRegionWidth()*1.50f,numbersregion[c].getRegionHeight()*1.50f);
			if(!numbers.equals(Integer.toString(GameVars.currentLevel)))
			sb.draw(cross,x - cross.getWidth()*1.25f,y,cross.getWidth()*1.50f,cross.getHeight()*1.50f);
		}
	}
}
