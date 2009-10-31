package com.icecream.entity;


import java.awt.Point;
import java.util.logging.Logger;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.factory.AssetFactory;
import com.icecream.game.GamePlayState;
import com.icecream.game.TeddyBeerGame;
import com.icecream.util.EAnimType;


public class Player extends Entity{		
	private static final Logger logger = Logger.getLogger(Player.class.getName());
	
	private Animation idleLeft;
	private Animation idleRight;
	private Animation idleUp;
	private Animation idleDown;
	private Animation idleHide;
	private Animation beerUp;
	private Animation beerDown;
	private Animation beerRight;
	private Animation beerLeft;
	
	private Animation currState;
	
	private Rectangle[] territory;
	
	private Point respawnPoint;
	
	private int SIZE = 15;
	
	private int animationSpeed = 200;
	
	public static enum Status{
		WALKIN_EMPTY,
		BEER_WALKING,
		HIDING,
		DEAD
	}
	
	public Status status;
	
	public Player(String id){
		super(id);	
		initAnimationStates();
		initTerritories();
	}
	
	public Player(String id, Vector2f position, Vector2f velocity){
		super(id);
		initAnimationStates();
		this.position = position;
		this.velocity = velocity;
		this.boundingBox = new Rectangle(position.x,position.y,SIZE,SIZE);
		this.status = Status.WALKIN_EMPTY;
		this.respawnPoint = new Point(0,0);
		initTerritories();
	}
	
	private void initTerritories(){
		territory = new Rectangle[4];
		territory[0] = new Rectangle(position.x,position.y,SIZE, SIZE);
		territory[1] = new Rectangle(position.x,position.y,SIZE, SIZE);
		territory[2] = new Rectangle(position.x,position.y,SIZE, SIZE);
		territory[3] = new Rectangle(position.x,position.y,SIZE, SIZE);
	}
	
	public void setRespawnPoint(Point point){
		respawnPoint = point;
	}
	
	private void respawn(){
		position.set(respawnPoint.x * GamePlayState.TILE_SIZE, respawnPoint.y * GamePlayState.TILE_SIZE);
	}
	
