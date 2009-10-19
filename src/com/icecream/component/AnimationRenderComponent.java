package com.icecream.component;

import java.util.List;
import java.util.logging.Logger;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.entity.Entity;

public class AnimationRenderComponent extends RenderComponent{

	private static final Logger logger = Logger.getLogger(AnimationRenderComponent.class.getName());
	
	private List<Animation> animations;
	
	private Animation anim;
	
	/**
	 * The state to play
	 */
	private int state;
	
	/**
	 * Creates a new Rendering component based on the parameters
	 * @param spatialComponent the spatialComponent used to render it in the world
	 * @param owner the owner of this component
	 * @param layer the layer in which this graphics will be painted
	 * @param animations list of animations to animate	 
	 */
	public AnimationRenderComponent(SpatialComponent spatialComponent, Entity owner, int layer, List<Animation> animations){
		super(spatialComponent, owner, layer);
		this.animations = animations;
		setState(0);
	}	
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		if(this.state < 0 || this.state > animations.size()){
			logger.info("Attempted to set to a state that doesn't exist");
		}else{
			this.state = state;
			anim = animations.get(getState());
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g){
		if(spatialComponent.isActive()){			
			Vector2f spatialPosition = spatialComponent.getPosition();
			anim.draw(spatialPosition.x, spatialPosition.y);
		}
	}
}
