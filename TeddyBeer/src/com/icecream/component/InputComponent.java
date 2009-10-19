package com.icecream.component;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.icecream.entity.Entity;
import com.icecream.exception.MissingComponentException;
import com.icecream.unit.IComponent;

/**
 * This can be overriden to create your own input stuff,
 * This default is for keyboard
 * @author Julius
 *
 */
public class InputComponent implements IComponent{

	private boolean active;
	
	private Entity entity;
	/**
	 * Because you cannot manipulate an object if it isn't in space
	 */
	private SpatialComponent spatialComponent;
	
	public InputComponent(SpatialComponent spatialComponent, Entity owner){
		this.spatialComponent = spatialComponent;
		this.entity = owner;
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
		if(component instanceof SpatialComponent){
			spatialComponent = (SpatialComponent)component;
		}else if(component instanceof Entity){
			entity = (Entity)component;
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
			throw new MissingComponentException("Missing an entity");
		}
		if(spatialComponent == null){
			throw new MissingComponentException("Missing Spatial Component");
		}
		return true;
	}

	public void doInput(Input input){
		Vector2f force = new Vector2f(0,0);
		if(input.isKeyDown(Input.KEY_LEFT)){
			force.x = -3;
		}
		if(input.isKeyDown(Input.KEY_RIGHT)){
			force.x = 3;
		}
		if(input.isKeyDown(Input.KEY_DOWN)){
			force.y = 3;
		}
		if(input.isKeyDown(Input.KEY_UP)){
			force.y = -3;
		}
		spatialComponent.setVelocity(force);
	}

}
