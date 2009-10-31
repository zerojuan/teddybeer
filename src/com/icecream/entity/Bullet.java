package com.icecream.entity;

import java.util.logging.Logger;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.game.GamePlayState;
import com.icecream.util.MathD;

public class Bullet extends Entity {
	private static final Logger logger = Logger.getLogger(Bullet.class.getName());
	public static int currBullet;
	public static int totalBullets  =0;
	
	private Entity player;
	
	private boolean active;
	
	private Vector2f origin;
	private Vector2f dest;
		
	private Circle circle;
	
	public Bullet(String id, Vector2f position, Vector2f velocity, Entity player) {
		super(id);
		this.position = position;
		this.velocity = velocity;
		currBullet = 0;
		this.player = player;
		active = false;
		origin = new Vector2f(0,0);
		dest = new Vector2f(0,0);
		circle = new Circle(0,0,0);
		logger.info("Total bullets.." + totalBullets);
		totalBullets++;
	}	

	public void shoot(Vector2f origin, Vector2f dest){
		active = true;
		this.origin = origin.copy();
		this.dest = dest.copy();
		circle.setRadius(2);
		velocity.set(10, 1);
		velocity.setTheta(MathD.angleBetween(origin, dest));
		position = this.origin;
		//Increment bullet id
		currBullet++;
		if(currBullet >= totalBullets-1){
			currBullet = 0;
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		if(active){
			g.setColor(Color.red);
			g.fill(circle);
			g.setColor(Color.white);
		}

	}	
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		GamePlayState gameState = (GamePlayState)(game.getCurrentState());
		if(active){			
			//velocity = move(delta);
			position.add(velocity);			
			circle.setCenterX(position.x);
			circle.setCenterY(position.y);
			if(gameState.isBlocked(position.x, position.y)){
				active = false;
			}
			if(circle.intersects(player.getBoundingBox())){
				if(!((Player)player).isHiding()){
					((Player)player).hurt();
					active = false;
				}
				
			}
		}
	}
}
