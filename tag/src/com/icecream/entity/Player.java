package com.icecream.entity;

import com.icecream.component.InputComponent;
import com.icecream.component.RenderComponent;
import com.icecream.component.SpatialComponent;
import com.icecream.exception.MissingComponentException;
import com.icecream.unit.IComponent;

public class Player extends Entity implements IComponent{

	private boolean active;	
	
	//Components that a player must have
	private SpatialComponent spatialComponent;
	private RenderComponent renderComponent;
	private InputComponent inputComponent;
	
	public Player(String id){
		super(id);
	}

	@Override
	public void activate() throws MissingComponentException {
		if(this.validate()){
			this.active = true;
		}
	}

	@Override
	public void connect(IComponent component) {
		if(component instanceof SpatialComponent){
			spatialComponent = (SpatialComponent)component;
		}else if(component instanceof RenderComponent){
			renderComponent = (RenderComponent)component;
		}else if(component instanceof InputComponent){
			inputComponent = (InputComponent)component;
		}
		
	}

	@Override
	public void deactivate() {
		active = false;
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
		return !((spatialComponent == null) || 
				(renderComponent == null) || 
				(inputComponent == null));
	}			

}
