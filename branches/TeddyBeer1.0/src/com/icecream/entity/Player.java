package com.icecream.entity;


import java.awt.Point;

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
	
	private Vector2f position;
	private Vector2f velocity;
	
	private Rectangle boundingBox;
	
	private Animation idleLeft;
	private Animation idleRight;
	private Animation idleUp;
	private Animation idleDown;
	
	private Animation currState;
	
	private Rectangle[] territory;
	
	private int SIZE = 18;
	
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
		initTerritories();
	}
	
	private void initTerritories(){
		territory = new Rectangle[4];
		territory[0] = new Rectangle(position.x,position.y,SIZE, SIZE);
		territory[1] = new Rectangle(position.x,position.y,SIZE, SIZE);
		territory[2] = new Rectangle(position.x,position.y,SIZE, SIZE);
		territory[3] = new Rectangle(position.x,position.y,SIZE, SIZE);
	}
	
	private void initAnimationStates(){
		idleLeft = new Animation(
					AssetFactory.instance().getSpriteSheet(EAnimType.IDLE_LEFT),
					100
				);
		idleDown = new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.IDLE_DOWN),
				100
			);
		idleUp = new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.IDLE_UP),
				100
			);
		idleRight = new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.IDLE_RIGHT),
				100
			);
		
		currState = idleLeft;
		
	}
	
	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}



	public Rectangle getBoundingBox() {
		return boundingBox;
	}



	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
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

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		currState.draw(position.x - 5, position.y - 10);
		if(TeddyBeerGame.DEBUG_MODE){
			//FIXME: Remove this upon release
			g.setColor(Color.red);
			for(int i = 0; i < 4; i++){
				g.draw(territory[i]);
			}
			g.setColor(Color.white);
			g.draw(boundingBox);
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
	         if (input.isKeyDown(Input.KEY_UP))	        	 
	         {
	             currState = idleUp;
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
	             //if (!gameState.collidesWithBlock(x + 20 + delta * 0.1f, y))
	             if (!(gameState.isBlocked(x + SIZE + fdelta, y) || gameState.isBlocked(x+SIZE+fdelta, y+SIZE-1)))
	             {
	                 currState.update(delta);
	                 x +=fdelta;
	             }
	         }
	         
	         position.x = x;
	         position.y = y;
	         boundingBox.setX(position.x);
	         boundingBox.setY(position.y);
	         setTerritory();
		
	}

	
}