	private void initAnimationStates(){
		idleLeft = new Animation(
					AssetFactory.instance().getSpriteSheet(EAnimType.IDLE_LEFT),
					animationSpeed
				);
		idleDown = new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.IDLE_DOWN),
				animationSpeed
			);
		idleUp = new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.IDLE_UP),
				animationSpeed
			);
		idleRight = new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.IDLE_RIGHT),
				animationSpeed
			);
		idleHide =  new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.IDLE_HIDE),
				100
			);
		beerUp =  new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.BEER_UP),
				100
			);
		beerLeft =  new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.BEER_LEFT),
				100
			);
		beerRight =  new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.BEER_RIGHT),
				100
			);
		beerDown =  new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.BEER_DOWN),
				100
			);
		
		currState = idleLeft;
		
	}
	
	public void setTerritory(){
		Point topLeft = new Point((int)position.x/20, (int)position.y/20);
		Point topRight = new Point((int)(position.x + boundingBox.getWidth())/20, (int)position.y/20);
		Point bottomLeft = new Point((int)position.x/20, (int)(position.y+boundingBox.getHeight())/20);
		Point bottomRight = new Point((int)(position.x+boundingBox.getWidth())/20, (int)(position.y+boundingBox.getHeight())/20);
		
		territory[0].setLocation(topLeft.x*20, topLeft.y*20);
		territory[1].setLocation(topRight.x*20, topRight.y*20);
		territory[2].setLocation(bottomLeft.x*20, bottomLeft.y*20);
		territory[3].setLocation(bottomRight.x*20, bottomRight.y*20);
	}
	
	public boolean isWithinBeerHouse(GamePlayState game){
		return game.isWithinBeerHouse(position.x, position.y);
	}
	
	public boolean isWithinBearHouse(GamePlayState game){
		return game.isWithinBearHouse(position.x, position.y);
	}
	/**
	 * Called if the player is hit
	 */
	public void hurt(){
		if(status != Status.HIDING){
			status = Status.DEAD;
		}
		
	}
	
	public boolean isCarrying(){
		return (status == Status.BEER_WALKING);
	}
	
	public boolean isHiding(){
		return (status == Status.HIDING);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		currState.draw(position.x-7, position.y-15);
		if(TeddyBeerGame.DEBUG_MODE){
			//FIXME: Remove this upon release
			g.setColor(Color.red);
			for(int i = 0; i < 4; i++){
				g.draw(territory[i]);
			}
			g.setColor(Color.white);
			g.draw(boundingBox);
			g.drawString(status.toString(), position.x, position.y);
		}
			
	}
	
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		  	Input input = container.getInput();
		  	float x = position.x;
		  	float y = position.y;
		  	float fdelta = delta * 0.1f;
		  	GamePlayState gameState = (GamePlayState)(game.getCurrentState());
		  	if(status != Status.DEAD){
		  		 if (input.isKeyDown(Input.KEY_UP))	        	 
		         {		  			 
		             currState = idleUp;
		             if(status == Status.BEER_WALKING){
		            	 currState = beerUp;
		             }
		             //if (!gameState.collidesWithBlock(x, y - delta * 0.1f))
		             if (!(gameState.isBlocked(x, y-fdelta) || gameState.isBlocked(x+SIZE-1, y-fdelta)))
		             {
		                 currState.update(delta);
		                 // The lower the delta the slowest the sprite will animate.
		                 y -= fdelta;
		             }
		         }
		         if (input.isKeyDown(Input.KEY_DOWN))
		         {
		             currState = idleDown;
		             if(status == Status.BEER_WALKING){
		            	 currState = beerDown;
		             }
		             //if (!gameState.collidesWithBlock(x, y + 20 + delta * 0.1f))
		             if (!(gameState.isBlocked(x, y+SIZE+fdelta) || gameState.isBlocked(x+SIZE-1, y+SIZE+fdelta)))
		             {
		                 currState.update(delta);
		                 y += fdelta;
		             }
		         }
		         if (input.isKeyDown(Input.KEY_LEFT))
		         {
		             currState = idleLeft;
		             if(status == Status.BEER_WALKING){
		            	 currState = beerLeft;
		             }
		             //if (!gameState.collidesWithBlock(x - fdelta, y))
		             if (!(gameState.isBlocked(x - fdelta, y) || gameState.isBlocked(x-fdelta, y+SIZE-1)))
		             {
		                 currState.update(delta);
		                 x -= fdelta;
		             }
		         }
		         if (input.isKeyDown(Input.KEY_RIGHT))
		         {
		             currState = idleRight;
		             if(status == Status.BEER_WALKING){
		            	 currState = beerRight;
		             }
		             //if (!gameState.collidesWithBlock(x + 20 + delta * 0.1f, y))
		             if (!(gameState.isBlocked(x + SIZE + fdelta, y) || gameState.isBlocked(x+SIZE+fdelta, y+SIZE-1)))
		             {
		                 currState.update(delta);
		                 x +=fdelta;
		             }
		         }
		         if(input.isKeyDown(Input.KEY_X) && status != Player.Status.BEER_WALKING){
		        	 status = Player.Status.HIDING;
		        	 currState = idleHide;
		         }
		         
		         if(isWithinBeerHouse(gameState) && status == Player.Status.WALKIN_EMPTY){
		        	 status = Player.Status.BEER_WALKING;
		         }
		         
		         if(isWithinBearHouse(gameState) && status == Player.Status.BEER_WALKING){
		        	 status = Player.Status.WALKIN_EMPTY;
		        	 Stats.instance().score++;
		        	 if(Stats.instance().score >= 5){
		        		 game.enterState(TeddyBeerGame.GAMEOVERSTATE);
		        	 }
		         }
		         if(position.x != x || position.y != y){
		        	 if(status == Player.Status.HIDING){
		        		 status = Player.Status.WALKIN_EMPTY;
		        	 }
		        	 if(status == Player.Status.BEER_WALKING){
		        		 Stats.instance().carryingTime+=delta*.2;
		        	 }else{
		        		 Stats.instance().runningTime+=delta*.2; 
		        	 }
		        	 
		         }		
		         
		         if(status == Player.Status.HIDING){
		        	 Stats.instance().hidingTime+=delta;
		         }
		         
		         position.x = x;
		         position.y = y;
		         boundingBox.setX(position.x);
		         boundingBox.setY(position.y);
		         setTerritory();
		  	}else{
		  		Stats.instance().diedTimes++;
		  		respawn();
		  		status = Player.Status.WALKIN_EMPTY;
		  	}
	        
	         
		
	}

	
}
