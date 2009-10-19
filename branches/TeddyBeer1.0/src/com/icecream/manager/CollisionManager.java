package com.icecream.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.component.IUpdateable;
import com.icecream.component.SpatialComponent;
import com.icecream.unit.IComponent;
import com.icecream.util.ECollisionId;

/**
 * Manages collisions between objects
 * 
 * Algorithm:
 * There is a collisionMap that contains the pairs of collisionIds
 * And there is a spatialComponents map that contains lists of components mapped to a collisionId
 * Every update the collisionMap is read and the pairs looped through
 * in the detectCollision method    
 * @author Julius
 *
 */
public class CollisionManager implements IManager, IUpdateable{

	private HashMap<ECollisionId, List<IComponent>> spatialComponents;	
	private Map<ECollisionId, ECollisionId> collisionMap;
	
	private static CollisionManager instance;
	
	private CollisionManager(){
		spatialComponents = new HashMap<ECollisionId, List<IComponent>>();
		collisionMap = new HashMap<ECollisionId, ECollisionId>();
	}
	
	public static CollisionManager instance(){
		if(instance == null){
			instance = new CollisionManager();
		}
		return instance;
	}
	
	
	public void addComponent(IComponent component){
		//Adds the spatialComponent to a list with the same collisionId 
		spatialComponents.get(((SpatialComponent)component).getCollisionId()).add(component);
	}
	/**
	 * Sets the collision between the objects
	 * @param me a CollisionID
	 * @param you collides with this ID
	 */
	public void setCollision(ECollisionId me, ECollisionId you){
		collisionMap.put(me, you);
		if(!spatialComponents.containsKey(me)){
			spatialComponents.put(me, new ArrayList<IComponent>());
		}
		if(!spatialComponents.containsKey(you)){
			spatialComponents.put(you, new ArrayList<IComponent>());
		}
	}
	/**
	 * Detects the collisions between components
	 * @param me
	 * @param you
	 */
	private void detectCollisionWith(ECollisionId me, ECollisionId you){
		List<IComponent> mes = spatialComponents.get(me);
		List<IComponent> yous = spatialComponents.get(you);
		for(IComponent iMe : mes){
			if(iMe.isActive()){
				for(IComponent iYou : yous){				
					SpatialComponent sMe = (SpatialComponent)iMe;
					SpatialComponent sYou = (SpatialComponent)iYou;
					if(sMe.getBounds()
							.intersects((sYou.getBounds()))){
						sMe.collidedWith(sYou);
						sYou.collidedWith(sMe);
					}
				}
			}			
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {		
		Set<ECollisionId> keySet = collisionMap.keySet();
		for(ECollisionId kId : keySet){
			detectCollisionWith(kId, collisionMap.get(kId));
		}
	}
	
	
}
