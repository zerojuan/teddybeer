package com.icecream.factory;

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
	private Image playerImage;
	private Image blockImage;

	private TiledMap testLevel;
	
	private SpriteSheet playerRunning;
	
	private static AssetFactory instance;	
	
	private AssetFactory() throws SlickException{
		//Instantiate Images
		backgroundImage = new Image("assets/background.png");
		playerImage = new Image("assets/playeranim.png");	
		playerRunning = new SpriteSheet(
					playerImage, 
					288/8, 65,0,0);
		//Instantiate sound
		
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
			case PLAYER: return playerImage;
			case BLOCK: return blockImage;
		}
		return null;
	}
	
	public SpriteSheet getSpriteSheet(EAnimType animType){
		switch(animType){
			case PLAYER_RUNNING: return playerRunning; 
		}
		return null;
	}
	
	public TiledMap getLevelMap(int level){
		return testLevel;
	}
	
	public Sound getSound(ESndType sndType){
		return null;
	}
	
	public ParticleSystem getParticleSystem(EParticleSysType particleSysType){
		return null;
	}
	
}
