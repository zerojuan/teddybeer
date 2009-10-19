package com.icecream.game;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.factory.AssetFactory;
import com.icecream.util.EImgType;

public class MenuState extends BasicGameState{

	private int stateID = -1;
	
	private Font textFont;
		
	private Image backgroundImg;
	
	private String statMessage;
	
	public MenuState(int state){
		this.stateID = state;
	}
	
	@Override
	public int getID() {	
		return stateID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		textFont = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12),true);
		statMessage = "Press X to start";
		
		backgroundImg = AssetFactory.instance().getImage(EImgType.BACKGROUND);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		backgroundImg.draw(0,0, container.getWidth(), container.getHeight());
		
		g.drawString(statMessage, 600,500);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input in = container.getInput();
		if(in.isKeyDown(Input.KEY_X)){
			game.enterState(TeddyBeerGame.GAMEPLAYSTATE);
		}
	}

}
