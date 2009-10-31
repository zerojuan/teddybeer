package com.icecream.factory;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Log;

import com.icecream.util.EAnimType;
import com.icecream.util.EImgType;
import com.icecream.util.EParticleSysType;
import com.icecream.util.ESndType;

/**
 * This class is responsible for handling all the art/sound assets
 * @author Julius
 *
 */
public class AssetFactory {
	
	private Image backgroundImage;
	private Image gameOverImage;
	private Image blockImage;
	private Image alertImage;

	private TiledMap testLevel;
	
	private SpriteSheet playerIdleRight;
	private SpriteSheet playerIdleUp;
	private SpriteSheet playerIdleDown;
	private SpriteSheet playerIdleLeft;
	private SpriteSheet playerBeerUp;
	private SpriteSheet playerBeerDown;
	private SpriteSheet playerBeerLeft;
	private SpriteSheet playerBeerRight;
	private SpriteSheet playerIdleHide;
	
	
	private SpriteSheet enemyDrunk;
	private SpriteSheet enemyAlertLeft;
	private SpriteSheet enemyAlertRight;
	private SpriteSheet enemyShootLeft;
	private SpriteSheet enemyShootRight;
	
	private Sound backGroundSound;
	private Sound blastSound;
	
	private Animation pressX;
	private Animation pressBack;
	
	private Image beerBox;
	
	private static AssetFactory instance;	
	
	private AssetFactory() throws SlickException{
		//Instantiate Images
		gameOverImage = new Image("assets/gameover.png");
		backgroundImage = new Image("assets/background_menu.gif");
		alertImage = new Image("assets/alert_thought.png");
		beerBox = new Image("assets/beerbox.png");
		Image[] pressXAnim = {new Image("assets/pressX1.png"), new Image("assets/pressX2.png")};
		Image[] pressBAnim = {new Image("assets/overspace.png"), new Image("assets/overspace1.png")};
		pressX = new Animation( pressXAnim, 300);
		pressBack = new Animation( pressBAnim, 300);
		
		playerIdleRight = new SpriteSheet(new Image("assets/idle_right.png"), 90/3, 30, 0, 0);
		playerIdleLeft = new SpriteSheet(new Image("assets/idle_left.png"), 90/3, 30, 0, 0);
		playerIdleUp= new SpriteSheet(new Image("assets/idle_up.png"), 90/3, 30, 0, 0);
		playerIdleDown = new SpriteSheet(new Image("assets/idle_down.png"), 90/3, 30, 0, 0);
		playerIdleHide = new SpriteSheet(new Image("assets/idle_hide.png"), 90/3, 30, 0, 0);
		
		playerBeerRight = new SpriteSheet(new Image("assets/beer_right.png"), 90/3, 30, 0, 0);
		playerBeerLeft = new SpriteSheet(new Image("assets/beer_left.png"), 90/3, 30, 0, 0);
		playerBeerUp= new SpriteSheet(new Image("assets/beer_up.png"), 90/3, 30, 0, 0);
		playerBeerDown = new SpriteSheet(new Image("assets/beer_down.png"), 90/3, 30, 0, 0);
		
		enemyDrunk = new SpriteSheet(new Image("assets/hunter_random.png"), 90/3, 30, 0, 0);
		enemyAlertLeft = new SpriteSheet(new Image("assets/hunter_alert.png"), 90/3, 30, 0, 0);
		enemyAlertRight = new SpriteSheet(new Image("assets/hunter_alert_right.png"), 90/3, 30, 0, 0);
		enemyShootLeft = new SpriteSheet(new Image("assets/hunter_shoot.png"), 90/3, 30, 0, 0);
		enemyShootRight = new SpriteSheet(new Image("assets/hunter_shoot_right.png"), 90/3, 30, 0, 0);
		//Instantiate sound
		blastSound = new Sound("assets/blast_sound.wav");
		backGroundSound = new Sound("assets/battleloop.wav");
		//Instantiate particle systems
		
		//Instantiate tiled map
		testLevel = new TiledMap("assets/test_level.tmx");
	}	
	
	public static AssetFactory instance(){
		if(instance == null){
			try{
				instance = new AssetFactory();				
			}catch(SlickException se){
				Log.error(se);
			}
		}
		return instance;
	}
	
	public Image getImage(EImgType imgType){
		switch(imgType){
			case BACKGROUND: return backgroundImage;
			case ALERT: return alertImage;
			case BLOCK: return blockImage;
			case GAME_OVER: return gameOverImage;
			case BEER_BOX: return beerBox;
		}
		return null;
	}
	
	public Animation getAnimation(EAnimType animType){
		switch(animType){
			case PRESS_X: return pressX;
			case PRESS_BACK: return pressBack;
		}
		return null;
	}
	
	public SpriteSheet getSpriteSheet(EAnimType animType){
		switch(animType){			
			case IDLE_DOWN: return playerIdleDown;
			case IDLE_LEFT: return playerIdleLeft;
			case IDLE_UP: return playerIdleUp;
			case IDLE_RIGHT: return playerIdleRight;
			case IDLE_HIDE: return playerIdleHide;
			case BEER_UP: return playerBeerUp;
			case BEER_DOWN: return playerBeerDown;
			case BEER_RIGHT: return playerBeerRight;
			case BEER_LEFT: return playerBeerLeft;
			case ENEMY_DRUNK: return enemyDrunk;
			case ENEMY_ALERT_LEFT: return enemyAlertLeft;
			case ENEMY_ALERT_RIGHT: return enemyAlertRight;
			case ENEMY_SHOOT_LEFT: return enemyShootLeft;
			case ENEMY_SHOOT_RIGHT: return enemyShootRight;			
		}
		return null;
	}
	
	public TiledMap getLevelMap(int level){
		return testLevel;
	}
	
	public Sound getSound(ESndType sndType){
		switch(sndType){
			case GUNSHOT: return blastSound;		
			case BACKGROUND: return backGroundSound;		
		}
		return null;
	}
	
	public ParticleSystem getParticleSystem(EParticleSysType particleSysType){
		return null;
	}
	
}
