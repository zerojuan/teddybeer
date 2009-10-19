package com.icecream.entity;

import com.icecream.component.RenderComponent;
import com.icecream.component.SpatialComponent;
import com.icecream.exception.MissingComponentException;
import com.icecream.unit.IComponent;

/**
 * A sprite is an entity that doesn't have any input
 * TODO: Create another name for this
 * @author Julius
 *
 */
public class Sprite extends Entity implements IComponent{

	private SpatialComponent spatialComponent;
	private RenderComponent renderComponent;
	
	private boolean active;
	
	public Sprite(String id) {
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
				(renderComponent == null));				
	}			

}
