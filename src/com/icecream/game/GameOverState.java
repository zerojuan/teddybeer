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
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import com.icecream.entity.Stats;
import com.icecream.factory.AssetFactory;
import com.icecream.util.EAnimType;
import com.icecream.util.EImgType;

public class GameOverState extends BasicGameState{

	private int id;
	
	private int inputDelay;
	
	private TiledMap currentMap;
	private Image gameOverImage;
	
	private Rectangle sky = new Rectangle(0,0,800,600);
	
	private String message[];
	private Font textFont;
	
	private Animation pressBack;
	
	public GameOverState(int id){
		this.id = id;
	}
	
	@Override
	public int getID() {
		return id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		textFont = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20),true);		
		currentMap = AssetFactory.instance().getLevelMap(0);
		gameOverImage = AssetFactory.instance().getImage(EImgType.GAME_OVER);
		pressBack = AssetFactory.instance().getAnimation(EAnimType.PRESS_BACK);
		message = new String[5];
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		message[0] = "You ran a total of " + Stats.instance().runningTime + " meters.";
		message[1] = "You were shot at " + Stats.instance().shotAtTimes + " times.";
		message[2] = "You were hiding a total of " + (float)Stats.instance().hidingTime/1000 + " sec.";
		message[3] = "You died " + Stats.instance().diedTimes + " times.";
		message[4] = "You carried beer home for " + (float)Stats.instance().carryingTime/1000 + " sec.";
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		currentMap.render(0,0);
		g.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
		g.setColor(Color.gray);
		g.fill(sky);
		g.setDrawMode(Graphics.MODE_NORMAL);
		g.setColor(Color.black);
		g.setFont(textFont);
		
		gameOverImage.draw(0,0);
		for(int i =0; i < 5; i++){
			g.drawString(message[i], 375, 250+ (i*(25)));
		}
		
		g.drawString("Now Go Drink Beer!!", 375, 250+(7*25));
		pressBack.draw(390, 250+(9*25));
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		inputDelay+=delta;
		Input in = container.getInput();
		if(inputDelay >= 100)
		if(in.isKeyDown(Input.KEY_SPACE)){
			inputDelay = 0;
			Stats.instance().reset();
			game.enterState(TeddyBeerGame.MENUSTATE);
			
		}
	}

}
