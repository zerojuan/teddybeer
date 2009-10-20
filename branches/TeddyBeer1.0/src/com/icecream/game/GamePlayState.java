package com.icecream.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import com.icecream.factory.AssetFactory;

public class GamePlayState extends BasicGameState{
	private int stateID = -1;	
		
	private TiledMap currentMap;
	
	private boolean blocked[][];
	
	public GamePlayState(int state){
		stateID = state;
	}
	
	private void createLevel(int level){
		currentMap = AssetFactory.instance().getLevelMap(level);
		blocked = new boolean[currentMap.getWidth()][currentMap.getHeight()];
		for(int xAxis = 0; xAxis < currentMap.getWidth(); xAxis++){
			for(int yAxis = 0; yAxis < currentMap.getHeight(); yAxis++){
				int tileId = currentMap.getTileId(xAxis, yAxis, 0);
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
		
	}	

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		currentMap.render(3, 3);
		for(int xAxis = 0; xAxis < currentMap.getWidth(); xAxis++){
			for(int yAxis = 0; yAxis < currentMap.getHeight(); yAxis++){
				int tileId = currentMap.getTileId(xAxis, yAxis, 0);
				Image value = currentMap.getTileImage(xAxis, yAxis, 0);
				value.draw(20*xAxis, 20*yAxis, 1);
			}
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
	}
	
}
