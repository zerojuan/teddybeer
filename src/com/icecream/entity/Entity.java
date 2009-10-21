package com.icecream.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.unit.IComponent;


public abstract class Entity{
	protected String id;
	
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
}
