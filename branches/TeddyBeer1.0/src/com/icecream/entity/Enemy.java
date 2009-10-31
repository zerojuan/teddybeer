package com.icecream.entity;

import java.awt.Point;
import java.util.logging.Logger;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.factory.AssetFactory;
import com.icecream.game.GamePlayState;
import com.icecream.game.TeddyBeerGame;
import com.icecream.util.EAnimType;
import com.icecream.util.EImgType;
import com.icecream.util.ESndType;

public class Enemy extends Entity {
	private final static Logger logger = Logger.getLogger(Enemy.class.getName());	
	
	private Entity player;
	
	private Rectangle boundingBox;
	private Circle range;
	
	private Bullet[] bullets;
	
	private Animation randomAnim;
	private Animation alertLeft;
	private Animation alertRight;
	private Animation shootLeft;
	private Animation shootRight;
	
	private Animation currState;
	
	private Image alertImage;
	
	private Vector2f dest;
	
	private int SIZE = 20;
	private int MID_ALERT_LEVEL = 200;
	
	private int decisionInterval;
	private int twitchInterval;
	private int reloadInterval = 1000;
	private int alertLevel;
	private Sound gunShotSound; 
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
		gunShotSound = AssetFactory.instance().getSound(ESndType.GUNSHOT);
		status = Status.MOVING;
	}
	
	private void initAnimationStates(){
		randomAnim = new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.ENEMY_DRUNK),
				100
			);
		alertLeft = new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.ENEMY_ALERT_LEFT),
				100
			);
		alertRight = new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.ENEMY_ALERT_RIGHT),
				100
			);
		shootLeft = new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.ENEMY_SHOOT_LEFT),
				100
			);
		shootRight = new Animation(
				AssetFactory.instance().getSpriteSheet(EAnimType.ENEMY_SHOOT_RIGHT),
				100
			);
		
		currState = randomAnim;
		
		alertImage = AssetFactory.instance().getImage(EImgType.ALERT);
	}
		
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		currState.draw(position.x-7, position.y-15);
		if(TeddyBeerGame.DEBUG_MODE){
			g.draw(boundingBox);
			g.draw(range);
			g.drawString(alertLevel+"", position.x, position.y);
		}
		if(status == Status.STILL){
			alertImage.draw(position.x - 20, position.y - 20);
		}
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
				currState = randomAnim;
				twitchInterval = 0;
			}
			
			Vector2f accel = move(destX,destY,delta);
			if(!collisionDetect(gameState, accel)){
				position.x += accel.x;
				position.y += accel.y;
				boundingBox.setX(position.x);
				boundingBox.setY(position.y);
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
			setAlertDirection((Player)player);
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
			setShootingDirection((Player)player);
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
			Stats.instance().shotAtTimes++;
			gunShotSound.play();
		}
		
	}
	
	private void setAlertDirection(Player target){
		if(target.position.x > position.x){
			currState = alertRight;
		}else{
			currState = alertLeft;
		}
	}
	
	private void setShootingDirection(Player target){
		if(target.position.x > position.x){
			currState = shootRight;
		}else{
			currState = shootLeft;
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
