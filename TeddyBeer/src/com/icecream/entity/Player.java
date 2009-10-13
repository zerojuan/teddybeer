package com.icecream.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.exception.MissingComponentException;
import com.icecream.unit.IComponent;

public class Player extends Entity implements IComponent{

	private boolean active;	
	
	public Player(String id, float x, float y){
		super(id);
	}

	@Override
	public void activate() throws MissingComponentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connect(IComponent component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public boolean validate() throws MissingComponentException {		
		return true;
	}	

}
