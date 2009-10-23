package com.icecream.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.unit.IComponent;


public abstract class Entity{
	protected String id;
	
	protected Vector2f position;
	protected Vector2f velocity;
	
	protected Rectangle boundingBox;
	
	public Entity(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public abstract void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException;			
	public abstract void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException;

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
}
