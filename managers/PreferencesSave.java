package com.awayout.game.managers;

import com.awayout.game.GameVars;
import com.awayout.game.GameVars.buttonswitch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

public class PreferencesSave {

	/* Datas */
	public Preferences prevdata;

	/* AvailableLevels*/
	public static int KeyLevel=1;

	/* Player Name */
	public static String[] name;

	/* Player Score */
	public static long[] score;

	/* Index */
	private int index = 0;

	/* Pdata ArrayLength */
	public static int pdatalength;

	/* InnerArray */
	public static InnerArray array = new InnerArray();

	/* JSON */
	private Json json = new Json();

	/* Files */
	private FileHandle file = Gdx.files.local("save.json");

	public void swipealldata(){;
		InnerArray newarray = new InnerArray();
		file.writeString(json.toJson(newarray), false);
	}
	public void save(){
		json.setOutputType(OutputType.json);
		file.writeString(json.toJson(array), false);
	}	
	public void load(){
		
		boolean exist = file.exists();
		
		if(!exist) return;
		
		InnerArray values = json.fromJson(InnerArray.class, file);
		
		if(values == null) return;
		
		if(values.pdata != null){
			score = new long[values.pdata.size+1];
			name = new String[values.pdata.size+1];
			pdatalength = values.pdata.size; 
			for(Object obj : values.pdata){
				Preferences newdata = (Preferences) obj;
				score[index] = newdata.score;
				name[index] = newdata.name;
				index++;
			}
			array.pdata = values.pdata ;
			sortHighScore();
		}
		if(values.leveldata != null){
			for(Object obj : values.leveldata){
				Preferences newdata = (Preferences) obj;
				KeyLevel = newdata.skeylevel;
			}
			array.leveldata = values.leveldata ;
		}
		if(values.gems != null){
			for(Object obj : values.gems){
				Preferences newdata = (Preferences) obj;
				GameVars.gems = newdata.gems;
			}
			array.gems = values.gems ;
		}
		if(values.options != null){
			for(Object obj : values.options){
				Preferences optionsdata = (Preferences) obj;
				GameVars.Sound = optionsdata.sound;
				GameVars.Vibrate = optionsdata.vibrate;
			}
			array.options = values.options;
		}
	}
	public static class InnerArray{
		/* Array */
		public Array<Preferences> pdata= new Array<Preferences>();
		public Array<Preferences> leveldata= new Array<Preferences>();
		public Array<Preferences> options = new Array<Preferences>();
		public Array<Preferences> gems = new Array<Preferences>();
	}
	public static class Preferences{

		/* AvailableLevels */
		public int skeylevel;

		/* Player Name */
		public String name;

		/* Player Score */
		public long score;
		
		/* Player Gems */
		public int gems;
		
		/* Key */
		//public boolean key;
		
		/* Switch */
		public buttonswitch switchbut;
		
		/* Options Data */
		public boolean sound = true ;
		public boolean vibrate = true ;
	}

	public Preferences PlayerData(String name,long score){
		Preferences playerdata = new Preferences();
		playerdata.name = name;
		playerdata.score = score;
		return playerdata;
	}
	public Preferences IncreaseAvailableLevel(int keyl){
		Preferences leveldata = new Preferences();
		leveldata.skeylevel = keyl;
		return leveldata;
	}
	public Preferences Options(boolean sound,boolean vibrate){
		Preferences optionsdata = new Preferences();
		optionsdata.sound = sound;
		optionsdata.vibrate = vibrate;
		return optionsdata;
	}
	public Preferences IncreaseGems(int gem){
		Preferences gems = new Preferences();
		gems.gems = gem;
		return gems;
	}
	public void setOptions(boolean sound,boolean vibrate){
		Preferences d3 = Options(sound, vibrate);
		array.options.removeAll(array.options, false);
		array.options.add(d3);
		save();
	}
	public void addScore(String name,long score){
		Preferences d1 = PlayerData(name, score);
		array.pdata.add(d1);
		save();
	}
	public void addLevel(int KeyLevel){
		PreferencesSave.KeyLevel = KeyLevel;
		Preferences d2 = IncreaseAvailableLevel(KeyLevel);
		array.leveldata.removeAll(array.leveldata, false);
		array.leveldata.add(d2);
		/*if(array.leveldata == null || array.leveldata.size == 0){
			array.leveldata.add(d2);
		}
		Preferences last = array.leveldata.peek();
		//Preferences first = array.leveldata.first();
		//Preferences pop = array.leveldata.pop();
		if(KeyLevel > last.AvailableLevels){
			array.leveldata.removeAll(array.leveldata, false);
			array.leveldata.add(d2);
		}*/
		save();
	}
	public void addGem(int gem){
		Preferences d4 = IncreaseGems(gem);
		array.gems.removeAll(array.gems, false);
		array.gems.add(d4);
		save();
	}
	public void sortHighScore(){
		int n = pdatalength;
		long temp = 0;
		String str;

		for (int i = 0; i < n; i++) {
			for (int j = 1; j < (n - i); j++) {

				if (score[j - 1] < score[j]) {
					temp = score[j - 1];
					score[j - 1] = score[j];
					score[j] = temp;
					str = name[j - 1];
					name[j - 1] = name[j];
					name[j] = str;
				}
			}
		}
	}
	public String getName(int i){
		return name[i];
	}
	public long getScore(int i){
		return score[i];
	}
}
