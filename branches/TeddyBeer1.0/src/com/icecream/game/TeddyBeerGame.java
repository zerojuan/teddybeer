package com.icecream.game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import com.icecream.exception.MissingComponentException;
import com.icecream.unit.IComponent;

public class TeddyBeerGame extends StateBasedGame implements IComponent{
	public static final int MENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	
	private GamePlayState gamePlayState;
	private MenuState menuState;
	
	private boolean active;
	
	public static final boolean DEBUG_MODE = true;
	
	public TeddyBeerGame() {
		super("TEDDY BEER");
		
		gamePlayState = new GamePlayState(GAMEPLAYSTATE);
		menuState = new MenuState(MENUSTATE);
		
		this.addState(gamePlayState);
		this.addState(menuState);
		
		this.enterState(MENUSTATE);
		
			
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		Log.setVerbose(true);
		container.setShowFPS(true);
		
		this.getState(MENUSTATE).init(container, this);
		this.getState(GAMEPLAYSTATE).init(container, this);
	}

	/**
	 * This is the main method
	 */
	public static void main(String args[]){
		try{
			//Construct the components
			TeddyBeerGame game = new TeddyBeerGame();
			
			//Establish component connections
			
			//Initialize components
			try{
				game.activate();
			}catch(MissingComponentException e){
				e.printStackTrace();
			}
			
			
			AppGameContainer app = new AppGameContainer(game);
			app.setDisplayMode(800, 600, false);
			app.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void activate() throws MissingComponentException{
		if(this.validate()){
			this.active = true;
			this.initialize();
		}
	}
	
	@Override
	public void connect(IComponent component){
		//TODO: create connections here
	}
	
	@Override
	public void deactivate(){
		active = false;
	}
	
	@Override
	public boolean isActive(){
		return active;
	}
	
	@Override
	public boolean validate() throws MissingComponentException{
		//TODO: create validations if this component is ready here
		return true;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
