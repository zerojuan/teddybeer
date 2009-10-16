package com.icecream.component;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.Log;

import com.icecream.entity.Entity;
import com.icecream.exception.MissingComponentException;
import com.icecream.unit.IComponent;
import com.icecream.util.ECollisionId;

public class SpatialComponent implements IComponent{

	private boolean active;
	
	private Entity entity;
	
	protected Vector2f position;
	protected Vector2f velocity;
	
	protected Rectangle boundingBox;
	
	protected ECollisionId collisionId;
	
	public SpatialComponent(){
		this.collisionId = ECollisionId.GUI;
	}
	
	public SpatialComponent(ECollisionId collisionId){
		this.collisionId = collisionId;
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
		Log.info("Setting velocity" + velocity.toString());
		this.velocity = velocity;
	}
	public ECollisionId getCollisionId(){
		return collisionId;
	}
	public void setCollisionId(ECollisionId collisionId){
		this.collisionId = collisionId;
	}
	public Rectangle getBounds(){
		return boundingBox;
	}
	public void setBounds(Rectangle rect){
		this.boundingBox = rect;
	}

	/**
	 * Called by the manager when I collided with another object
	 * @param component The component this object collided with
	 */
	public void collidedWith(SpatialComponent component){
		//FIXME: Add collision logic here
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
			throw new MissingComponentException();
		}
		return true;
	}

	public void update(int delta){
		position.add(velocity);
		velocity.scale(-1);
	}	

}
