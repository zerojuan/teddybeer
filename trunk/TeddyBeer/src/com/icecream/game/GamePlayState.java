package com.icecream.game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.factory.AssetFactory;
import com.icecream.util.EImgType;

public class GamePlayState extends BasicGameState{
	private int stateID = -1;
	
	private Animation running;
	
	private Image backgroundImg;
	
	public GamePlayState(int state){
		stateID = state;
	}
	
	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		SpriteSheet spriteSheet = new SpriteSheet(
						AssetFactory.instance().getImage(EImgType.PLAYER), 
						288/8, 65,0,0);
		running = new Animation(spriteSheet,100);
		
		backgroundImg = AssetFactory.instance().getImage(EImgType.BACKGROUND);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		backgroundImg.draw(0,0, container.getWidth(), container.getHeight());
		running.draw(200,200, -200, 200);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
}
