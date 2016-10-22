package com.awayout.game.managers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;


/**
 * Contains MapObjectss for game resources.
 */
public class ContentManager {
	
	private ObjectMap<String, Texture> textures;
	private ObjectMap<String, Music> music;
	private ObjectMap<String, Sound> sounds;
	private ObjectMap<String, TextureAtlas> atlas;

	
	public ContentManager() {
		textures = new ObjectMap<String, Texture>();
		music = new ObjectMap<String, Music>();
		sounds = new ObjectMap<String, Sound>();
		atlas = new ObjectMap<String , TextureAtlas>();
	}
	
	/***********/
	/* Texture */
	/***********/
	
	public void loadTexture(String path) {
		int slashIndex = path.lastIndexOf('/');
		String key;
		if(slashIndex == -1) {
			key = path.substring(0, path.lastIndexOf('.'));
		}
		else {
			key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
		}
		loadTexture(path, key);
	}
	public void loadTexture(String path, String key) {
		Texture tex = new Texture(Gdx.files.internal(path));
		textures.put(key, tex);
	}
	public Texture getTexture(String key) {
		return textures.get(key);
	}
	public void removeTexture(String key) {
		Texture tex = textures.get(key);
		if(tex != null) {
			textures.remove(key);
			tex.dispose();
		}
	}
	
	/*********/
	/* Music */
	/*********/
	
	public void loadMusic(String path) {
		int slashIndex = path.lastIndexOf('/');
		String key;
		if(slashIndex == -1) {
			key = path.substring(0, path.lastIndexOf('.'));
		}
		else {
			key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
		}
		loadMusic(path, key);
	}
	public void loadMusic(String path, String key) {
		Music m = Gdx.audio.newMusic(Gdx.files.internal(path));
		music.put(key, m);
	}
	public Music getMusic(String key) {
		return music.get(key);
	}
	public void removeMusic(String key) {
		Music m = music.get(key);
		if(m != null) {
			music.remove(key);
			m.dispose();
		}
	}
	
	/*******/
	/* SFX */
	/*******/
	
	public void loadSound(String path) {
		int slashIndex = path.lastIndexOf('/');
		String key;
		if(slashIndex == -1) {
			key = path.substring(0, path.lastIndexOf('.'));
		}
		else {
			key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
		}
		loadSound(path, key);
	}
	public void loadSound(String path, String key) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
		sounds.put(key, sound);
	}
	public Sound getSound(String key) {
		return sounds.get(key);
	}
	public void removeSound(String key) {
		Sound sound = sounds.get(key);
		if(sound != null) {
			sounds.remove(key);
			sound.dispose();
		}
	}
	/****************/
	/* TextureAtlas */
	/****************/
	
	public void loadAtlas(String path) {
		int slashIndex = path.lastIndexOf('/');
		String key;
		if(slashIndex == -1) {
			key = path.substring(0, path.lastIndexOf('.'));
		}
		else {
			key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
		}
		loadAtlas(path, key);
	}
	public void loadAtlas(String path, String key) {
		TextureAtlas tex = new TextureAtlas(Gdx.files.internal(path));
		atlas.put(key, tex);
	}
	public TextureAtlas getAtlas(String key) {
		return atlas.get(key);
	}
	public void removeAtlas(String key) {
		TextureAtlas tex = atlas.get(key);
		if(tex != null) {
			atlas.remove(key);
			tex.dispose();
		}
	}
	/****************/
	/* Skin */
	/****************/
	
	/*public void loadAtlas(String path) {
		int slashIndex = path.lastIndexOf('/');
		String key;
		if(slashIndex == -1) {
			key = path.substring(0, path.lastIndexOf('.'));
		}
		else {
			key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
		}
		loadAtlas(path, key);
	}
	public void loadAtlas(String path, String key) {
		TextureAtlas tex = new TextureAtlas(Gdx.files.internal(path));
		atlas.put(key, tex);
	}
	public TextureAtlas getAtlas(String key) {
		return atlas.get(key);
	}
	public void removeAtlas(String key) {
		TextureAtlas tex = atlas.get(key);
		if(tex != null) {
			atlas.remove(key);
			tex.dispose();
		}
	}*/
	/*********/
	/* other */
	/*********/
	
	public void removeAll() {
		for(Object o : textures.values()) {
			Texture tex = (Texture) o;
			tex.dispose();
		}
		textures.clear();
		for(Object o : music.values()) {
			Music music = (Music) o;
			music.dispose();
		}
		music.clear();
		for(Object o : sounds.values()) {
			Sound sound = (Sound) o;
			sound.dispose();
		}
		sounds.clear();
	}
	
}
