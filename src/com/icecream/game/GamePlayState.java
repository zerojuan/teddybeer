package com.icecream.game;

import java.awt.Point;
import java.util.logging.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import com.icecream.entity.Bullet;
import com.icecream.entity.Enemy;
import com.icecream.entity.Entity;
import com.icecream.entity.Player;
import com.icecream.factory.AssetFactory;

public class GamePlayState extends BasicGameState{
	private final static Logger logger = Logger.getLogger(GamePlayState.class.getName());
	
	private int stateID = -1;	
		
	private TiledMap currentMap;
	
	private boolean blocked[][];
	
	public static int TILE_SIZE = 20;
	
	private Entity player;
	
	private Enemy[] enemies;
	
	private Bullet[] bullets;
	
	private Point beerHouse;
	private Point bearHouse;
	
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
				tileId = currentMap.getTileId(xAxis, yAxis, 2);
				value = currentMap.getTileProperty(tileId, "blocked", "false");
				if("true".equals(value)){					
					blocked[xAxis][yAxis] = true;
				}
			}
		}
		beerHouse.setLocation(27,3);
		bearHouse.setLocation(3,3);
		
	}
	
	public boolean isWithinBeerHouse(float x, float y){
		int xBlock = (int)Math.floor(x / TILE_SIZE);
		int yBlock = (int)Math.floor(y / TILE_SIZE);
		if(xBlock < 0 || xBlock >= currentMap.getWidth())
			return true;
		if(yBlock < 0 || yBlock >= currentMap.getHeight()){
			return true;
		}
		return beerHouse.equals(new Point(xBlock,yBlock));
	}
	
	public boolean isWithinBearHouse(float x, float y){
		int xBlock = (int)Math.floor(x / TILE_SIZE);
		int yBlock = (int)Math.floor(y / TILE_SIZE);
		if(xBlock < 0 || xBlock >= currentMap.getWidth())
			return true;
		if(yBlock < 0 || yBlock >= currentMap.getHeight()){
			return true;
		}
		return bearHouse.equals(new Point(xBlock,yBlock));
	}
	
	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {	
		beerHouse = new Point(90,90);
		bearHouse = new Point(80,90);		
		createLevel(0);
		player = new Player("me",new Vector2f(bearHouse.x * TILE_SIZE,bearHouse.y * TILE_SIZE), new Vector2f(0,0));
		((Player)player).setRespawnPoint(bearHouse);
		bullets = new Bullet[5];		
		for(int i = 0; i < 5; i++){
			bullets[i] = new Bullet(""+i,new Vector2f(0,0), new Vector2f(0,0), player);
		}		
		enemies = new Enemy[4];
		enemies[0] = new Enemy("you", new Vector2f(200,200), new Vector2f(0,0), player, bullets);
		enemies[1] = new Enemy("you", new Vector2f(100,400), new Vector2f(0,0), player, bullets);
		enemies[2] = new Enemy("you", new Vector2f(500,400), new Vector2f(0,0), player, bullets);
		enemies[3] = new Enemy("you", new Vector2f(400,200), new Vector2f(0,0), player, bullets);
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
		for(int i = 0; i < 5; i++){
			bullets[i].render(container, game, g);
		}
		
		player.render(container, game, g);
		currentMap.render(0, 0, 0, 0, currentMap.getWidth(), currentMap.getHeight(), 2, false);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input in = container.getInput();
		if(in.isKeyPressed(Input.KEY_Q)){
			TeddyBeerGame.DEBUG_MODE= !(TeddyBeerGame.DEBUG_MODE);
		}
		for(int i = 0; i < 4; i++){
			enemies[i].update(container, game, delta);
		}
		for(int i = 0; i < 5; i++){
			bullets[i].update(container, game, delta);
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
