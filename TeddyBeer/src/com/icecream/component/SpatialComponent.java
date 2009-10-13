package com.icecream.component;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.icecream.entity.Entity;
import com.icecream.exception.MissingComponentException;
import com.icecream.unit.IComponent;

public class SpatialComponent implements IComponent{

	private boolean active;
	
	private Entity entity;
	
	protected Vector2f position;
	protected Vector2f velocity;
	
	protected Rectangle boundingBox;
	
	public SpatialComponent(){
		
	}
	
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
		position = new Vector2f(0,0);
		velocity = new Vector2f(0,0);
		
		boundingBox = new Rectangle(0,0,20,20);
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

	public void update(int delta){
		position.add(velocity);
		velocity.scale(-1);
	}
	

}
