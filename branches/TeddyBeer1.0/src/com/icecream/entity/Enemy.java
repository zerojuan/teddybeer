package com.icecream.entity;

import java.awt.Point;
import java.util.logging.Logger;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.factory.AssetFactory;
import com.icecream.game.GamePlayState;
import com.icecream.util.EAnimType;

public class Enemy extends Entity {
	private final static Logger logger = Logger.getLogger(Enemy.class.getName());
	
	private Vector2f position;
	private Vector2f velocity;
	
	private Rectangle boundingBox;
	
	private Animation idleLeft;
	private Animation idleRight;
	private Animation idleUp;
	private Animation idleDown;
	
	private Animation currState;
	
	private Vector2f dest;
	
	private int SIZE = 20;
	
	private int decisionInterval;
	
	float destX = 0f; 
	float destY = 0f; 
	
	private enum EEnemyStatus{
		STILL,
		MOVING,
		TARGETING
	}
	
	public Enemy(String id){
		super(id);	
		initAnimationStates();
		
	}
	
	public Enemy(String id, Vector2f position, Vector2f velocity){
		super(id);
		initAnimationStates();
		this.position = position;
		this.velocity = velocity;
		this.boundingBox = new Rectangle(position.x, position.y, 20, 20);
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
		
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		currState.draw(position.x, position.y);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		GamePlayState gameState = (GamePlayState)(game.getCurrentState());
		//Randomly walk to a spot	
		
		if(decisionInterval >= 250){
			destX = (float)(Math.random()*500) - 100;
			destY = (float)(Math.random()*500) - 100;
			decisionInterval = 0;
		}
		decisionInterval += delta;
		Vector2f accel = move(destX,destY,delta);
		if(!collisionDetect(gameState, accel)){
			position.x += accel.x;
			position.y += accel.y;
		}
		
		//Scan for player
		//If player was seen
			//count to 10
			//if still i see then shoot
				
		
	}
	
	private boolean collisionDetect(GamePlayState gameState, Vector2f accel){
		int x = (int)(position.x + accel.x);
		int y = (int)(position.y + accel.y);
		Point topLeft = new Point(x/20, y/20);
		Point topRight = new Point((int)(x + boundingBox.getWidth())/
					20, y/20);
		Point bottomLeft = new Point((int)x/20, (int)(y+boundingBox.getHeight())/20);
		Point bottomRight = new Point((int)(x + boundingBox.getWidth())/
					20,(int)(y+boundingBox.getHeight())/20);
		return (gameState.isBlocked(topLeft.x, topLeft.y)) ||
				(gameState.isBlocked(bottomLeft.x, bottomLeft.y)) ||
				(gameState.isBlocked(topRight.x, topRight.y)) ||
				(gameState.isBlocked(bottomRight.x, bottomRight.y));
	}
	public Vector2f move(float destX, float destY, int delta){
		int speed = 200;
		float incX = (globalToLocal(position.x, destX)) / (speed);
		float incY = (globalToLocal(position.y, destY)) / (speed);			
		
		//xPos = startX + (incX * (elapsedTime / 100));
		//yPos = startY + (incY * (elapsedTime / 100));
		//position.x+=incX; 
		//position.y+=incY;
		
		return new Vector2f(incX,incY);
		
		/*
		float incX = position.x;
		float incY = position.y;
		
		PointF polarCoord = MathUtil.cartesianToPolar(new PointF(incX, incY));
		if(polarCoord.x > 0){
			polarCoord.x--;
		}
		PointF cartCoord = MathUtil.polarToCartesian(polarCoord);
		setPos(cartCoord.x, -cartCoord.y);*/
		
	}
	
	public float globalToLocal(float local, float dest){
		return dest - local;
	}

}
