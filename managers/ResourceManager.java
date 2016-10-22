package com.awayout.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ResourceManager {

	public static AssetManager asset;
	public void load(){		
		asset = new AssetManager();
		asset.load("gfx/bg.png", Texture.class);
		asset.load("gfx/gameground.png", Texture.class);
		asset.load("gfx/mainsplash.jpg", Texture.class);
		asset.load("gfx/sprites/butdown.png", Texture.class);
		asset.load("gfx/sprites/butup.png", Texture.class);
		asset.load("gfx/sprites/circle.png", Texture.class);
		asset.load("gfx/sprites/downarrow.png", Texture.class);
		asset.load("gfx/sprites/fullkey.png", Texture.class);
		asset.load("gfx/sprites/hrope.png", Texture.class);
		asset.load("gfx/sprites/key.png", Texture.class);
		asset.load("gfx/sprites/leftarrow.png", Texture.class);
		asset.load("gfx/sprites/locked.png", Texture.class);
		asset.load("gfx/sprites/rightarrow.png", Texture.class);
		asset.load("gfx/sprites/uparrow.png", Texture.class);
		asset.load("gfx/sprites/vrope.png", Texture.class);
		asset.load("gfx/sprites/watch.png", Texture.class);
		asset.load("gfx/gui/atlas.pack", TextureAtlas.class);
		asset.load("gfx/gui/hpson.pack",TextureAtlas.class);
		asset.load("gfx/gui/setsup.json", Skin.class, new SkinLoader.SkinParameter("gfx/gui/atlas.pack"));
		asset.load("sfx/beep.mp3",Sound.class);
		asset.load("sfx/cgem.mp3",Sound.class);
		asset.load("sfx/click.mp3",Sound.class);
		asset.load("sfx/maintheme.ogg",Music.class);
		asset.load("sfx/switch.mp3",Sound.class);
	}
	public void dispose(){
		asset.dispose();
	}
	public AssetManager getAsset() {
		return asset;
	}
}
