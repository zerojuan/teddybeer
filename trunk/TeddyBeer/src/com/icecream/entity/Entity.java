package com.icecream.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Entity {
	protected String id;
	
	protected Vector2f position;
	protected Vector2f velocity;
	
	protected Rectangle boundingBox;
	
	public Entity(String id){
		this.id = id;
		position = new Vector2f(0,0);
		velocity = new Vector2f(0,0);		
		boundingBox = new Rectangle(0,0,20,20);
	}
	
	public Entity(String id, Vector2f position){
		this.id = id;
		this.position = position;
		velocity = new Vector2f(0,0);
		boundingBox = new Rectangle(0,0,20,20);
	}
	
	public Entity(String id, Vector2f position, Vector2f velocity){
		this.id = id;
		this.position = position;
		this.velocity = velocity;
		boundingBox = new Rectangle(0,0,20,20);
	}
	
	public Entity(String id, Vector2f position, Vector2f velocity, Rectangle boundingBox){
		this.id = id;
		this.position = position;
		this.velocity = velocity;
		this.boundingBox = boundingBox;
	}
	
	public Entity(String id, Vector2f position, Rectangle boundingBox){
		this.id = id;
		this.position = position;
		this.boundingBox = boundingBox;
	}
	
	public void setBounds(Rectangle bounds){
		this.boundingBox = bounds;
	}
	
	public float getXPos(){
		return position.x;
	}
	
	public float getYPos(){
		return position.y;
	}
	
	public void setYPos(float y){
		position.y = y;
	}
	
	public void setXPos(float x){
		position.x = x;
	}
	
	public void setPosition(float x, float y){
		position.x = x;
		position.y = y;		
		boundingBox.setLocation(x-(boundingBox.getWidth()/2), y-(boundingBox.getHeight()/2));
	}
	
	public Vector2f getPosition(){
		return position;
	}
	
	public Shape getBounds(){
		return (Shape)boundingBox;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
