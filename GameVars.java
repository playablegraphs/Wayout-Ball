package com.awayout.game;

public class GameVars {
	
	/* Integers */
	public static final int SCALE = 2;
	public static final int HEIGHT = 1740;
	public static final int WIDTH = 1080;
	public static final int padding = 180;
	public static int levelTime = 120;
	public static int currentLevel;
	public static int tutorialpage=0;
	public static int MAX_LEVEL = 15;
	public static int gems = 0;
	public static int touched;
	
	/* Floats */
	public static final float PPM = 100f;
	public static float hpAmount = 83;
	public static float currentScore = 0;
	public static float totalScore = 0;
	public static float x,y;

	/* Booleans */
	public static boolean b2ddeletetrigger = false;
	public static boolean unlocked;
	public static boolean once;
	public static boolean done;
	public static boolean Vibrate = true;
	public static boolean Sound = true;
	public static boolean teleports = false;
	public static boolean tutorials = false;
	public static boolean attached = false;
	public static boolean canresume;
	public static boolean retryclicked;
	public static boolean finished;
	public static boolean colliding;
	public static boolean usedgem;
	public static boolean dead;
	public static boolean collideright;
	public static boolean collideleft;
	public static boolean collidetop;
	public static boolean collidebottom;
	public static boolean control = true ;
	
	/* Mixed */
	public static final String TITLE = "WayOut";
	public static long startTime = 0;

	/* Enums */
	public static enum tutorial{KEY,SWITCH,ONEHIT,BANNEDWALLS,EMPTY }
	public static enum buttonswitch{DOWN,UP	}
	public static enum condition{PLAYING,PAUSED,FINISHED,DEAD,NOTPLAYING,EMPTY,OPTIONS,GAMEFINISHED	}
	public static enum teleportpoint { point1,point2,none}
	
	/* Enum Data Keepers */
	public static teleportpoint point = teleportpoint.none;
	public static buttonswitch button = buttonswitch.UP;
	public static condition state = condition.NOTPLAYING;
	public static tutorial tuts = tutorial.EMPTY;

}
