package com.icecream.component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import com.icecream.entity.Entity;
import com.icecream.exception.MissingComponentException;
import com.icecream.unit.IComponent;

public class RenderComponent implements IComponent, IRenderable {

	private boolean active;
	
	protected SpatialComponent spatialComponent;
	
	private Entity entity;
	/**
	 * Render component implements layers
	 */
	private int layer;
	
	private Image image;
	
	/**
	 * Creates a new Rendering component based on the parameters
	 * @param spatialComponent the spatialComponent used to render it in the world
	 * @param owner the owner of this component
	 * @param layer the layer in which this graphics will be painted
	 * @param image the image to draw
	 */
	public RenderComponent(SpatialComponent spatialComponent, Entity owner, int layer, Image image){
		this.spatialComponent = spatialComponent;
		this.entity = owner;
		setLayer(layer);
		this.image = image;
	}
	/**
	 * Creates a new Rendering component based on the parameters
	 * @param spatialComponent the spatialComponent used to render it in the world
	 * @param owner the owner of this component
	 * @param layer the layer in which this graphics will be painted	 
	 */
	public RenderComponent(SpatialComponent spatialComponent, Entity owner, int layer){
		this.spatialComponent = spatialComponent;
		this.entity = owner;
		setLayer(layer);
	}
	
	public Image getImage(){
		return image;
	}
	
	public void setImage(Image image){
		this.image = image;
	}
	
	public int getLayer(){
		return layer;
	}
	
	public void setLayer(int layer){
		this.layer = layer;
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
		}else if(component instanceof SpatialComponent){
			this.spatialComponent = (SpatialComponent)component;
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
		if(spatialComponent == null){
			return false;
		}
		return true;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) {		
		if(spatialComponent.isActive()){
			//Paint stuff here			
			Vector2f spatialPosition = spatialComponent.getPosition(); 
			image.draw(spatialPosition.x, spatialPosition.y);
		}
	}
	
	

}
