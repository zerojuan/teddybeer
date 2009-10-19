package com.icecream.manager;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import com.icecream.component.IUpdateable;
import com.icecream.component.SpatialComponent;
import com.icecream.unit.IComponent;

public class SpatialManager implements IManager, IUpdateable{

	private List<SpatialComponent> spatialComponents;
	
	private static SpatialManager instance;
	
	private SpatialManager(){
		spatialComponents = new ArrayList<SpatialComponent>();
	}
	
	public static SpatialManager instance(){
		if(instance == null){
			instance = new SpatialManager();
		}
		return instance;
	}
	
	@Override
	public void addComponent(IComponent component) {
		if(component instanceof SpatialComponent){
			spatialComponents.add((SpatialComponent)component);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		for(SpatialComponent comp : spatialComponents){
			comp.update(delta);
		}
	}
	
	

}
