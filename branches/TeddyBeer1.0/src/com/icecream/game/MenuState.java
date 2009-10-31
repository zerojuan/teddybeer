package com.icecream.game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
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
import com.icecream.util.EAnimType;
import com.icecream.util.EImgType;

public class MenuState extends BasicGameState{

	private int stateID = -1;
	
	private Font textFont;
		
	private Image backgroundImg;
	
	private String statMessage;
	
	private Animation bearRunning;
	private Animation hunter;
	
	private Animation pressX;
	
	private int inputDelay;
	
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
		pressX = AssetFactory.instance().getAnimation(EAnimType.PRESS_X);
		bearRunning =  new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.BEER_DOWN),
				200
			);
		hunter = new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.ENEMY_ALERT_LEFT),
				500
			);
		backgroundImg = AssetFactory.instance().getImage(EImgType.BACKGROUND);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(Color.black);
		backgroundImg.draw(0,0, container.getWidth(), container.getHeight());
		hunter.draw(100, 400, 120, 120);
		bearRunning.draw(550,400, 110,110);
		pressX.draw(250, 550);	
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		inputDelay+=delta;
		Input in = container.getInput();
		if(inputDelay >= 100)
		if(in.isKeyDown(Input.KEY_SPACE)){
			inputDelay = 0;
			game.enterState(TeddyBeerGame.GAMEPLAYSTATE);
			
		}
	}

}
