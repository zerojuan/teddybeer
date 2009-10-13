package com.icecream.component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.entity.Entity;
import com.icecream.exception.MissingComponentException;
import com.icecream.unit.IComponent;

public class RenderComponent implements IComponent, IRenderable {

	private boolean active;
	
	private Entity entity;
		
	
	public RenderComponent(){		
	}
	
	@Override
	public void activate() throws MissingComponentException {
		if(this.validate()){
			this.active = true;	
			initialize();
		}
	}

	@Override
	public void connect(IComponent component) {
		if(component instanceof Entity){
			this.entity = (Entity)component;
		}
	}

	@Override
	public void deactivate() {
		active = false;
	}

	@Override
	public void initialize() {
		
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public boolean validate() throws MissingComponentException {
		if(entity == null){
			return false;
		}
		return true;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, int delta) {
		
	}
	
	

}
