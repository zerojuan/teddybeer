package com.icecream.game;

import java.util.logging.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import com.icecream.entity.Enemy;
import com.icecream.entity.Entity;
import com.icecream.entity.Player;
import com.icecream.factory.AssetFactory;

public class GamePlayState extends BasicGameState{
	private final static Logger logger = Logger.getLogger(GamePlayState.class.getName());
	
	private int stateID = -1;	
		
	private TiledMap currentMap;
	
	private boolean blocked[][];
	
	private int TILE_SIZE = 20;
	
	private Entity player;
	
	private Enemy[] enemies;
	
	public GamePlayState(int state){
		stateID = state;
	}
	
	private void createLevel(int level){			
		currentMap = AssetFactory.instance().getLevelMap(level);
		blocked = new boolean[currentMap.getWidth()][currentMap.getHeight()];
		for(int xAxis = 0; xAxis < currentMap.getWidth(); xAxis++){
			for(int yAxis = 0; yAxis < currentMap.getHeight(); yAxis++){
				int tileId = currentMap.getTileId(xAxis, yAxis, 1);
				String value = currentMap.getTileProperty(tileId, "blocked", "false");
				if("true".equals(value)){					
					blocked[xAxis][yAxis] = true;
				}
			}
		}
	}
	
	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		createLevel(0);
		player = new Player("me",new Vector2f(10,10), new Vector2f(0,0));
		enemies = new Enemy[4];
		enemies[0] = new Enemy("you", new Vector2f(200,200), new Vector2f(0,0), player);
		enemies[1] = new Enemy("you", new Vector2f(450,400), new Vector2f(0,0), player);
		enemies[2] = new Enemy("you", new Vector2f(300,400), new Vector2f(0,0), player);
		enemies[3] = new Enemy("you", new Vector2f(400,200), new Vector2f(0,0), player);
	}	

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		currentMap.render(0, 0);
		
		//FIXME: Remove this upon release
		if(TeddyBeerGame.DEBUG_MODE){
			for(int xAxis = 0; xAxis < currentMap.getWidth(); xAxis++){
				for(int yAxis = 0; yAxis < currentMap.getHeight(); yAxis++){
					g.draw(new Rectangle(xAxis*20, yAxis*20, 20,20));
				}
			}
		}
		for(int i = 0; i < 4; i++){
			enemies[i].render(container, game, g);
		}
		player.render(container, game, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		for(int i = 0; i < 4; i++){
			enemies[i].update(container, game, delta);
		}
		player.update(container, game, delta);
	}
	
	public boolean isBlocked(float x, float y){
		int xBlock = (int)Math.floor(x / TILE_SIZE);
		int yBlock = (int)Math.floor(y / TILE_SIZE);
		if(xBlock < 0 || xBlock >= currentMap.getWidth())
			return true;
		if(yBlock < 0 || yBlock >= currentMap.getHeight()){
			return true;
		}
		return blocked[xBlock][yBlock];
	}
	
	public boolean isBlocked(int xBlock, int yBlock){
		if(xBlock < 0 || xBlock >= currentMap.getWidth())
			return true;
		if(yBlock < 0 || yBlock >= currentMap.getHeight()){
			return true;
		}
		return blocked[xBlock][yBlock];
	}
	
	public boolean collidesWithBlock(float x, float y){
		int cenX = (int)Math.floor(x/20);
		int cenY = (int)Math.floor(y/20);
		if(cenX < 0 || cenX >= currentMap.getWidth())
			return false;
		if(cenY < 0 || cenY >= currentMap.getHeight()){
			return false;
		}
		return blocked[cenX][cenY];
	}
	
}
