package com.icecream.entity;

import java.awt.Point;
import java.util.logging.Logger;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.factory.AssetFactory;
import com.icecream.game.GamePlayState;
import com.icecream.util.EAnimType;

public class Enemy extends Entity {
	private final static Logger logger = Logger.getLogger(Enemy.class.getName());	
	
	private Entity player;
	
	private Rectangle boundingBox;
	private Circle range;
	
	private Bullet[] bullets;
	
	private Animation idleLeft;
	private Animation idleRight;
	private Animation idleUp;
	private Animation idleDown;	
	
	private Animation currState;
	
	private Vector2f dest;
	
	private int SIZE = 20;
	private int MID_ALERT_LEVEL = 200;
	
	private int decisionInterval;
	private int twitchInterval;
	private int reloadInterval = 1000;
	private int alertLevel;
	
	float destX = 0f; 
	float destY = 0f; 
	
	public static enum Status{
		STILL,
		MOVING,
		SHOOT
	}
	
	public Status status;
	
	public Enemy(String id){
		super(id);	
		initAnimationStates();
		
	}
	
	public Enemy(String id, Vector2f position, Vector2f velocity, Entity player, Bullet[] bullets){
		super(id);
		init();
		initAnimationStates();
		this.position = position;
		this.velocity = velocity;
		this.boundingBox = new Rectangle(position.x, position.y, 20, 20);
		this.range = new Circle(position.x, position.y, 100);
		this.bullets = bullets;
		this.player = player;
	}
	
	private void init(){
		status = Status.MOVING;
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
		currState.draw(position.x-10, position.y-20);
		g.draw(boundingBox);
		g.draw(range);
		g.drawString(alertLevel+"", position.x, position.y);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		decisionInterval += delta;
		twitchInterval += delta;
		GamePlayState gameState = (GamePlayState)(game.getCurrentState());
		//Randomly walk to a spot	
		if(status == Status.MOVING){
			//Only look for new destination at specific intervals
			if(decisionInterval >= 250){ 
				destX = (float)(Math.random()*200) - 100 + position.x;
				destY = (float)(Math.random()*200) - 100 + position.y;
				//destX = (float)(Math.random()*200) - 100 + player.getPosition().x;
				//destY = (float)(Math.random()*200) - 100 + player.getPosition().y;
				decisionInterval = 0;				
			}
			
			if(twitchInterval >= 3000){
				setDrunkenSpriteDirection();
				twitchInterval = 0;
			}
			
			Vector2f accel = move(destX,destY,delta);
			if(!collisionDetect(gameState, accel)){
				position.x += accel.x;
				position.y += accel.y;
				boundingBox.setCenterX(position.x);
				boundingBox.setCenterY(position.y);
				range.setCenterX(position.x);
				range.setCenterY(position.y);
			}
			
			//check if target is within vicinity
			if(isTargetInRange()){
				status = Status.STILL;
				alertLevel = MID_ALERT_LEVEL;
			}
		}else if(status == Status.STILL){
			if(((Player)player).status == Player.Status.HIDING){
				alertLevel -= delta/2;
			}else{
				alertLevel += delta/2;
			}
			
			if(alertLevel >= MID_ALERT_LEVEL*2){
				status = Status.SHOOT;
			}else if(alertLevel < 0){
				status = Status.MOVING;
			}
		}else if(status == Status.SHOOT){
			reloadInterval += delta;
			if(((Player)player).status == Player.Status.HIDING){
				alertLevel -= delta/2;
			}
			if(!isTargetInRange()){
				alertLevel = -1;
			}
			shoot();
			if(alertLevel < 0){
				status = Status.MOVING;
				reloadInterval = 1000;
			}
		}
		
		
		
		//Scan for player
		//If player was seen
			//count to 10
			//if still i see then shoot
				
		
	}
	
	private void shoot(){
		if(reloadInterval > 1000){
			bullets[Bullet.currBullet].shoot(position, player.getPosition());
			reloadInterval = 0;
		}
		
	}
	
	private void setDrunkenSpriteDirection(){
		int direction = (int)(Math.random()*3);		
		if(direction == 0){
			currState = idleLeft;
		}else if(direction == 1){
			currState = idleRight;
		}else if(direction == 2){
			currState = idleUp;
		}else if(direction == 3){
			currState = idleDown;
		}
	}
	
	private boolean isTargetInRange(){
		if(((Player)player).status == Player.Status.HIDING){
			return false;
		}
		return range.intersects(player.getBoundingBox());
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
